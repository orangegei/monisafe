package org.example.pojo;



import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    private Integer userId;//主键ID
    private String username;//用户名
    private String password;//密码

    private String emailAddress;//邮箱

    private String phoneNumber; // 电话号码
}
