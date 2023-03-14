package com.Service.Impl;

import com.Mapper.*;
import com.Mapper.union.SongDataMapper;

import com.Pojo.*;
import com.Pojo.union.*;
import com.Service.DataService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.checkerframework.checker.units.qual.C;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DataServiceImpl implements DataService {

    @Resource
    private SongDataMapper songDataMapper;

    @Resource
    private CommentsMapper commentsMapper;

    @Resource
    private SingerMapper singerMapper;

    @Resource
    private AlbumMapper albumMapper;

    @Resource
    private ListsMapper listsMapper;

    @Resource
    private SongMapper songMapper;

    @Resource
    private Song_examiningMapper songExaminingMapper;

    @Override
    public SongData getSongDataBySid(String Sid) throws BadSqlGrammarException{
        SongData songData = songDataMapper.selectSongDataBySid(Sid);
        songData.setSid(Sid);
        return songData;
    }

    @Override
    public List<SongData> selectSongDataListBySongListId(String songListId) throws BadSqlGrammarException{
        List<SongData> songDataList= songDataMapper.selectSongDataListBySongListId(songListId);
        return songDataList;
    }

    @Override
    public SongListData selectSongListDataBySongListId(String songListId) throws BadSqlGrammarException{
        
        SongListData songListData = songDataMapper.selectSongListDataByListId(songListId);
        return songListData;
    }

    @Override
    public IPage<Comments> selectComments(Page<Comments> page,String target, String targetId) {
        String type = "1";
        switch (target){
            case "song":
                type = "1";
                break;
            case "album":
                type = "4";
                break;
            case "singer":
                type = "3";
                break;
            case "songlist":
                target = "list";
                type = "2";
                break;
        }
        IPage<Comments> commentsList= songDataMapper.selectComments(page, type,targetId);
//
        return commentsList;
    }


    @Override
    public IPage<SongData> search(Page<SongData> page, String key, String value) {
        String sql_key;
        switch (key){
            case "singerName":
                sql_key = "singer.singer_name";
                break;
            case "songName":
                sql_key = "song.song_name";
                break;
            case "albumName":
                sql_key = "album.album_name";
                break;
            case "all":
                sql_key = "concat(album.album_name,song.song_name,singer.singer_name)";
                break;
            default:
                sql_key = "song.song_name";
        }


        IPage<SongData> songDataList= songDataMapper.search(page, sql_key,value);

        return songDataList;
    }

    @Override
    public void addComment(String details, String target,String id, String uid) throws QueryTimeoutException {
        Comments comment = new Comments();
        comment.setCDetails(details);
        comment.setCUserid(uid);
        comment.setCTid(Integer.parseInt(id));
        String type = "1";
        switch (target){
            case "song":
                type = "1";
                break;
            case "album":
                type = "4";
                break;
            case "singer":
                type = "3";
                break;
            case "songlist":
                type = "2";
                break;
        }
        comment.setCType(type);
        commentsMapper.insert(comment);
    }

    //随机推荐8首歌曲
    @Override
    public List<SongData> randomRecommendationSong() throws BadSqlGrammarException {
        List<SongData> songDataList = songDataMapper.randomRecommendationSong();
        return songDataList;
    }

    @Override
    public List<Singer> randomRecommendationSinger() throws BadSqlGrammarException {
        List<Singer> singerList  = singerMapper.randomRecommendationSinger();
        return singerList;
    }

    @Override
    public List<AlbumData> randomRecommendationAlbum() throws BadSqlGrammarException {
        List<AlbumData> singerList  = albumMapper.randomRecommendationAlbum();
        return singerList;
    }

    @Override
    public List<ListsData> getLists(String uid) throws BadSqlGrammarException {
        List<ListsData> lists = listsMapper.getList(uid);
        return lists;
    }

    @Override
    public void collectionSongLists(String sid, String lid) {
        listsMapper.collectionSongLists(sid,lid);
    }

    @Override
    public SingerData getSingerData(String sid) {
        SingerData singerData = singerMapper.getSingerData(sid);
        return singerData;
    }

    @Override
    public List<SongData> getSingerSongData(String sid) {
        List<SongData> singerSongData = singerMapper.getSingerSongData(sid);
        return singerSongData;
    }

    @Override
    public List<CommentData> getSingerComment(String singerid) {
        List<CommentData> commentData = singerMapper.getSingerComment(singerid);
        return commentData;
    }

    @Override
    public AlbumData getAlbumData(String albumid) {
        AlbumData albumData = albumMapper.getAlbumData(albumid);
        return albumData;
    }

    @Override
    public List<SongData> getAlbumSongData(String albumid) {
        List<SongData> albumSongData = albumMapper.getAlbumSongData(albumid);
        return albumSongData;
    }

    @Override
    public List<CommentData> getAlbumCommentData(String albumid) {
        List<CommentData> commentData = albumMapper.getAlbumCommentData(albumid);
        return commentData;
    }

    @Override
    public void addList(String uid, String title,String details) {
            listsMapper.addList(uid,title,details);
    }

    @Override
    public List<ListsData> randomRecommendationLists() {
        List<ListsData> listes  = listsMapper.randomRecommendationLists();
        return listes;
    }

    @Override
    public void addSong(String songName, String albumName, String singerName, String resourcesAddress, String submitUserId) {
        Song_examing song_examing = new Song_examing();
        song_examing.setSEName(songName);
        song_examing.setSEalbum(albumName);
        song_examing.setSEsinger(singerName);
        song_examing.setSEresourcesaddress(resourcesAddress);
        song_examing.setSEuserid(Integer.valueOf(submitUserId));
        songExaminingMapper.insert(song_examing);
    }

    @Override
    public int selectListsSong(String sid, String lid) {
        List<songId> a = listsMapper.selectListsSong(sid,lid);
        int b = a.size();

        return b;
    }
}
