package com.easy.work.user;

import com.easy.work.api.service.UserService;
import com.easy.work.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTests {

    @Autowired
    private UserService userService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void saveUser() {
        User user = new User();
        user.setUserName("张三222");
        user.setTrueName("张三丰");
        user.setAge(26);
        user.setPassword("123456");
        user.setSex("男");
        user.setStatus(1);
        try {
            userService.save(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateUser() {
        User user = new User();
        user.setId(21L);
        user.setTrueName("测试123");
        try {
            long a = userService.update(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

