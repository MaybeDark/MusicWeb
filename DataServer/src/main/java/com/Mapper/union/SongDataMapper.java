package com.Mapper.union;

import com.Pojo.Comments;
import com.Pojo.Singer;
import com.Pojo.union.SongData;
import com.Pojo.union.SongListData;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.*;
import org.springframework.security.core.parameters.P;

import java.util.List;

public interface SongDataMapper extends BaseMapper<SongData> {

    //通过音乐ID获取音乐信息
    @Results({
            @Result(column = "song_id", property = "Sid"),
            @Result(column = "song_name", property = "SName"),
            @Result(column = "singer_id",property = "SGid"),
            @Result(column = "singer_name", property = "SGName"),
            @Result(column = "album_id",property = "Aid"),
            @Result(column = "album_name", property = "AName"),
            @Result(column = "song_resources_address",property = "SResources_address")
    })
    @Select("select song.song_name,singer.singer_id,singer.singer_name,album.album_id,album.album_name,song_resources_address " +
            "from song join singer on song.song_singer = singer.singer_id join album on song.song_album = album.album_id " +
            "WHERE song.song_id = (#{songid}) ")
    SongData selectSongDataBySid(@Param("songid") String songid);

    //通过歌单ID获取歌单信息
    @Results({
            @Result(column = "list_title", property = "LTitle"),
            @Result(column = "list_time", property = "LTime"),
            @Result(column = "list_details", property = "LDetails"),
            @Result(column = "user_id", property = "Uid"),
            @Result(column = "user_nickName", property = "UNickName"),
    })
    @Select("select lists.list_title,lists.list_time,lists.list_details,user.user_id,user.user_nickName " +
            "from lists,user " +
            "where lists.list_id = (#{listId}) and lists.list_userid = user.user_id")
    SongListData selectSongListDataByListId(@Param("listId")String songListId );

    //通过歌单ID获取歌单包含的歌曲的信息
    @Results({
            @Result(column = "song_name", property = "SName"),
            @Result(column = "singer_name", property = "SGName"),
            @Result(column = "album_name", property = "AName"),
            @Result(column = "album_id",property = "Aid"),
            @Result(column = "singer_id",property = "SGid"),
            @Result(column = "song_id",property = "Sid"),
            @Result(column = "song_resources_address",property = "SResources_address")
    })
    @Select("SELECT song.song_id,song.song_name,singer.singer_id,singer.singer_name,song.song_resources_address,album.album_id,album.album_name " +
            "FROM songlist right JOIN song on songlist.songlist_songid = song.song_id LEFT JOIN singer on song.song_singer = singer.singer_id LEFT JOIN album ON song.song_album = album.album_id " +
            "where songlist.songlist_listid = (#{songListId}) ")
    List<SongData> selectSongDataListBySongListId(@Param("songListId") String songListId);


    @Results({
            @Result(column =  "user_nickName", property = "UNickName"),
            @Result(column =  "comments_id",property = "Cid"),
            @Result(column =  "comments_details",property = "CDetails"),
            @Result(column =  "comments_time",property = "CTime"),
            @Result(column =  "comments_userid",property = "CUserid"),
            @Result(column =  "comments_type",property = "CType"),
            @Result(column =  "comments_targetid",property = "CTid"),
    })
    @Select("SELECT comments.*,user.user_nickName from user,comments " +
            "where comments_type = (#{type}) and comments.comments_targetid = (#{id}) " +
            "and user.user_id = comments.comments_userid ")
    IPage<Comments> selectComments(Page<Comments> page, @Param("type")String type,@Param("id")String id);


    @Results({
            @Result(column = "song_name", property = "SName"),
            @Result(column = "singer_name", property = "SGName"),
            @Result(column = "album_name", property = "AName"),
            @Result(column = "album_id",property = "Aid"),
            @Result(column = "singer_id",property = "SGid"),
            @Result(column = "song_id",property = "Sid"),
            @Result(column = "song_resources_address",property = "SResources_address")
    })
    @Select("SELECT song.song_id,song.song_name,singer.singer_id,singer.singer_name,song.song_resources_address,album.album_id,album.album_name " +
            "FROM song JOIN singer on song.song_singer = singer.singer_id join album on song.song_album = album.album_id " +
            "where ${key} like CONCAT('%',#{value},'%')")
    IPage<SongData> search(Page<SongData> page, @Param("key")String key , @Param("value")String value);

    @Results({
            @Result(column = "song_name", property = "SName"),
            @Result(column = "singer_id",property = "SGid"),
            @Result(column = "singer_name", property = "SGName"),
            @Result(column = "album_id",property = "Aid"),
            @Result(column = "song_id",property = "Sid"),
            @Result(column = "album_name", property = "AName"),
            @Result(column = "song_resources_address",property = "SResources_address")
    })
    @Select("select song.song_name,singer.singer_id,singer.singer_name,album.album_id,album.album_name,song_resources_address,song.song_id " +
            "from song,singer,album " +
            "WHERE song.song_singer = singer.singer_id and song.song_album = album.album_id " +
            "order by rand() limit 8")
    List<SongData> randomRecommendationSong();



}
