package org.example.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.pojo.User;

@Mapper
public interface UserMapper {
    @Select("select * from user_info where username=#{username}")
    User findByUsername(String username);

    @Insert("insert into user_info(username, password, email_address, phone_number)" +
            " values(#{username}, #{password}, #{emailAddress}, #{phoneNumber})")
    void add(String username, String password, String emailAddress, String phoneNumber);
}
