package com.xiaomo.mapper;

import com.xiaomo.domain.User;
import com.xiaomo.handler.MyHandler;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.Date;
import java.util.List;

@Mapper
public interface UserMapper {
//    @Select("select * from user")
    List<User> findAll();

//    @Insert("insert into user (username, password, name, createTime) values (#{username}, #{password}, #{name}, #{createTime})")
    void saveUser(User user);

//    @Update("update user set username=#{username}, password=#{password}, name=#{name}, createTime=#{createTime} where id=#{id}")
    void updateUser(User user);

//    @Delete("delete from user where id=#{id}")
    void deleteUser(int id);

    User getUserByName(User user);

    List<User> getUserById(int id);

    /*@Select("select * from user where username = #{username}")
    @Results({
            @Result(property = "createTime", column = "createTime", javaType = Date.class, jdbcType = JdbcType.TIMESTAMP, typeHandler = MyHandler.class)
    })*/
    User getUserByNameString(String username);

    User getUserByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

}
