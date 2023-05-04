package com.manji.shardingdemo.controller;
import com.manji.shardingdemo.entity.User;
import com.manji.shardingdemo.service.UserService;
import com.manji.shardingdemo.util.SnowflakeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/sharding/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("save")
    public Integer saveUser(Long userId){
        User user = new User();
        user.setId(userId);
        user.setAge(123);
        user.setName("hbw");
        user.setCarId(123);
        return userService.save(user);
    }


    @GetMapping("bachCreate")
    public Integer bachCreate(Integer size){
        List<User> users = new ArrayList<>();
        for (Integer i = 0; i < size; i++) {
            User user = new User();
            user.setId(SnowflakeUtil.snowflake());
            user.setAge(10);
            user.setName(createRandomStr1(10));
            user.setCarId(123);
            users.add(user);
        }
        return userService.bachSave(users);
    }

    @GetMapping("list")
    public List<User> listUserByPage(Integer pageSize, Integer pageNumber){

        return userService.listUsers(pageSize, pageNumber);
    }

    @GetMapping("list2")
    public List<User> listUserByPage2(Integer pageSize, Integer pageNumber){

        return userService.listUsers2(pageSize, pageNumber);
    }

    public static String createRandomStr1(int length){

        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        Random random = new Random();

        StringBuffer stringBuffer = new StringBuffer();

        for (int i = 0; i < length; i++) {

            int number = random.nextInt(62);

            stringBuffer.append(str.charAt(number));

        }

        return stringBuffer.toString();

    }

}
