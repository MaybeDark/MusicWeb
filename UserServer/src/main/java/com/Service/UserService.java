package com.Service;

import com.Pojo.LoginForm;
import com.Pojo.RegisterForm;
import com.Vo.RespBean;


public interface UserService {
    RespBean login(LoginForm loginForm);

    RespBean loginOut();

    RespBean register(RegisterForm registerForm);
}
