package com.Config;


import com.ErrorHandle.NotLoggedErrorHandle;
import com.Filter.EncodingFilter;
import com.Filter.ExceptionFilter;
import com.Filter.HoldPassFilter;
import com.Filter.RequestWrapperFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

//用于配置springSecurity 可用于添加过滤器
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    public final static String[] authenticated_URL ={"/resource/uploadAudio"};              //不允许匿名访问的URL

    // 密码加密方式
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    @Resource
    private EncodingFilter encodingFilter;  //utf编码过滤器
    @Resource
    private RequestWrapperFilter requestWrapperFilter;
    @Resource
    private HoldPassFilter holdPassFilter;  //处理token的过滤器
    @Resource
    private NotLoggedErrorHandle notLoggedErrorHandle;  //处理未登录的类
    @Resource
    private ExceptionFilter exceptionFilter;    //处理过滤器产出的异常的过滤器

    @Override
    protected  void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                //关闭csrf
                .csrf().disable()
                //不通过session获取securityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                //需要认证的接口
                .antMatchers(authenticated_URL).authenticated()
                //除了上面的接口都允许匿名访问
                .anyRequest().anonymous()
                .and()
                .cors();
        //添加自定义未登录处理
        httpSecurity.exceptionHandling().authenticationEntryPoint(notLoggedErrorHandle);
        //添加检验token过滤器

        httpSecurity.addFilterBefore(holdPassFilter,UsernamePasswordAuthenticationFilter.class);
        httpSecurity.addFilterBefore(requestWrapperFilter,HoldPassFilter.class);
        httpSecurity.addFilterBefore(encodingFilter,RequestWrapperFilter.class);
        httpSecurity.addFilterBefore(exceptionFilter,EncodingFilter.class);
    }

}
