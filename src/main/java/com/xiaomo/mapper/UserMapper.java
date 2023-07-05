package com.xiaomo.mapper;

import com.xiaomo.domain.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("select * from user")
    List<User> findAll();

    @Insert("insert into user (username, password, name, createTime) values (#{username}, #{password}, #{name}, #{createTime})")
    void saveUser(User user);

    @Update("update user set username=#{username}, password=#{password}, name=#{name}, createTime=#{createTime} where id=#{id}")
    void updateUser(User user);

    @Delete("delete from user where id=#{id}")
    void deleteUser(int id);
}
