package com.Controller.Impl;

import com.Constant.Enum.RespBeanEnum;
import com.Controller.DataController;
import com.Pojo.*;
import com.Pojo.union.*;
import com.Service.DataService;
import com.Validation.annotation.FormData;
import com.Vo.RespBean;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.binding.BindingException;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.BindException;
import java.util.*;

@RestController
public class DataControllerImpl implements DataController {

    @Resource
    private DataService dataService;

    @Override
    public String hello() {
        return "hello";
    }

    @Override
    public RespBean getSongData(String singid) throws BindException{
        Map<String,Object> resultMap = new HashMap<>();
        if(!StringUtils.hasText(singid) || singid.equals(" ")){
            resultMap.put("msg","参数不正确");
            return RespBean.error(RespBeanEnum.ERROR_400,resultMap);
        }
        SongData songData = dataService.getSongDataBySid(singid);
        if(Objects.isNull(songData)){
            resultMap.put("msg","资源未找到");
            return RespBean.error(RespBeanEnum.ERROR_404,resultMap);
        }else {
            resultMap.put("songData",songData);
            return RespBean.success(RespBeanEnum.SUCCESS_200,resultMap);
        }
    }

    //TODO pageForm校验
    @Override
//    支持album、singer、songlist、song获取评论
    public RespBean getSongCommentList(PageForm pageForm, BindingResult result){
        if (result.hasErrors()) {
            Map<String,Object> errorMap = new HashMap<>();
            errorMap.put("msg","参数错误");
            result.getFieldErrors().forEach(fieldError -> errorMap.put(fieldError.getField(),fieldError.getDefaultMessage()));
            return RespBean.error(RespBeanEnum.ERROR_400,errorMap);
        }
        Map<String,Object> resultMap = new HashMap<>();
        Page<Comments> page;
        try {
            page = new Page<>(Integer.parseInt(pageForm.getCurrent()),Integer.parseInt(pageForm.getSize()));
        }catch (Exception e){
            resultMap.put("msg","参数不合法");
            return RespBean.success(RespBeanEnum.ERROR_400,resultMap);
        }

        IPage<Comments> commentList = dataService.selectComments(page,pageForm.getTarget(),pageForm.getTargetId());
        resultMap.put("current",pageForm.getCurrent());
        resultMap.put("size",pageForm.getSize());
        resultMap.put("total",commentList.getTotal());
        resultMap.put("commentList",commentList.getRecords());
        return RespBean.success(RespBeanEnum.SUCCESS_200,resultMap);
    }



    @Override
    public RespBean getSongListData(String songListId) {
        Map<String,Object> resultMap = new HashMap<>();
        List<SongData> songDataList = dataService.selectSongDataListBySongListId(songListId);
        SongListData songListData = dataService.selectSongListDataBySongListId(songListId);
        if(Objects.isNull(songListData)){
            resultMap.put("msg","资源未找到");
            return RespBean.error(RespBeanEnum.ERROR_404,resultMap);
        }else{
            resultMap.put("songListData",songListData);
            resultMap.put("songDataList",songDataList);
            return RespBean.success(RespBeanEnum.SUCCESS_200,resultMap);
        }
    }
    @Override
    //支持通过singerName、songName、albumName、all作为key进行模糊查询
    public RespBean search(SearchForm searchForm, BindingResult result) {
        if (result.hasErrors()) {
            Map<String,Object> errorMap = new HashMap<>();
            errorMap.put("msg","参数不正确,搜索失败");
            result.getFieldErrors().forEach(fieldError -> errorMap.put(fieldError.getField(),fieldError.getDefaultMessage()));
            return RespBean.error(RespBeanEnum.ERROR_400,errorMap);
        }
        Map<String,Object> resultMap = new HashMap<>();
        Page<SongData> page;
        IPage<SongData> songDataList = null;
        try {
            page = new Page<>(Integer.parseInt(searchForm.getCurrent()),Integer.parseInt(searchForm.getSize()));
            songDataList = dataService.search(page,searchForm.getKey(),searchForm.getValue());

        }catch (NumberFormatException e){
            resultMap.put("msg","参数不合法");
            return RespBean.success(RespBeanEnum.ERROR_400,resultMap);
        }catch (MyBatisSystemException e){
            resultMap.put("msg","数据库查询失败");
            return RespBean.success(RespBeanEnum.ERROR_500,resultMap);
        }

        resultMap.put("current",searchForm.getCurrent());
        resultMap.put("size",searchForm.getSize());
        resultMap.put("total",songDataList.getTotal());
        resultMap.put("songDataList",songDataList.getRecords());
        return RespBean.success(RespBeanEnum.SUCCESS_200,resultMap);
    }

