package org.example.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.pojo.User;
import org.example.pojo.UserLog;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("select * from user_info where username=#{username}")
    User findByUsername(String username);

    @Insert("insert into user_info(username, password, email_address, phone_number)" +
            " values(#{username}, #{password}, #{emailAddress}, #{phoneNumber})")
    void add(String username, String password, String emailAddress, String phoneNumber);

    @Insert("INSERT INTO user_log_info (username, login_time, login_ip, operation_type) VALUES (#{username}, #{loginTime}, #{loginIp}, #{operationType})")
    void addLogInfo(String username, String loginTime, String loginIp, String operationType);

    @Update("update user_info set email_address=#{emailAddress}, phone_number=#{phoneNumber} where username=#{username}")
    void update(User user);

    @Update("update user_info set password=#{password} where username=#{username}")
    void updatePwd(String password, String username);

    @Select("SELECT * FROM user_log_info WHERE username = #{username}")
    List<UserLog> getLogInfoByUsername(String username);
}
