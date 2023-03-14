package com.Service;


import com.Pojo.*;
import com.Pojo.union.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.jdbc.BadSqlGrammarException;

import java.util.List;

public interface DataService {

    SongData getSongDataBySid(String resourcesAddress) throws BadSqlGrammarException;

    List<SongData> selectSongDataListBySongListId(String songListId) throws BadSqlGrammarException;

    SongListData selectSongListDataBySongListId(String songListId) throws BadSqlGrammarException;

    IPage<Comments> selectComments(Page<Comments> page,String target, String targetId);

    IPage<SongData> search(Page<SongData> page, String key,String value);

    void addComment(String details, String target,String id, String uid);

    List<SongData> randomRecommendationSong() throws BadSqlGrammarException;

    List<Singer> randomRecommendationSinger() throws BadSqlGrammarException;

    List<AlbumData> randomRecommendationAlbum() throws BadSqlGrammarException;

    List<ListsData> getLists(String uid) throws BadSqlGrammarException;

    void collectionSongLists(String sid,String lid);

    //获取歌手详细信息
    SingerData getSingerData(String sid);
    //获取歌手的歌曲信息
    List<SongData> getSingerSongData(String sid);
    //获取歌手的评论
    List<CommentData> getSingerComment(String singerid);

    AlbumData getAlbumData(String albumid);

    List<SongData> getAlbumSongData(String albumid);

    List<CommentData> getAlbumCommentData(String albumid);

    void addList(String uid, String title,String details);

    List<ListsData> randomRecommendationLists();

    void addSong(String songName, String albumName, String singerName, String resourcesAddress, String submitUserId);

    int selectListsSong(String sid, String lid);
}
