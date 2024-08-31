package org.example.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public abstract class AlertLog {
    private String province; // 省份
    private int status; // 告警类型 0为normal 1为warning 2为severe
    private LocalDateTime TransactionTime; // 告警时间
}
