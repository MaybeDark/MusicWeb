package com.Controller;

import com.Pojo.LoginForm;
import com.Pojo.RegisterForm;
import com.Vo.RespBean;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.BindException;

@RequestMapping("/user")
public interface UserController {

    @RequestMapping("/hello")
    String hello();

    @PostMapping("/login")
    RespBean login(@RequestBody @Valid LoginForm loginForm, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws BindException;

    @PostMapping("/loginOut")
    RespBean loginOut();

    @PostMapping("/register")
    RespBean register(@RequestBody @Valid RegisterForm registerForm, BindingResult result, HttpServletRequest request, HttpServletResponse response);
}
