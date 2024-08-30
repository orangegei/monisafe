package org.example.DTO;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class RegisterForm {
    private String username;
    private String password;
    @Email
    private String emailAddress;
    private String phoneNumber;
}
