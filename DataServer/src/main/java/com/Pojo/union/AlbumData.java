package com.Pojo.union;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AlbumData {


    int Aid;//专辑id
    String AName;//专辑名字
    String ATime;//专辑的时间
    String ADetails;//专辑的谢姐
    String SGName;//哪个歌手的专辑
}
