package com.Constant.Enum;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

//存放返回体的状态码
@AllArgsConstructor
@ToString
@Getter
public enum RespBeanEnum {
    SUCCESS_200(200,"OK"),
    SUCCESS_204(204,"No Content"),
    SUCCESS_206(206,"Partial Content"),

    WARN_301(301,"Partial Content"),
    WARN_302(302,"Found"),
    WARN_303(303,"See Other"),
    WARN_304(304,"Not Modified"),
    WARN_307(307,"Temporary Redirect"),

    ERROR_400(400,"Bad Request"),
    ERROR_401(401,"Unauthorized"),
    ERROR_403(403,"Forbidden"),
    ERROR_404(404,"Not Found"),
    ERROR_405(405,"Request method not supported"),

    ERROR_500(500,"Internal Server Error"),
    ERROR_503(503,"Service Unavailable");




    private final Integer code;
    private final String msg;


}
