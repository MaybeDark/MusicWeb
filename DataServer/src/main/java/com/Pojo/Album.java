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
@TableName(value = "album") //对应的表名
public class Album {

    @TableId(value = "album_id")
    int Aid;

    @TableField(value = "album_name")
    String AName;

    @TableField(value = "album_singer")
    String ASinger;

    @TableField(value = "album_time")
    String ATime;

    @TableField(value = "album_details")
    String ADetails;
}
