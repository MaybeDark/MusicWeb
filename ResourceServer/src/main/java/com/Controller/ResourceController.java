package com.Controller;

import com.Vo.RespBean;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@RequestMapping("/resource")
public interface ResourceController {


    @RequestMapping("/getAudio/{resourcesAddress}")
    void getAudioResource(@RequestHeader(value = "start",required = false,defaultValue = "firstHalf")String start,
                          @PathVariable String resourcesAddress, HttpServletResponse response);

    @PostMapping(value = "/uploadAudio")
    RespBean uploadAudioResource(@RequestParam Map<String,String> map, @RequestParam("file")MultipartFile uploadMp3);
}