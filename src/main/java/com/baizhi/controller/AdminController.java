package com.baizhi.controller;

import com.baizhi.aliyun.SendMessage;
import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RequestMapping("admin")
@RestController
public class AdminController {

    @Autowired
    private AdminService adminService;

    @RequestMapping("login")
    public Map<String, Object> login(Admin admin, String inputCode, HttpServletRequest request){
        HashMap<String, Object> map = new HashMap<>();
        try {
            adminService.login(admin,inputCode,request);
            map.put("status",true);
        } catch (Exception e) {
            map.put("status",false);
            map.put("message",e.getMessage());
        }
        return map;
    }


    @RequestMapping("add")
    public void add(Admin admin,String phone,String code){
        String verifyCode = String.valueOf(new Random().nextInt(899999) + 100000);

        try {
            System.out.println("开始发送短信");
            SendMessage.sendMessage(verifyCode,phone);
            System.out.println("短信发送成功");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("短信发送失败");
        }
        if (code == verifyCode){
            adminService.add(admin);
        }else {
            throw new RuntimeException("验证码不正确");
        }

    }
}
