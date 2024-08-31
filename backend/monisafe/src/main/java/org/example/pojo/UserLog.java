package org.example.pojo;

import lombok.Data;

@Data
public class UserLog {
    String username;
    String loginTime;
    String loginIp;
    String operationType;
}
