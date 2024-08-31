package org.example.pojo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;


import java.time.LocalDateTime;

@Data
public class User {
    private Integer userId;//主键ID
    private String username;//用户名
    @JsonIgnore // 让springmvc把当前对象转换成json字符串的时候，忽略password
    private String password;//密码

    @Email
    private String emailAddress;//邮箱

    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号码必须为11位数字")
    private String phoneNumber; // 电话号码
}
