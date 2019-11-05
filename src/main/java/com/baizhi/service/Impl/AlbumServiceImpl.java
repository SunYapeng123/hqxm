package com.baizhi.service.Impl;

import com.baizhi.dao.AlbumDao;
import com.baizhi.dao.StarDao;
import com.baizhi.entity.Album;
import com.baizhi.entity.Star;
import com.baizhi.service.AlbumService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {


    @Autowired
    private AlbumDao albumDao;

    @Autowired
    private StarDao starDao;


    @Override
    public Map<String, Object> selectAll(Integer page, Integer rows) {

        Album album = new Album();

        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);

        List<Album> list = albumDao.selectByRowBounds(album, rowBounds);

        for (Album a : list) {
            Star star = starDao.selectByPrimaryKey(a.getStarId());
            a.setStar(star);
        }


        int count = albumDao.selectCount(album);

        HashMap<String, Object> map = new HashMap<>();

        map.put("page",page);
        map.put("rows",list);
        map.put("total",count%rows==0?count/rows:count/rows+1);
        map.put("count",count);

        return map;
    }


    @Override
    public String add(Album album){
        album.setId(UUID.randomUUID().toString());
        album.setCreateDate(new Date());
        album.setCount(0);
        int i = albumDao.insertSelective(album);
        if (i == 0){
            throw new RuntimeException("添加失败");
        }
        return album.getId();
    }


    @Override
    public void edit(Album album){
        if ("".equals(album.getCover())){
            album.setCover(null);
        }

        try {
            albumDao.updateByPrimaryKeySelective(album);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("修改失败");
        }
    }

    @Override
    public void del(String id, HttpServletRequest request){
        Album album = albumDao.selectByPrimaryKey(id);
        int i = albumDao.deleteByPrimaryKey(id);
        if(i == 0){
            throw new RuntimeException("删除失败");
        }else{
            String cover = album.getCover();
            File file = new File(request.getServletContext().getRealPath("/banner/img"), cover);
            boolean b = file.delete();
            if(b == false){
                throw new RuntimeException("删除文件失败");
            }
        }
    }

    @Override
    public Album selectOne(String id){
        Album album = albumDao.selectByPrimaryKey(id);
        return album;
    }


    @Override
    public List<Album> selectAll(){
        List<Album> list = albumDao.selectAll();
        return list;
    }
}
