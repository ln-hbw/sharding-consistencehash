package com.manji.shardingdemo.mapper;

import com.manji.shardingdemo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserMapper {

    int save(@Param("user") User user);

    Integer bachSave(@Param("list") List<User> users);

    List<User> listPage(@Param("start") int start, @Param("size") Integer size);

    List<User> listPage2(@Param("start") int start, @Param("size") Integer size);

}
