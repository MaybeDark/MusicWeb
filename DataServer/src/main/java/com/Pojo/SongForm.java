package com.Pojo;

import com.Validation.annotation.FormData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/*
* 插入歌曲填充类
* */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class SongForm {


        @NotBlank(message="歌曲名不能为空")
        @Size(max = 12 ,min = 1,message = "歌曲名应在1-12个字符")
        @FormData()
        String songName;

        @NotBlank(message="歌手id不能为空")
        @Size(max = 12 ,min = 1,message = "歌曲名应在1-12个字符")
        @FormData()
        String songSinger;


        @FormData()
        String songAlbum;

        @FormData()
        String songResourcesAddress;

//    @NotBlank(message = "验证码为空")
//    @Captcha()
//    @FormData()
//    String captcha;


}
