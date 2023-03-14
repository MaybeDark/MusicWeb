package com.Service.impl;

import com.Constant.Enum.RespBeanEnum;
import com.Mapper.UserMapper;
import com.Pojo.LoginForm;
import com.Pojo.LoginUser;
import com.Pojo.RegisterForm;
import com.Pojo.User;
import com.Service.UserService;
import com.Vo.RespBean;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.QueryChainWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tools.JWT;
import com.tools.RedisUtil;
import io.lettuce.core.RedisCommandTimeoutException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantLock;

/**
* 登录的具体实现类
*/
//@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private ObjectMapper objectMapper;

    @Resource
    private UserMapper userMapper;
    @Resource
    private RedisUtil redisUtil;

    Map<String , ReentrantLock> mutexCache = new ConcurrentHashMap<>(); //一个用户名只能同时执行一个上锁的业务

    @Override
    public RespBean login(LoginForm loginForm) throws RedisCommandTimeoutException , RedisConnectionFailureException{
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginForm.getUserName(),loginForm.getUserPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        if(Objects.isNull(authenticate)){
            throw new RuntimeException("登录失败");
        }

        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        User user = loginUser.getUser();

        Map<String,Object> claims = new HashMap<>();
        claims.put("id",user.getUid());
        claims.put("rank",user.getURank());
        String token = JWT.creat(claims);

        Map<String,Object> resultMap = new HashMap<>();

        if(!redisUtil.set("loginID:" + user.getUid(), token, 60 * 30)){
            resultMap.put("msg","redis数据库连接超时");
            return RespBean.error(RespBeanEnum.ERROR_500,resultMap);
        }
        resultMap.put("msg","登录成功");
        resultMap.put("token",token);
        resultMap.put("nickName", user.getUNickName());
        resultMap.put("userId",user.getUid());
        return RespBean.success(RespBeanEnum.SUCCESS_200,resultMap);
    }

    @Override
    public RespBean loginOut() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String redisKey = (String) authentication.getPrincipal();
        Map<String,Object> resultMap = new HashMap<>();
        if (!redisUtil.del(redisKey)){
            resultMap.put("msg","redis数据库连接超时");
            return RespBean.error(RespBeanEnum.ERROR_500,resultMap);
        }
        resultMap.put("msg","");
        return RespBean.success(RespBeanEnum.SUCCESS_200,resultMap);
    }

    @Override
    public  RespBean register(RegisterForm registerForm) {
        User user;
        String userName = registerForm.getUserName();
        String userPassword = registerForm.getUserPassword();
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUName,userName);
        Map<String,Object> resultMap = new HashMap<>();
        //从缓存集中获取钥匙，如果没有就new一个，如果在用就进队列
        ReentrantLock mute4key = mutexCache.computeIfAbsent(userName,k -> new ReentrantLock());

        //开始用之前先上锁
        mute4key.lock();
        try{
            user = userMapper.selectOne(wrapper);
            if(!Objects.isNull(user)){
                resultMap.put("msg","用户名已存在");
                return RespBean.success(RespBeanEnum.SUCCESS_200,resultMap);
            }else {
                user = new User();
                user.setUName(userName);
                user.setUPassword(passwordEncoder.encode(userPassword));
                user.setUNickName(userName);
                user.setURank("user");
            }

            if(userMapper.insert(user) == 1){                       //mysql插入数据
                resultMap.put("msg","");
                return RespBean.success(RespBeanEnum.SUCCESS_200,resultMap);
            }else {
                resultMap.put("msg","数据库发生异常,注册失败");
                return RespBean.success(RespBeanEnum.ERROR_500,resultMap);
            }
        }finally {
            //如果等待队列等于0就是删除钥匙 节省内存
            if(mute4key.getQueueLength() == 0){
                mutexCache.remove(userName);
            }
            //用完解锁
            mute4key.unlock();
        }


    }
}
