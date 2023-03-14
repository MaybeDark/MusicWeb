package com.Controller.impl;

import com.Constant.constant;
import com.Controller.UserController;
import com.Constant.Enum.RespBeanEnum;
import com.Pojo.LoginForm;
import com.Pojo.RegisterForm;
import com.Service.UserService;
import com.Vo.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


//@Slf4j                  //slf4j日志 log.***("")
@RestController
public class UserControllerImpl implements UserController {

    @Autowired
    private UserService userService;

    public String  hello(){
        System.out.println("hello");
        return "hello";
    }

    @Override
    public RespBean login(@RequestBody @Valid LoginForm loginForm, BindingResult result, HttpServletRequest request, HttpServletResponse response)
        throws  HttpClientErrorException{
        if (result.hasErrors()) {
            Map<String,Object> errorMap = new HashMap<>();
            errorMap.put("msg","登录失败");
            result.getFieldErrors().forEach(fieldError -> errorMap.put(fieldError.getField(),fieldError.getDefaultMessage()));
            response.setStatus(400);
            return RespBean.error(RespBeanEnum.ERROR_400,errorMap);
        }

        RespBean resp =userService.login(loginForm);
        response.setStatus(resp.getCode());
        return resp;
    }

    @Override
    public RespBean loginOut() {
        userService.loginOut();
        return null;
    }

    @Override
    public RespBean register(@RequestBody @Valid RegisterForm registerForm, BindingResult result, HttpServletRequest request, HttpServletResponse response) {
        if (result.hasErrors()) {
            Map<String,Object> errorMap = new HashMap<>();
            errorMap.put("msg","注册失败");
            result.getFieldErrors().forEach(fieldError -> errorMap.put(fieldError.getField(),fieldError.getDefaultMessage()));
            response.setStatus(400);
            return RespBean.error(RespBeanEnum.ERROR_400,errorMap);
        }
        RespBean resp = userService.register(registerForm);
        response.setStatus(resp.getCode());
        return resp;
    }

}
