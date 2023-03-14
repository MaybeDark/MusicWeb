package com.Pojo.union;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SingerData {
//新增歌单要title、uid
//歌手详情要sgid  要返回sgname、sgdetails、sgtype；属于他个歌列表；他的评论
//专辑个歌手一样
    String SGid;//歌手id
    String SGName;//歌手名字
    String SGDetails;//歌手详情
    String SGType;//歌手类别
//    String aid;//专辑id
//    String aname;//专辑名字
}
