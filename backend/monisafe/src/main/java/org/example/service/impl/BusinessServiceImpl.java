package org.example.service.impl;

import org.example.mapper.BusinessMapper;
import org.example.pojo.ChartData;
import org.example.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BusinessServiceImpl implements BusinessService {

    @Autowired
    BusinessMapper businessMapper;

    @Override
    public ChartData getAgeAmountData(LocalDateTime startTime, LocalDateTime endTime, String transactionType) {
        List<Map<String, Object>> rawData = businessMapper.getATMAgeAmountData(startTime, endTime, transactionType);
        ChartData chartData = getChartData(rawData);

        return chartData;
    }

    @Override
    public ChartData getAgeCountData(LocalDateTime startTime, LocalDateTime endTime, String transactionType) {
        List<Map<String, Object>> rawData = businessMapper.getATMAgeCountData(startTime, endTime, transactionType);
        ChartData chartData = getChartData(rawData);

        return chartData;
    }

    @Override
    public ChartData getRangeData(LocalDateTime startTime, LocalDateTime endTime, String transactionType) {
        List<Map<String, Object>> rawData = businessMapper.getATMRangeData(startTime, endTime, transactionType);
        System.out.println(rawData);
        ChartData chartData = getChartData(rawData);

        return chartData;
    }

    @Override
    public ChartData getforexTypeData(LocalDateTime startTime, LocalDateTime endTime, String transactionType) {
        List<Map<String, Object>> rawData = businessMapper.getforexTypeData(startTime, endTime, transactionType);
        System.out.println(rawData);
        ChartData chartData = getChartData(rawData);

        return chartData;
    }

    @Override
    public ChartData getforexPurposeData(LocalDateTime startTime, LocalDateTime endTime, String transactionType) {
        List<Map<String, Object>> rawData = businessMapper.getforexPurposeData(startTime, endTime, transactionType);
        System.out.println(rawData);
        ChartData chartData = getChartData(rawData);

        return chartData;
    }


    private ChartData getChartData(List<Map<String, Object>> rawData) {
        List<String> xdata = new ArrayList<>();
        List<Double> ydata = new ArrayList<>();

        for (Map<String, Object> row : rawData) {
            xdata.add(String.valueOf(row.get("xdata")));
            ydata.add(((Number) row.get("ydata")).doubleValue());
        }
        System.out.println(xdata);
        System.out.println(ydata);
        ChartData chartData = new ChartData(xdata, ydata);
        return chartData;
    }


}
