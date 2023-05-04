package com.manji.shardingdemo.service;

import com.manji.shardingdemo.entity.User;

import java.util.List;

public interface UserService {

    Integer save(User user);

    Integer bachSave(List<User> users);

    List<User> listUsers(Integer pageSize, Integer pageNumber);

    List<User> listUsers2(Integer pageSize, Integer pageNumber);
}