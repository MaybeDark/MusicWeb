package com.Pojo;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName(value = "song") //对应的表名
public class Song {

    @TableId(value = "song_id")
    int Sid;

    @TableField(value = "song_name")
    String SName;

    @TableField(value = "song_singer")
    int SSinger;

    @TableField(value = "song_album")
    int SAlbum;

    @TableField(value = "song_lyric")
    String SLyric;
}
