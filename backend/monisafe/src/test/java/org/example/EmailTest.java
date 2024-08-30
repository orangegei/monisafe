package org.example;

import org.example.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

@Controller
public class EmailTest {

    @Autowired
    private EmailService emailService;

    @Test
    public void someMethod() {
        emailService.sendSimpleMail("1471036380@qq.com", "你好", "hello email");
    }

}
