package com.Pojo;

import com.Validation.annotation.FormData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CommentForm {

    @NotBlank
    @FormData
    String details;

    @NotBlank
    @FormData
    String targetId;

    @NotBlank
    @FormData
    String target;
}
