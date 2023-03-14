package com.ErrorHandle;

import com.Constant.Enum.RespBeanEnum;
import com.Vo.RespBean;
import com.alibaba.fastjson.JSON;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//用户未登录时处理方法
@Component
public class NotLoggedErrorHandle implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
        Map<String,Object> msgMap = new HashMap<>();
        msgMap.put("msg","用户未登录");
        httpServletResponse.setStatus(403);
        httpServletResponse.getWriter().write(JSON.toJSONString(RespBean.error(RespBeanEnum.ERROR_403,msgMap)));
    }

}
