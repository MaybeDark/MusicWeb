package com.ErrorHandle;

import com.Constant.Enum.RespBeanEnum;
import com.Vo.RespBean;
import com.fasterxml.jackson.core.JsonParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.net.BindException;
import java.util.HashMap;
import java.util.Map;


//异常捕获类,用于捕获Controller层的异常
//Service层异常统一抛出到Controller层
@ControllerAdvice
public class ExceptionHandle {

    @ExceptionHandler(value = {JsonParseException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody RespBean JsonParseExceptionHandle(JsonParseException e){
        Map<String,String> resultMap = new HashMap<>();
        resultMap.put("msg","请求体JSON格式异常");
        return RespBean.error(RespBeanEnum.ERROR_400,resultMap);
    }

    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody RespBean HttpMessageNotReadableExceptionHandle(HttpMessageNotReadableException e){
        Map<String,String> resultMap = new HashMap<>();
        resultMap.put("msg","请求体参数不正确");
        return  RespBean.error(RespBeanEnum.ERROR_400,resultMap);
    }

    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody RespBean HttpRequestMethodNotSupportExceptionHandle(HttpRequestMethodNotSupportedException e){
        Map<String,String> resultMap = new HashMap<>();
        resultMap.put("msg","请求方式不接受");
        return  RespBean.error(RespBeanEnum.ERROR_400,resultMap);
    }

//    @ExceptionHandler(value = QueryTimeoutException.class)
//    public @ResponseBody RespBean QueryTimeoutExceptionHandle(QueryTimeoutException e){
//        return RespBean.error(RespBeanEnum.ERROR_500,"数据库连接超时");
//    }

    @ExceptionHandler(value = RequestRejectedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody RespBean RequestRejectedExceptionHandle(RequestRejectedException e){
        Map<String,String> resultMap = new HashMap<>();
        resultMap.put("msg","请求地址格式不正确");
        return RespBean.error(RespBeanEnum.ERROR_500,resultMap);
    }

    @ExceptionHandler(value = BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody RespBean BindExceptionHandle(BindException e){
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("msg","参数含有非法字符");
        return RespBean.error(RespBeanEnum.ERROR_500,resultMap);
    }
}
