package com.baizhi.controller;

import com.baizhi.entity.Album;
import com.baizhi.entity.Article;
import com.baizhi.entity.Banner;
import com.baizhi.service.AlbumService;
import com.baizhi.service.ArticleService;
import com.baizhi.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("cmfz")
public class FrontController {

    @Autowired
    private AlbumService albumService;

    @Autowired
    private BannerService bannerService;

    @Autowired
    private ArticleService articleService;

    @RequestMapping("first_page")
    public Map<String,Object> firstPage(String uid, String type, String sub_type){
        HashMap<String, Object> map = new HashMap<>();
        if ("all".equals(type)){
            List<Banner> banners = bannerService.selectAll();
            List<Album> albums = albumService.selectAll();
            List<Article> articles = articleService.selectAll();
            map.put("header",banners);
            map.put("body",albums);
            map.put("jiao",articles);

        }
        if ("wen".equals(type)){
            List<Album> albums = albumService.selectAll();
            map.put("album",albums);
        }
        if ("si".equals(type)){
            if ("ssyj".equals(sub_type)){

            }
            if ("xmfy".equals(sub_type)){

            }
        }
        return map;
    }
}
