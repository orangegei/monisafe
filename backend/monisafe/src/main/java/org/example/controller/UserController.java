package org.example.controller;

import org.example.pojo.Result;
import org.example.pojo.User;
import org.example.service.UserService;
import org.example.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result register(String username, String password, String emailAddress, String phoneNumber) {
        // 查询用户
        User u = userService.findByUsername(username);
        if (u==null) {
            // 没有占用，注册
            userService.register(username, password, emailAddress, phoneNumber);
            return Result.success();
        } else {
            return Result.error("用户名已被占用");
        }

    }

    @PostMapping("/login")
    public Result<String> login(String username, String password) {
        // 根据用户名查询用户
        User loginUser = userService.findByUsername(username);

        // 判断该用户是否存在
        if (loginUser==null) {
            return Result.error("用户名错误");
        }

        // 判断密码是否正确
        if (loginUser.getPassword().equals(Md5Util.getMD5String(password))) {
            // 登录成功
            return Result.success("jwt token令牌");
        }

        return Result.error("密码错误");

    }

}
