package com.baizhi.service;
import com.baizhi.entity.Star;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface StarService {
    Map<String,Object> selectAll(Integer page, Integer rows);

    String add(Star star);

    void edit(Star star);

    void del(String id, HttpServletRequest request);

    List<Star> getAllStarForSelect();
}
