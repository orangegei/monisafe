package org.example.service.impl;

import org.example.mapper.MonitorMapper;
import org.example.pojo.MonitorData;
import org.example.pojo.SingleResponseTimeLog;
import org.example.pojo.WithdrawalMonitorLog;
import org.example.service.MonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MonitorServiceImpl implements MonitorService {
    @Autowired
    MonitorMapper monitorMapper;

    @Override
    public MonitorData getATMMonitorData(LocalDateTime time) {
        return monitorMapper.getATMMonitorData(time);
    }

    @Override
    public MonitorData getforexMonitorData(LocalDateTime time) {
        return monitorMapper.getforexMonitorData(time);
    }

    @Override
    public List<WithdrawalMonitorLog> getWithdrawalMonitorLogs(LocalDateTime time) {
        return monitorMapper.getWithdrawalMonitorLogs(time);
    }

    @Override
    public List<SingleResponseTimeLog> getSingleResponseTimeLogs(LocalDateTime time) {
        return monitorMapper.getSingleResponseTimeLogs(time);
    }
}
