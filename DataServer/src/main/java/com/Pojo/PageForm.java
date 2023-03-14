package com.Pojo;

import com.Validation.annotation.FormData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PageForm {

    @FormData
    String current;

    @FormData
    String size;

    @FormData
    String target;

    @FormData
    String targetId;

}
