package org.example.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MonitorData {
    private LocalDateTime transactionTime;
    // 1分钟平均响应时间
    private double averageResponseTime;
    // 取款总金额
    private double transactionAmount;
    // 取款笔数
    private int transactionCount;

}
