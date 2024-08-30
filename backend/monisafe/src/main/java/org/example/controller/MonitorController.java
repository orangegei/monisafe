package org.example.controller;

import org.example.pojo.*;
import org.example.service.MonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@Validated
@RequestMapping("/monitor")
public class MonitorController {

    @Autowired
    MonitorService monitorService;


    @GetMapping("/atm")
    public Result<MonitorData> getATMdata(@RequestParam LocalDateTime time) {
        MonitorData ATMMonitorData = monitorService.getATMMonitorData(time);

        return Result.success(ATMMonitorData);
    }

    @GetMapping("/forex")
    public Result<MonitorData> getforexdata(LocalDateTime time) {
        MonitorData forexMonitorData = monitorService.getforexMonitorData(time);

        return Result.success(forexMonitorData);
    }

    @GetMapping("/logs")
    public Result<List<AlertLog>> getLogs(LocalDateTime time) {
        List<AlertLog> alertLogs = new ArrayList<>();
        List<WithdrawalMonitorLog> withdrawalMonitorLogs = monitorService.getWithdrawalMonitorLogs(time);
        List<SingleResponseTimeLog> singleResponseTimeLogs = monitorService.getSingleResponseTimeLogs(time);
        alertLogs.addAll(withdrawalMonitorLogs);
        alertLogs.addAll(singleResponseTimeLogs);
        return Result.success(alertLogs);
    }



}
