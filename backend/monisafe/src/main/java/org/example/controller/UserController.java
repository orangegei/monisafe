package org.example.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.example.DTO.ForgetPasswordForm;
import org.example.DTO.LoginForm;
import org.example.DTO.RegisterForm;
import org.example.pojo.Result;
import org.example.pojo.User;
import org.example.pojo.UserLog;
import org.example.service.EmailService;
import org.example.service.UserService;
import org.example.utils.JwtUtil;
import org.example.utils.Md5Util;
import org.example.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@Validated
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;

    @PostMapping("/register")
    public Result register(@RequestBody @Validated RegisterForm registerForm) {
        String username = registerForm.getUsername();
        String password = registerForm.getPassword();
        String emailAddress = registerForm.getEmailAddress();
        String phoneNumber = registerForm.getPhoneNumber();
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
    public Result<String> login(@RequestBody LoginForm loginForm, HttpServletRequest request) {
        String username = loginForm.getUsername();
        String password = loginForm.getPassword();

        // 根据用户名查询用户
        User loginUser = userService.findByUsername(username);
        System.out.println(username);
        System.out.println(password);

        // 判断该用户是否存在
        if (loginUser==null) {
            return Result.error("用户名错误");
        }

        System.out.println(loginUser.getPassword());
        System.out.println(Md5Util.getMD5String(password));

        // 判断密码是否正确
        if (loginUser.getPassword().equals(Md5Util.getMD5String(password))) {
            // 登录成功
            Map<String, Object> claims = new HashMap<>();
            claims.put("username", loginUser.getUsername());
            claims.put("emailAddress", loginUser.getEmailAddress());
            claims.put("userId", loginUser.getUserId());

            String token = JwtUtil.genToken(claims);
            // 获取登录时间和IP
            String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            String loginIp = request.getRemoteAddr();
            String operationType = "登录";
            userService.addLogInfo(username, loginTime, loginIp, operationType); // 操作详情
            String emailContent = "Hello " + username + ", your token is " + token;
            emailService.sendSimpleMail("1659227126@qq.com", "登录成功", emailContent);

            return Result.success(token);
        }

        return Result.error("密码错误");
    }

    @PatchMapping("/forgetPassword")
    public Result resetPassword(@RequestBody @Validated ForgetPasswordForm forgetPasswordForm, HttpServletRequest request) {
        String username = forgetPasswordForm.getUsername();
        String emailAddress = forgetPasswordForm.getEmailAddress();
        String phoneNumber = forgetPasswordForm.getPhoneNumber();
        String newPassword = forgetPasswordForm.getNewPassword();

        User user = userService.findByUsername(username);
        if (user==null) {
            return Result.error("用户不存在");
        }

        if (!user.getPhoneNumber().equals(phoneNumber) || !user.getEmailAddress().equals(emailAddress)) {
            return Result.error("手机号码或邮箱不匹配");
        }

        userService.updatePwd(newPassword, username);
        // 获取修改密码时间和IP
        String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String loginIp = request.getRemoteAddr();
        String operationType = "修改密码";
        userService.addLogInfo(username, loginTime, loginIp, operationType); // 操作详情
        return Result.success();
    }

    @PutMapping("/settings/updateInfo")
    public Result update(@RequestBody @Validated User user, HttpServletRequest request) {
        userService.update(user);
        // 获取修改密码时间和IP
        String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String loginIp = request.getRemoteAddr();
        String operationType = "修改密码";
        userService.addLogInfo(user.getUsername(), loginTime, loginIp, operationType); // 操作详情
        return Result.success();
    }

    @PatchMapping("/settings/updatePwd")
    public Result updatePwd(@RequestBody @Validated Map<String, String> params, HttpServletRequest request) {
        // 1. 校验参数
        String oldPwd = params.get("oldPwd");
        String newPwd = params.get("newPwd");
        String rePwd = params.get("rePwd");
        System.out.println(oldPwd);
        System.out.println(newPwd);
        System.out.println(rePwd);

        if (!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(rePwd)) {
            // 若有pwd没传递，不处理
            return Result.error("缺少参数");
        }
        // 原密码是否正确
        //
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User loginUser = userService.findByUsername(username);
        if (!loginUser.getPassword().equals(Md5Util.getMD5String(oldPwd))) {
            return Result.error("原密码错误");
        }
        // newPwd和rePwd是否一样
        if (!rePwd.equals(newPwd)) {
            return Result.error("两次输入密码不一致");
        }

        // 2.调用service完成密码更新
        userService.updatePwd(newPwd, username);
        // 获取修改密码时间和IP
        String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String loginIp = request.getRemoteAddr();
        String operationType = "修改密码";
        userService.addLogInfo(username, loginTime, loginIp, operationType); // 操作详情
        return Result.success();
    }

    @GetMapping("/settings/updateInfo")
    public Result<User> userInfo() {
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");

        User user = userService.findByUsername(username);
        return Result.success(user);
    }

    @GetMapping("/settings/securitylog")
    public Result<List<UserLog>> securitylog() {
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");

        List<UserLog> logs = userService.getLogInfoByUsername(username);

        return Result.success(logs);
    }




}