    @Override
//  支持album、singer、songlist、song新增评论
    public RespBean addComment(CommentForm commentForm, BindingResult result) {
        if (result.hasErrors()) {
            Map<String,Object> errorMap = new HashMap<>();
            errorMap.put("msg","参数不正确,新增评论失败");
            result.getFieldErrors().forEach(fieldError -> errorMap.put(fieldError.getField(),fieldError.getDefaultMessage()));
            return RespBean.error(RespBeanEnum.ERROR_400,errorMap);
        }
        Map<String,Object> resultMap = new HashMap<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String redisKey = (String) authentication.getPrincipal();
        String Uid = new StringTokenizer(redisKey,"loginID:").nextToken();
        try {
            dataService.addComment(commentForm.getDetails(),commentForm.getTarget(), commentForm.getTargetId(), Uid);
            return RespBean.success(RespBeanEnum.SUCCESS_200);
        }catch (Exception e){
            return RespBean.error(RespBeanEnum.ERROR_500);
        }

    }

    @Override
    public RespBean randomRecommendationSong() {
        Map<String,Object> resultMap = new HashMap<>();
        List<SongData> songDataList = dataService.randomRecommendationSong();
        if(Objects.isNull(songDataList)){
            resultMap.put("msg","资源未找到");
            return RespBean.error(RespBeanEnum.ERROR_404,resultMap);
        }else {
            resultMap.put("randomSongDataList",songDataList);
            return RespBean.success(RespBeanEnum.SUCCESS_200,resultMap);
        }
    }

    @Override
    public RespBean randomRecommendationAlbum() {
        Map<String,Object> resultMap = new HashMap<>();
        List<AlbumData> AlbumDataList = dataService.randomRecommendationAlbum();
        if(Objects.isNull(AlbumDataList)){
            resultMap.put("msg","资源未找到");
            return RespBean.error(RespBeanEnum.ERROR_404,resultMap);
        }else {
            resultMap.put("randomAlbumDataList",AlbumDataList);
            return RespBean.success(RespBeanEnum.SUCCESS_200,resultMap);
        }
    }

    @Override
    public RespBean randomRecommendationSinger() {
        Map<String,Object> resultMap = new HashMap<>();
        List<Singer> SingerList = dataService.randomRecommendationSinger();
        if(Objects.isNull(SingerList)){
            resultMap.put("msg","资源未找到");
            return RespBean.error(RespBeanEnum.ERROR_404,resultMap);
        }else {
            resultMap.put("randomSingerList",SingerList);
            return RespBean.success(RespBeanEnum.SUCCESS_200,resultMap);
        }
    }

    @Override
    public RespBean randomRecommendationLists() {
        Map<String,Object> resultMap = new HashMap<>();
        List<ListsData> Listes = dataService.randomRecommendationLists();
        if(Objects.isNull(Listes)){
            resultMap.put("msg","资源未找到");
            return RespBean.error(RespBeanEnum.ERROR_404,resultMap);
        }else {
            resultMap.put("randomListes",Listes);
            return RespBean.success(RespBeanEnum.SUCCESS_200,resultMap);
        }
    }

    @Override
    public RespBean getLists() {
        Map<String,Object> resultMap = new HashMap<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String redisKey = (String) authentication.getPrincipal();
        String uid = new StringTokenizer(redisKey,"loginID:").nextToken();
        List<ListsData> listsList = dataService.getLists(uid);
        if(Objects.isNull(listsList)){
            resultMap.put("msg","资源未找到");
            return RespBean.error(RespBeanEnum.ERROR_404,resultMap);
        }else {
            resultMap.put("listsList",listsList);
            return RespBean.success(RespBeanEnum.SUCCESS_200,resultMap);
        }
    }

    @Override
    public RespBean getListes(UserIdForm userIdForm, BindingResult bindResult) {
        if (bindResult.hasErrors()) {
            Map<String,Object> errorMap = new HashMap<>();
            errorMap.put("msg","参数错误");
            bindResult.getFieldErrors().forEach(fieldError -> errorMap.put(fieldError.getField(),fieldError.getDefaultMessage()));
            return RespBean.error(RespBeanEnum.ERROR_400,errorMap);
        }
        Map<String,Object> resultMap = new HashMap<>();
        String uid = userIdForm.getUid();
        List<ListsData> listsList = dataService.getLists(uid);
        if(Objects.isNull(listsList)){
            resultMap.put("msg","资源未找到");
            return RespBean.error(RespBeanEnum.ERROR_404,resultMap);
        }else {
            resultMap.put("listsList",listsList);
            return RespBean.success(RespBeanEnum.SUCCESS_200,resultMap);
        }
    }

