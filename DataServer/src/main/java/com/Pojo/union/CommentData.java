package com.Pojo.union;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CommentData {

    String cd;//评论细节
    String ct;//评论时间
    String userName;//谁评论她
}
