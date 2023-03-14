package com.Mapper;

import com.Pojo.ExaminingSongForm;
import com.Pojo.Song;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;

public interface SongMapper  extends BaseMapper<Song> {

    @Insert("insert into ")
    void insert(ExaminingSongForm examiningSongForm);
}
