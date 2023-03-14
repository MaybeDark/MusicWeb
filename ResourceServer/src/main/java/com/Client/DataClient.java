package com.Client;

import com.Config.FeignConfig;
import com.Fallback.DataClientFallback;
import com.Pojo.ExaminingSongForm;
import com.Vo.RespBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

/**
 * Feign远程调用类
 */
//value = "服务名" ,fallback = error处理类
@FeignClient(value="DataServer" ,configuration = FeignConfig.class)
public interface DataClient {

    @PostMapping("/data/addSong")
    RespBean addExaminingSong(@RequestBody @Valid ExaminingSongForm examiningSongForm);

}
