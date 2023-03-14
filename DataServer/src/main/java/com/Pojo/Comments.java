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
@TableName(value = "comments") //对应的表名
public class Comments {

    @TableId(value = "comments_id")
    int Cid;

    @TableField(value = "comments_details")
    String CDetails;

    @TableField(value = "comments_time")
    String CTime;

    @TableField(value = "comments_userid")
    String CUserid;

    @TableField(value = "comments_type")
    String CType;

    @TableField(value = "comments_targetid")
    int CTid;

    @TableField(exist = false)
    String UNickName;
}
