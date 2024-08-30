package org.example.DTO;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class ForgetPasswordForm {
    private String username;
    @Email
    private String emailAddress;
    private String phoneNumber;
    private String newPassword;
}
