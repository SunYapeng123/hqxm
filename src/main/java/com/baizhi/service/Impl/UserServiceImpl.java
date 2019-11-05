package com.baizhi.service.Impl;

import com.baizhi.dao.StarDao;
import com.baizhi.dao.UserDao;
import com.baizhi.entity.Star;
import com.baizhi.entity.User;
import com.baizhi.service.StarService;
import com.baizhi.service.UserService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserServiceImpl implements UserService {


    @Autowired
    private UserDao userDao;

    @Override
    public Map<String, Object> selectAll(Integer page, Integer rows) {

        User user = new User();

        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);

        List<User> list = userDao.selectByRowBounds(user, rowBounds);

        int count = userDao.selectCount(user);

        Map<String, Object> map = new HashMap<>();

        map.put("page",page);
        map.put("rows",list);
        map.put("total",count%rows==0?count/rows:count/rows+1);
        map.put("count",count);


        return map;
    }

    @Override
    public List<User> export(){
        return userDao.selectAll();
    }
}
