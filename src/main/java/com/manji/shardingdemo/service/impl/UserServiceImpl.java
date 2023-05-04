package com.manji.shardingdemo.service.impl;

import com.manji.shardingdemo.entity.User;
import com.manji.shardingdemo.mapper.UserMapper;
import com.manji.shardingdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Integer save(User user) {
        return userMapper.save(user);
    }

    @Override
    public Integer bachSave(List<User> users) {

        return userMapper.bachSave(users);
    }

    @Override
    public List<User> listUsers(Integer pageSize, Integer pageNumber) {

        return userMapper.listPage((pageNumber - 1) * pageSize, pageSize);
    }

    @Override
    public List<User> listUsers2(Integer pageSize, Integer pageNumber) {
        return userMapper.listPage2((pageNumber - 1) * pageSize, pageSize);
    }
}