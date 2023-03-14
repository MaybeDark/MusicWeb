package com.Pojo;

import com.Validation.annotation.Captcha;
import com.Validation.annotation.FormData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


/**
 * 登录表格填充类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginForm {

    @NotBlank(message="用户名不能为空")
    @Size(max = 12 ,min = 1,message = "用户名应在1-12个字符")
    @FormData()
    String userName;

    @NotBlank(message="密码不能为空")
    @Size(min = 3,max = 12,message = "密码应在3-12个字符")
    @FormData()
    String userPassword;

//    @NotBlank(message = "验证码为空")
//    @Captcha()
//    @FormData()
//    String captcha;

}
