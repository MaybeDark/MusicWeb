package com.Pojo;

import com.Validation.annotation.FormData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchForm {

    @FormData
    @NotBlank
    String key;

    @FormData
    @NotNull
    @NotBlank
    String value;

    @FormData
    String current;

    @FormData
    String size;
}
