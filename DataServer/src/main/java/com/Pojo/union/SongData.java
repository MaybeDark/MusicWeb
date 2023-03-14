package com.Pojo.union;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SongData {

    String SName;

    int SGid;

    String SGName;

    int Aid;

    String AName;

    String Sid;

    String SResources_address;
}
