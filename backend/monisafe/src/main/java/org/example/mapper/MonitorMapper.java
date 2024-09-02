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

    // 获取该分钟的数据
//    @Select("select * from average_monitor_data where transaction_type=\"ATM\" and transaction_time=#{time}")
//    List<MonitorData> getATMMonitorData(LocalDateTime time);

    // 获取过去十分钟的数据
    @Select("select * from average_monitor_data where transaction_type=\"ATM\" and transaction_time BETWEEN #{time} - INTERVAL 10 MINUTE AND #{time}")
    List<MonitorData> getATMMonitorData(LocalDateTime time);

    @Select("select * from average_monitor_data where transaction_type=\"forex\" and transaction_time BETWEEN #{time} - INTERVAL 10 MINUTE AND #{time}")
    List<MonitorData> getforexMonitorData(LocalDateTime time);

    @Select("select * from withdrawal_monitor where transaction_time=#{time}")
    List<WithdrawalMonitorLog> getWithdrawalMonitorLogs(LocalDateTime time);

    @Select("select * from single_response_time where transaction_time=#{time}")
    List<SingleResponseTimeLog> getSingleResponseTimeLogs(LocalDateTime time);
}
