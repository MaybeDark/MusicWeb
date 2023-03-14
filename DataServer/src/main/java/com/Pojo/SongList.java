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
@TableName(value = "songlist") //对应的表名
public class SongList {

    @TableId(value = "songlist_id")
    int SLid;

    @TableField(value = "songlist_listid")
    int SLLid;

    @TableField(value = "songlist_songid")
    int SLSid;
}
