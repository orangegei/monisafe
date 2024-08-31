package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.pojo.MonitorData;
import org.example.pojo.SingleResponseTimeLog;
import org.example.pojo.WithdrawalMonitorLog;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface MonitorMapper {

    @Select("select * from average_monitor_data where transaction_type=\"ATM\" and transaction_time=#{time}")
    MonitorData getATMMonitorData(LocalDateTime time);

    @Select("select * from average_monitor_data where transaction_type=\"forex\" and transaction_time=#{time}")
    MonitorData getforexMonitorData(LocalDateTime time);

    @Select("select * from withdrawal_monitor where transaction_time=#{time}")
    List<WithdrawalMonitorLog> getWithdrawalMonitorLogs(LocalDateTime time);

    @Select("select * from single_response_time where transaction_time=#{time}")
    List<SingleResponseTimeLog> getSingleResponseTimeLogs(LocalDateTime time);
}
