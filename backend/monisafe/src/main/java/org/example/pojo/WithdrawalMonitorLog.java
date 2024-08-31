package org.example.pojo;
import lombok.Data;

@Data
public class WithdrawalMonitorLog extends AlertLog {
    private int gender; // 性别 0为女 1为男
    private int age;
    private double TransactionAmount; // 交易总量
    private int eventType; // 0为大额取款告警 1为异常时间取款告警

}
