package org.example.service;

import org.example.pojo.ChartData;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface BusinessService {

    ChartData getAgeAmountData(LocalDateTime startTime, LocalDateTime endTime, String transactionType);

    ChartData getAgeCountData(LocalDateTime startTime, LocalDateTime endTime, String transactionType);

    ChartData getRangeData(LocalDateTime startTime, LocalDateTime endTime, String transactionType);

    ChartData getforexTypeData(LocalDateTime startTime, LocalDateTime endTime, String transactionType);

    ChartData getforexPurposeData(LocalDateTime startTime, LocalDateTime endTime, String forex);
}
