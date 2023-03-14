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
@TableName(value = "lists") //对应的表名
public class Lists {

    @TableId(value = "list_id")
    int Lid;

    @TableField(value = "list_title")
    String LTitle;

    @TableField(value = "list_userid")
    int LUserid;

    @TableField(value = "list_time")
    String LTime;

    @TableField(value = "list_details")
    String LDetails;
}
