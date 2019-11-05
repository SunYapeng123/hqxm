package com.baizhi.service.Impl;

import com.baizhi.dao.AlbumDao;
import com.baizhi.dao.ChapterDao;
import com.baizhi.dao.UserDao;
import com.baizhi.entity.Banner;
import com.baizhi.entity.Chapter;
import com.baizhi.entity.User;
import com.baizhi.service.ChapterService;
import com.baizhi.service.UserService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

@Service
@Transactional
public class ChapterServiceImpl implements ChapterService {


    @Autowired
    private ChapterDao chapterDao;


    @Override
    public Map<String, Object> selectAll(Integer page, Integer rows,String albumId) {

        Chapter chapter = new Chapter();

        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);

        List<Chapter> list = chapterDao.selectByRowBounds(chapter, rowBounds);

        int count = chapterDao.selectCount(chapter);

        Map<String, Object> map = new HashMap<>();

        map.put("page",page);
        map.put("rows",list);
        map.put("total",count%rows==0?count/rows:count/rows+1);
        map.put("count",count);


        return map;
    }


    @Override
    public String add(Chapter chapter){
        chapter.setId(UUID.randomUUID().toString());
        chapter.setCreateDate(new Date());
        int i = chapterDao.insertSelective(chapter);
        if (i == 0){
            throw new RuntimeException("添加失败");
        }
        return chapter.getId();
    }

    @Override
    public void edit(Chapter chapter){
        int i = chapterDao.updateByPrimaryKeySelective(chapter);
        if (i == 0){
            throw new RuntimeException("修改章节失败");
        }
    }




}
