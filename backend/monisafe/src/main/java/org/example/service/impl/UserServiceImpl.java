package org.example.service.impl;

import org.example.mapper.UserMapper;
import org.example.pojo.User;
import org.example.pojo.UserLog;
import org.example.service.UserService;
import org.example.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByUsername(String username) {
        User u = userMapper.findByUsername(username);
        return u;
    }

    @Override
    public void register(String username, String password, String emailAddress, String phoneNumber) {
        // 加密
        String md5Password = Md5Util.getMD5String(password);

        // 添加
        userMapper.add(username, md5Password, emailAddress, phoneNumber);

    }

    @Override
    public void addLogInfo(String username, String loginTime, String loginIp, String operationType) {
        userMapper.addLogInfo(username, loginTime, loginIp, operationType);
    }

    @Override
    public void update(User user) {
        userMapper.update(user);
    }

    @Override
    public void updatePwd(String newPwd, String username) {
        userMapper.updatePwd(Md5Util.getMD5String(newPwd), username);
    }

    @Override
    public List<UserLog> getLogInfoByUsername(String username) {
        return userMapper.getLogInfoByUsername(username);
    }
}
