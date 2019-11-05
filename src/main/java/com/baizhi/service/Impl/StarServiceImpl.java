package com.baizhi.service.Impl;

import com.baizhi.dao.BannerDao;
import com.baizhi.dao.StarDao;
import com.baizhi.entity.Banner;
import com.baizhi.entity.Star;
import com.baizhi.service.BannerService;
import com.baizhi.service.StarService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

@Service
@Transactional
public class StarServiceImpl implements StarService {


    @Autowired
    private StarDao starDao;

    @Override
    public Map<String, Object> selectAll(Integer page, Integer rows) {

        Star star = new Star();

        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);

        List<Star> list = starDao.selectByRowBounds(star, rowBounds);

        int count = starDao.selectCount(star);

        Map<String, Object> map = new HashMap<>();

        map.put("page",page);
        map.put("rows",list);
        map.put("total",count%rows==0?count/rows:count/rows+1);
        map.put("count",count);


        return map;
    }



    @Override
    public String add(Star star){
        star.setId(UUID.randomUUID().toString());
        star.setBir(new Date());
        int i = starDao.insertSelective(star);
        if (i == 0){
            throw new RuntimeException("添加失败");
        }
        return star.getId();
    }


    @Override
    public void edit(Star star){
        if ("".equals(star.getPhoto())){
            star.setPhoto(null);
        }

        try {
            starDao.updateByPrimaryKeySelective(star);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("修改失败");
        }
    }

    @Override
    public void del(String id, HttpServletRequest request){
        Star star = starDao.selectByPrimaryKey(id);
        int i = starDao.deleteByPrimaryKey(id);
        if(i == 0){
            throw new RuntimeException("删除失败");
        }else{
            String cover = star.getPhoto();
            File file = new File(request.getServletContext().getRealPath("/star/img"), cover);
            boolean b = file.delete();
            if(b == false){
                throw new RuntimeException("删除文件失败");
            }
        }
    }

    @Override
    public List<Star> getAllStarForSelect(){
        List<Star> list = starDao.selectAll();
        return list;
    }
}
