package org.example.service;

import org.example.pojo.User;
import org.example.pojo.UserLog;

import java.util.List;

public interface UserService {

    // 根据用户名查询对象
    User findByUsername(String username);

    //注册
    void register(String username, String password, String emailAddress, String phoneNumber);

    void addLogInfo(String username, String loginTime, String loginIp, String operationType);

    void update(User user);

    void updatePwd(String newPwd, String username);

    public List<UserLog> getLogInfoByUsername(String username);


}
