package com.Mapper;

import com.Pojo.Album;
import com.Pojo.union.AlbumData;
import com.Pojo.union.AlbumSongData;
import com.Pojo.union.CommentData;
import com.Pojo.union.SongData;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AlbumMapper extends BaseMapper<Album> {

    @Results({
            @Result(column = "album_details", property = "ADetails"),
            @Result(column = "singer_name", property = "SGName"),
            @Result(column = "album_id",property = "Aid"),
            @Result(column = "album_time",property = "ATime"),
            @Result(column = "album_name", property = "AName")
    })
    @Select("select album.album_id,album.album_name,album.album_singer,album.album_details,singer.singer_name,album.album_time from album,singer where album.album_id = singer.singer_id order by rand() limit 8")
    List<AlbumData> randomRecommendationAlbum();

    @Results({
            @Result(column = "album_details", property = "ADetails"),
            @Result(column = "singer_name", property = "SGName"),
            @Result(column = "album_id",property = "Aid"),
            @Result(column = "album_time",property = "ATime"),
            @Result(column = "album_name", property = "AName")
    })
    @Select("select album.album_id,album.album_name,album.album_singer,album.album_details,singer.singer_name,album.album_time " +
            "from album,singer " +
            "where album.album_id = singer.singer_id  and album.album_id = (#{albumid})")
    AlbumData getAlbumData(@Param("albumid") String albumid);

    @Results({
            @Result(column = "song_id",property = "Sid"),
            @Result(column = "album_name", property = "AName"),
            @Result(column = "song_resources_address",property = "SResources_address"),
            @Result(column = "song_name", property = "SName"),
            @Result(column = "singer_id",property = "SGid"),
            @Result(column = "singer_name", property = "SGName"),
            @Result(column = "album_id",property = "Aid"),
            @Result(column = "album_name", property = "AName"),
            @Result(column = "song_resources_address",property = "SResources_address")
    })
    @Select("select song.song_id,song.song_name,singer.singer_id,singer.singer_name,album.album_id,album.album_name,song_resources_address " +
            "from song join singer on song.song_singer = singer.singer_id join album on song.song_album = album.album_id " +
            "WHERE album.album_id = (#{albumid}) and song.song_album = (#{albumid})")
    List<SongData> getAlbumSongData(@Param("albumid") String albumid);

    @Results({
            @Result(column = "comments_details", property = "cd"),
            @Result(column = "comments_time",property = "ct"),
            @Result(column = "user_name",property = "userName")
    })
    @Select("select comments.comments_details,comments.comments_time,user.user_name from comments,user where comments_targetid = (#{albumid}) and comments_type = 4 and comments_userid = user.user_id")
    List<CommentData> getAlbumCommentData(@Param("albumid") String albumid);
}