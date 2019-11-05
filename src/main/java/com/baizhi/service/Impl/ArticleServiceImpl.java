package com.baizhi.service.Impl;


import com.baizhi.dao.ArticleDao;
import com.baizhi.entity.Article;
import com.baizhi.service.ArticleService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {


    @Autowired
    private ArticleDao articleDao;

    @Override
    public Map<String, Object> selectAll(Integer page, Integer rows) {

        Article article = new Article();
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);


        List list = articleDao.selectByRowBounds(article, rowBounds);

        int count = articleDao.selectCount(article);

        Map<String, Object> map = new HashMap<>();

        map.put("page",page);
        map.put("rows",list);
        map.put("total",count%rows==0?count/rows:count/rows);
        map.put("count",count);

        return map;
    }

    @Override
    public String add(Article article){
        article.setId(UUID.randomUUID().toString());
        article.setCreatDate(new Date());
        int i = articleDao.insertSelective(article);
        if (i == 0){
            throw new RuntimeException("添加失败");
        }
        return article.getId();
    }

    @Override
    public void edit(Article article){
        try {
            articleDao.updateByPrimaryKeySelective(article);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("修改失败");
        }

    }


    @Override
    public List<Article> selectAll(){
        List<Article> list = articleDao.selectAll();
        return list;
    }


}
