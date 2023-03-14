package com.Controller;

import com.Pojo.*;
import com.Vo.RespBean;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.BindException;

@RequestMapping("/data")
public interface DataController {

    @RequestMapping("/hello")
    String hello();

    @PostMapping("/getSongData/{singid}")
    RespBean getSongData(@PathVariable("singid") String singid) throws BindException;

    @PostMapping("/getSongListData/{songListId}")
    RespBean getSongListData(@PathVariable("songListId")String songListId);

    @PostMapping("/getCommentList")
    RespBean getSongCommentList(@RequestBody @Valid PageForm pageForm, BindingResult bindResult);

    @PostMapping("/search")
    RespBean search(@RequestBody @Valid SearchForm searchForm, BindingResult result);

    @PostMapping("/addComment")
    RespBean addComment(@RequestBody @Valid CommentForm commentForm,BindingResult result);
    //si个随机推荐
    @PostMapping("/randomRecommendationSong")
    RespBean randomRecommendationSong();

    @PostMapping("/randomRecommendationAlbum")
    RespBean randomRecommendationAlbum();

    @PostMapping("/randomRecommendationSinger")
    RespBean randomRecommendationSinger();

    @PostMapping("/randomRecommendationLists")
    RespBean randomRecommendationLists();

    @PostMapping("/getLists")
    RespBean getLists();

    /*上下两个都是用用户id获取歌单
    上面是通过token拿uid
    下面是通过参数拿uid
    */
    @PostMapping("/getListes")
    RespBean getListes(@RequestBody @Valid UserIdForm userIdForm, BindingResult bindResult);

    @PostMapping("/CollectionSongLists")
    RespBean collectionSongLists(@RequestBody @Valid CollectionSongForm collectionSongForm, BindingResult bindResult);
    //查歌手详情
    @PostMapping("/getSingerData/{singerid}")
    RespBean getSingerData(@PathVariable("singerid")String singerid);
    //查专辑详情
    @PostMapping("/getAlbumData/{albumid}")
    RespBean getAlbumData(@PathVariable("albumid")String albumid);
    //参数不带id新增歌单
    @PostMapping("/addListes")
    RespBean addListes(@RequestBody @Valid AddListesForm addListesForm, BindingResult bindResult);
    //参数带id
    @PostMapping("/addLists")
    RespBean addLists(@RequestBody @Valid AddListsForm addListsForm, BindingResult bindResult);

    @PostMapping("/addSong")
    RespBean addExaminingSong(@RequestBody @Valid ExaminingSongForm examiniwngSongForm);

}
