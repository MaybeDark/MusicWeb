package com.Pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * mybatisplus所需的填充类User
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName(value = "user") //对应的表名
public class User {

    @TableId(value = "user_id") //对应主键
    int Uid;

    @TableField(value = "user_name")//对应列名
    String UName;

    @TableField(value = "user_password")
    String UPassword;

    @TableField(value = "user_nickName")
    String UNickName;

    @TableField(value = "user_rank")
    String URank;
}