    @Override
    public RespBean collectionSongLists(CollectionSongForm collectionSongForm, BindingResult bindResult) {
        if (bindResult.hasErrors()) {
            Map<String,Object> errorMap = new HashMap<>();
            errorMap.put("msg","参数错误");
            bindResult.getFieldErrors().forEach(fieldError -> errorMap.put(fieldError.getField(),fieldError.getDefaultMessage()));
            return RespBean.error(RespBeanEnum.ERROR_400,errorMap);
        }
        String lid = collectionSongForm.getLid();
        String sid = collectionSongForm.getSid();
        try {
            int a = dataService.selectListsSong(sid,lid);
            if(a!= 0){
                return RespBean.error(RespBeanEnum.ERROR_500,"歌曲已存在");
            }
            dataService.collectionSongLists(sid,lid);


            return RespBean.success(RespBeanEnum.SUCCESS_200);
        }catch (Exception e){
            return RespBean.error(RespBeanEnum.ERROR_500);
        }
    }

    @Override
    public RespBean getSingerData(String singerid) {
        Map<String,Object> resultMap = new HashMap<>();
        SingerData singerData = dataService.getSingerData(singerid);
        List<SongData> songListData = dataService.getSingerSongData(singerid);
        List<CommentData> commentData = dataService.getSingerComment(singerid);
        if(Objects.isNull(songListData)&&Objects.isNull(commentData)){
            resultMap.put("msg","资源未找到");
            return RespBean.error(RespBeanEnum.ERROR_404,resultMap);
        }else{
            resultMap.put("singerData",singerData);
            resultMap.put("songListData",songListData);
            resultMap.put("commentData",commentData);
            return RespBean.success(RespBeanEnum.SUCCESS_200,resultMap);
        }
    }

    @Override
    public RespBean getAlbumData(String albumid) {
        Map<String,Object> resultMap = new HashMap<>();
        AlbumData albumData = dataService.getAlbumData(albumid);
        //专辑里面有啥歌
        List<SongData> albumSongData = dataService.getAlbumSongData(albumid);
        //专辑评论
        List<CommentData> albumcommentData = dataService.getAlbumCommentData(albumid);
        if(Objects.isNull(albumData)){
            resultMap.put("msg","资源未找到");
            return RespBean.error(RespBeanEnum.ERROR_404,resultMap);
        }else{
            resultMap.put("albumData",albumData);
            resultMap.put("albumSongData",albumSongData);
            resultMap.put("albumcommentData",albumcommentData);
            return RespBean.success(RespBeanEnum.SUCCESS_200,resultMap);
        }
    }

    @Override
    public RespBean addListes(AddListesForm addListesForm, BindingResult bindResult) {
        if (bindResult.hasErrors()) {
            Map<String,Object> errorMap = new HashMap<>();
            errorMap.put("msg","参数错误");
            bindResult.getFieldErrors().forEach(fieldError -> errorMap.put(fieldError.getField(),fieldError.getDefaultMessage()));
            return RespBean.error(RespBeanEnum.ERROR_400,errorMap);
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String redisKey = (String) authentication.getPrincipal();
        String uid = new StringTokenizer(redisKey,"loginID:").nextToken();
        String title = addListesForm.getTitle();
        String details = addListesForm.getDetails();
        try {
            dataService.addList(uid,title,details);
            return RespBean.success(RespBeanEnum.SUCCESS_200);
        }catch (Exception e){
            return RespBean.error(RespBeanEnum.ERROR_500);
        }
    }

    @Override
    public RespBean addLists(AddListsForm addListsForm, BindingResult bindResult) {
        if (bindResult.hasErrors()) {
            Map<String,Object> errorMap = new HashMap<>();
            errorMap.put("msg","参数错误");
            bindResult.getFieldErrors().forEach(fieldError -> errorMap.put(fieldError.getField(),fieldError.getDefaultMessage()));
            return RespBean.error(RespBeanEnum.ERROR_400,errorMap);
        }
        String title = addListsForm.getTitle();
        String uid = addListsForm.getUid();
        String details = addListsForm.getDetails();
        try {
            dataService.addList(uid,title,details);
            return RespBean.success(RespBeanEnum.SUCCESS_200);
        }catch (Exception e){
            return RespBean.error(RespBeanEnum.ERROR_500);
        }
    }

    @Override
    public RespBean addExaminingSong(ExaminingSongForm examiningSongForm) {
        try {
            dataService.addSong(examiningSongForm.getSongName(),examiningSongForm.getAlbumName(),examiningSongForm.getSingerName()
            ,examiningSongForm.getResourcesAddress(),examiningSongForm.getSubmitUserid()
            );
            return RespBean.success(RespBeanEnum.SUCCESS_200);
        }catch (Exception e){
            return RespBean.error(RespBeanEnum.ERROR_500);
        }
    }


}
