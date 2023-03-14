package com.Mapper;

import com.Pojo.Lists;
import com.Pojo.ListsData;
import com.Pojo.Singer;
import com.Pojo.songId;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ListsMapper  extends BaseMapper<Lists> {
    //根据用户id获取歌单
    @Results({
            @Result(column = "list_title", property = "LTitle"),
            @Result(column = "list_time", property = "LTime"),
            @Result(column = "list_id", property = "Lid"),
            @Result(column = "list_details", property = "LDetails"),
            @Result(column = "user_name", property = "userName")
    })
    @Select("select lists.list_id,lists.list_title,lists.list_time,lists.list_details, user.user_name from lists,user where lists.list_userid = (#{uid}) and `user`.user_id = (#{uid})")
    List<ListsData> getList(@Param("uid") String uid);

    @Results({
            @Result(column = "list_title", property = "LTitle"),
            @Result(column = "list_time", property = "LTime"),
            @Result(column = "list_id", property = "Lid"),
            @Result(column = "list_details", property = "LDetails"),
            @Result(column = "song_id", property = "Sid"),
            @Result(column = "list_userid", property = "LUserid")
    })
    @Insert("insert into songlist value(0,(#{lid}),(#{sid}))")
    void collectionSongLists(@Param("sid") String sid,@Param("lid") String lid);

    @Insert("insert into lists value(0,(#{title}),(#{uid}),now(),(#{details}))")
    void addList(@Param("uid") String uid,@Param("title") String title,@Param("details") String details);

    @Results({
            @Result(column = "list_id",property = "Lid"),
            @Result(column = "list_title", property = "LTitle"),
            @Result(column = "list_userid", property = "LUserid"),
            @Result(column = "list_details", property = "LDetails"),
            @Result(column = "user_name", property = "userName"),
            @Result(column = "list_time", property = "LTime")

    })
    @Select("select lists.list_id, lists.list_title, lists.list_details,lists.list_time,user.user_name from lists,user where user.user_id = lists.list_userid order by rand() limit 8")
    List<ListsData> randomRecommendationLists();


    @Results({
            @Result(column = "list_title", property = "LTitle"),
            @Result(column = "list_time", property = "LTime"),
            @Result(column = "list_id", property = "Lid"),
            @Result(column = "list_details", property = "LDetails"),
            @Result(column = "song_id", property = "Sid"),
            @Result(column = "list_userid", property = "LUserid")
    })
    @Select("SELECT songlist.songlist_songid FROM songlist where songlist.songlist_listid = (#{lid}) and songlist.songlist_songid = (#{sid})")
    List<songId> selectListsSong(@Param("sid") String sid, @Param("lid") String lid);
}
