package com.Pojo.union;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SongListData {

    String LTitle;

    String UNickName;

    String LTime;

    String LDetails;

    int Uid;
}
