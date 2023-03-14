package com.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ExaminingSongForm {

    String songName;
    String singerName;
    String albumName;
    String resourcesAddress;
    String submitUserid;
}
