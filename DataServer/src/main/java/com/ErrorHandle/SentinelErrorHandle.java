package com.ErrorHandle;

import com.Constant.Enum.RespBeanEnum;
import com.Vo.RespBean;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


//对本地的BlockException进行处理
@Component
public class SentinelErrorHandle implements BlockExceptionHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, BlockException e) throws Exception {
        RespBean respBean = null;
        if (e instanceof FlowException) {
            respBean = RespBean.error(RespBeanEnum.ERROR_503,"访问用户过多,请稍后再试");
        } else if (e instanceof DegradeException) {
            respBean = RespBean.error(RespBeanEnum.ERROR_503,"服务出错,但已降级处理");
        } else if (e instanceof ParamFlowException) {
            respBean = RespBean.error(RespBeanEnum.ERROR_503,"访问用户过多,请稍后再试");
        } else if (e instanceof SystemBlockException) {
            respBean = RespBean.error(RespBeanEnum.ERROR_503,"服务器过载,已启动保护");
        } else if (e instanceof AuthorityException) {
            respBean = RespBean.error(RespBeanEnum.ERROR_503,"你已被服务器拒绝访问");
        }
        httpServletResponse.setStatus(503);
        httpServletResponse.getWriter().write(JSON.toJSONString(respBean));

}
}
