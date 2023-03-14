package com.Controller.Impl;

import com.Client.DataClient;
import com.Constant.Enum.RespBeanEnum;
import com.Controller.ResourceController;
import com.Pojo.ExaminingSongForm;
import com.Service.ResourceService;
import com.Vo.RespBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.tools.AudioTools;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import static com.Constant.constant.local_resources_address;

@RestController
public class ResourceControllerImpl implements ResourceController {

    @Resource
    private ResourceService resourceService;
    @Resource
    private DataClient dataClient;


    public void getAudioResource(@RequestHeader(value = "start",required = false,defaultValue = "firstHalf")String start,
                                 @PathVariable String resourcesAddress, HttpServletResponse response){
        File file = new File(local_resources_address+ resourcesAddress);
        if(file.exists()){
            File musicFile = new File(file,"music.mp3");

            if(musicFile.exists()) {
                try (OutputStream out = response.getOutputStream();
                     FileInputStream fis = new FileInputStream(musicFile);
                     BufferedInputStream bif = new BufferedInputStream(fis)) {
                    long fileLength= musicFile.length();
                    long end =  fileLength / 2;
                    if (start.equals("secondHalf")) {
                        bif.skip(fileLength / 2);
                        end = fileLength;
                    }
                    response.setStatus(javax.servlet.http.HttpServletResponse.SC_PARTIAL_CONTENT);  //根据需求实现
                    response.addHeader("Content-Type", "application/mp3");
                    response.addHeader("Content-Length", "" + fileLength /2);
                    response.addHeader("Video-Duration", AudioTools.getVideoDuration(musicFile));

                    byte[] bytes = new byte[1024];
                    int n = -1;
                    long len = 0;
                    while ((n = bif.read(bytes)) != -1) {
                        if((len + 1024) > end){
                            out.write(bytes, 0,(int)(end - len));
                            len += ((int)(end - len));
                            break;
                        }
                        out.write(bytes, 0, n);
                        len += n;
                    }
                } catch (Exception e) {
                    response.setStatus(500);
                }
            }else {
                response.setStatus(404);
            }
        }else {
            response.setStatus(404);  //根据需求实现
        }
    }

    public RespBean uploadAudioResource(@RequestParam Map<String,String> map, @RequestParam("file")MultipartFile uploadFile){
        Map<String ,String> resultMap = new HashMap<>();
        ExaminingSongForm examiningSongForm = new ExaminingSongForm();
        examiningSongForm.setSongName(map.get("songName"));
        examiningSongForm.setAlbumName(map.get("albumName"));
        examiningSongForm.setSingerName(map.get("singerName"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String redisKey = (String) authentication.getPrincipal();
        String Uid = new StringTokenizer(redisKey,"loginID:").nextToken();

        examiningSongForm.setSubmitUserid(Uid);
        InputStream uploadFileInputStream = null;
        try {
            uploadFileInputStream = uploadFile.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String resourcesAddress = "";
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(uploadFileInputStream)){
            if(AudioTools.isMP4(bufferedInputStream)){
                    resourcesAddress = resourceService.uploadAudioResource(bufferedInputStream);
                    examiningSongForm.setResourcesAddress(resourcesAddress);
                    dataClient.addExaminingSong(examiningSongForm);
                    resultMap.put("msg","上传成功");
                    return RespBean.success(RespBeanEnum.SUCCESS_200,resultMap);
            }else {
                resultMap.put("msg","仅支持MP3文件上传");
                return RespBean.error(RespBeanEnum.ERROR_400,resultMap);
            }
        }catch (Exception e){
            resultMap.put("msg","服务器发生错误");
            e.printStackTrace();
            return RespBean.error(RespBeanEnum.ERROR_500);
        }
    }
}
