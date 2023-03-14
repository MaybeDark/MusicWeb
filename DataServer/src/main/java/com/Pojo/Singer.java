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
@TableName(value = "singer") //对应的表名
public class Singer {

    @TableId(value = "singer_id")
    int SGid;

    @TableField(value = "singer_name")
    String SGName;

    @TableField(value = "singer_details")
    String SGDetails;

    @TableField(value = "singer_type")
    String SGType;

}
