package com.Mapper;

import com.Pojo.Singer;
import com.Pojo.union.CommentData;
import com.Pojo.union.SingerData;
import com.Pojo.union.SongData;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.security.core.parameters.P;

import java.util.List;

public interface SingerMapper  extends BaseMapper<Singer> {
    @Results({
            @Result(column = "singer_id",property = "SGid"),
            @Result(column = "singer_name", property = "SGName"),
            @Result(column = "singer_type", property = "SGType"),
            @Result(column = "singer_details", property = "SGDetails")

    })
    @Select("select singer.singer_name, singer.singer_details, singer.singer_type,singer.singer_id from singer order by rand() limit 8")
    List<Singer> randomRecommendationSinger();

    @Results({
            @Result(column = "singer_id",property = "SGid"),
            @Result(column = "singer_name", property = "SGName"),
            @Result(column = "singer_type", property = "SGType"),
            @Result(column = "singer_details", property = "SGDetails")
    })
    @Select("select singer.singer_name, singer.singer_details, singer.singer_type,singer.singer_id from singer where singer.singer_id = (#{sid})")
    SingerData getSingerData(@Param("sid") String sid);

    //通过歌手id获取音乐信息
    @Results({
            @Result(column = "song_name", property = "SName"),
            @Result(column = "singer_id",property = "SGid"),
            @Result(column = "singer_name", property = "SGName"),
            @Result(column = "album_id",property = "Aid"),
            @Result(column = "song_id",property = "Sid"),
            @Result(column = "album_name", property = "AName"),
            @Result(column = "song_resources_address",property = "SResources_address")
    })
    @Select("select song.song_id,song.song_name,singer.singer_id,singer.singer_name,album.album_id,album.album_name,song_resources_address " +
            "from song,singer,album " +
            "WHERE song.song_singer = (#{sid}) and singer.singer_id = (#{sid}) and song.song_album = album.album_id ")
    List<SongData> getSingerSongData(@Param("sid") String sid);



    //通过歌手id找到属于她的评论
    @Results({
            @Result(column = "comments_details", property = "cd"),
            @Result(column = "comments_time",property = "ct"),
            @Result(column = "user_name",property = "userName")
    })
    @Select("select comments.comments_details,comments.comments_time,user.user_name from comments,user where comments_targetid = (#{singerid}) and comments_type = 3 and comments_userid = user.user_id")
    List<CommentData> getSingerComment(@Param("singerid") String singerid);
}
