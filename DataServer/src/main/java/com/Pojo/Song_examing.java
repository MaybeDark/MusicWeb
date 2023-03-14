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
@TableName(value = "song_examining") //对应的表名
public class Song_examing {

    @TableId(value = "song_examining_id")
    int SEid;

    @TableField(value = "song_examining_name")
    String SEName;

    @TableField(value = "song_examining_resources_address")
    String SEresourcesaddress;

    @TableField(value = "song_examining_album")
    String SEalbum;

    @TableField(value = "song_examining_singer")
    String SEsinger;

    @TableField(value = "song_examining_submit_user_id")
    int SEuserid;

    @TableField(value = "song_examining_submit_user_name")
    String SEuserName;
}
