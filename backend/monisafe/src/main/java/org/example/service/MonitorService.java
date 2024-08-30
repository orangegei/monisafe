package org.example.service;

import org.example.pojo.MonitorData;
import org.example.pojo.SingleResponseTimeLog;
import org.example.pojo.WithdrawalMonitorLog;

import java.time.LocalDateTime;
import java.util.List;

public interface MonitorService {
    MonitorData getATMMonitorData(LocalDateTime time);

    MonitorData getforexMonitorData(LocalDateTime time);

    List<WithdrawalMonitorLog> getWithdrawalMonitorLogs(LocalDateTime time);

    List<SingleResponseTimeLog> getSingleResponseTimeLogs(LocalDateTime time);
}
