package com.Fallback;

import com.Client.DataClient;
import com.Constant.Enum.RespBeanEnum;
import com.Pojo.ExaminingSongForm;
import com.Vo.RespBean;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

//对MusicClient发生错误时的处理类
//response的非2**的状态码都会触发fallback
//对未完成服务进行降级处理
@Component
public class DataClientFallback implements DataClient {
    @Override
    public RespBean addExaminingSong(ExaminingSongForm examiningSongForm) {
        System.out.println("触发降级");
        return RespBean.error(RespBeanEnum.ERROR_500);
    }
}
