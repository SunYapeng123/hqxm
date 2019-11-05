package com.baizhi;

import com.baizhi.dao.AdminDao;
import com.baizhi.entity.Admin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class HqxmApplicationTests {

    @Autowired
    private AdminDao adminDao;

    @Test
    void contextLoads() {

        List<Admin> list = adminDao.selectAll();
        for (Admin admin : list) {
            System.out.println(list);
        }
    }

}
