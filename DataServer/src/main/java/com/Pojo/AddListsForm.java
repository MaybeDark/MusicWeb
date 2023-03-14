package com.Pojo;

import com.Validation.annotation.FormData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/*
* 插入歌单填充泪
* */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class AddListsForm
{
    @FormData
    String title;
    @FormData
    String uid;
    @FormData
    String details;
}
