package com.Service.Impl;

import com.Service.ResourceService;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Random;

import static com.Constant.constant.local_resources_address;

@Service
public class ResourceServiceImpl implements ResourceService {

    /*
    * Return resourceAddress
    *
    * */
    @Override
    public String uploadAudioResource(BufferedInputStream uploadFileInputStream){
        File file = new File(local_resources_address);
        Random random = new Random();
        int address = random.nextInt(1000000);
        File targetFile;
        while (true){
            targetFile = new File(file,String.valueOf(address));
            if(!targetFile.exists()){
                targetFile.mkdirs();
                targetFile = new File(targetFile,"music.mp3");
                try(FileOutputStream fileOutputStream = new FileOutputStream(targetFile)){
                    byte[] bs = new byte[1024];
                    int n = -1;
                    while((n = uploadFileInputStream.read(bs)) != -1) {
                        fileOutputStream.write(bs,0,n);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            }
            address = random.nextInt(1000000);
        }
        return String.valueOf(address);
    }
}
