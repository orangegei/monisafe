package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.pojo.ChartData;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface BusinessMapper{

    @Select("SELECT age_group as xdata, SUM(transaction_amount) as ydata " +
            "FROM age_group_transaction " +
            "WHERE transaction_type = #{transactionType} AND transaction_time BETWEEN #{startTime} AND #{endTime} " +
            "GROUP BY age_group " +
            "ORDER BY age_group")
    List<Map<String, Object>> getATMAgeAmountData(LocalDateTime startTime, LocalDateTime endTime, String transactionType);


    @Select("SELECT age_group as xdata, SUM(transaction_count) as ydata " +
            "FROM age_group_transaction " +
            "WHERE transaction_type = #{transactionType} AND transaction_time BETWEEN #{startTime} AND #{endTime} " +
            "GROUP BY age_group " +
            "ORDER BY age_group")
    List<Map<String, Object>> getATMAgeCountData(LocalDateTime startTime, LocalDateTime endTime, String transactionType);

    // 返回从startTime 到 endTime 各金额段的交易数量
    @Select("SELECT amount_range as xdata, SUM(transaction_count) as ydata " +
            "FROM amount_range_transaction " +
            "WHERE transaction_type = #{transactionType} AND transaction_time BETWEEN #{startTime} AND #{endTime} " +
            "GROUP BY amount_range " +
            "ORDER BY amount_range")
    List<Map<String, Object>> getATMRangeData(LocalDateTime startTime, LocalDateTime endTime, String transactionType);

    @Select("SELECT exchange_type as xdata, SUM(transaction_count) as ydata " +
            "FROM forex_type " +
            "WHERE transaction_type = #{transactionType} AND transaction_time BETWEEN #{startTime} AND #{endTime} " +
            "GROUP BY exchange_type " +
            "ORDER BY exchange_type")
    List<Map<String, Object>> getforexTypeData(LocalDateTime startTime, LocalDateTime endTime, String transactionType);

    @Select("SELECT purpose as xdata, SUM(transaction_count) as ydata " +
            "FROM forex_purpose_transaction " +
            "WHERE transaction_type = #{transactionType} AND transaction_time BETWEEN #{startTime} AND #{endTime} " +
            "GROUP BY exchange_type " +
            "ORDER BY exchange_type")
    List<Map<String, Object>> getforexPurposeData(LocalDateTime startTime, LocalDateTime endTime, String transactionType);




}
