package com.Filter;

import com.Constant.Enum.RespBeanEnum;
import com.Vo.RespBean;
import com.alibaba.fastjson.JSON;
import com.tools.JWT;
import com.tools.RedisUtil;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.Config.SecurityConfig.anonymous_URL;

/**
 * 用户持有token时 签发Security认证
 */
@Component
public class HoldPassFilter extends OncePerRequestFilter {

    @Resource
    private RedisUtil redisUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        for (String s : anonymous_URL) {
            if(httpServletRequest.getRequestURL().indexOf(s) != -1){
                filterChain.doFilter(httpServletRequest,httpServletResponse);
                return;
            }
        }

        String token = httpServletRequest.getHeader("token");
        if(!StringUtils.hasText(token)){
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }
        String userId;
        try{
            Claims claims = JWT.parseBody(token);
            userId = String.valueOf(claims.get("id"));
        }catch(Exception e){
            Map<String,Object> resultMap = new HashMap<>();
            resultMap.put("msg","Cookie非法");
//            httpServletResponse.setContentType("text/html;charset=utf-8");
            httpServletResponse.setStatus(403);
            httpServletResponse.getWriter().write(JSON.toJSONString(RespBean.error(RespBeanEnum.ERROR_403,resultMap)));
            return;
        }

        String redisKey = "loginID:"+userId;

        String redisToken = (String)redisUtil.get(redisKey);

        if(!StringUtils.hasText(redisToken)){
            Map<String,Object> resultMap = new HashMap<>();
            resultMap.put("msg","Cookie已过期");
//            httpServletResponse.setContentType("text/html;charset=utf-8");
            httpServletResponse.setStatus(403);
            httpServletResponse.getWriter().write(JSON.toJSONString(RespBean.error(RespBeanEnum.ERROR_403,resultMap)));
            return;
        }

        if(!redisToken.equals(token)){
            Map<String,Object> resultMap = new HashMap<>();
            resultMap.put("msg","Cookie非法");
//            httpServletResponse.setContentType("text/html;charset=utf-8");
            httpServletResponse.setStatus(403);
            httpServletResponse.getWriter().write(JSON.toJSONString(RespBean.error(RespBeanEnum.ERROR_403,resultMap)));
            return;
        }

        //TODO 获取权限信息
        //签发认证
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(redisKey,null,null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
