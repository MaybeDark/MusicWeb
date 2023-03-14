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
public class CollectionSongForm {

    @FormData
    String lid;

    @FormData
    String sid;
}
