package org.example.controller;


import org.example.pojo.ChartData;
import org.example.pojo.Result;
import org.example.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@Validated
@RequestMapping("/business")
public class BusinessController {

    @Autowired
    BusinessService businessService;

    // 获取ATM 各年龄段 交易金额数据
    @GetMapping("/atm/age/amount")
    public Result<ChartData> getATMAgeAmountData(@RequestParam LocalDateTime startTime, @RequestParam LocalDateTime endTime) {
        ChartData chartData = businessService.getAgeAmountData(startTime, endTime, "ATM");

        return Result.success(chartData);
    }

    // 获取ATM 各年龄段 交易数量数据
    @GetMapping("/atm/age/count")
    public Result<ChartData> getATMAgeCountData(@RequestParam LocalDateTime startTime, @RequestParam LocalDateTime endTime) {
        ChartData chartData = businessService.getAgeCountData(startTime, endTime, "ATM");

        return Result.success(chartData);
    }

    // 获取ATM 各金额范围 交易数量数据
    @GetMapping("/atm/range")
    public Result<ChartData> getATMRangeData(@RequestParam LocalDateTime startTime, @RequestParam LocalDateTime endTime) {
        ChartData chartData = businessService.getRangeData(startTime, endTime, "ATM");

        return Result.success(chartData);
    }

    // 获取forex 各年龄段 交易金额数据
    @GetMapping("/forex/age/amount")
    public Result<ChartData> getforexAgeAmountData(@RequestParam LocalDateTime startTime, @RequestParam LocalDateTime endTime) {
        ChartData chartData = businessService.getAgeAmountData(startTime, endTime, "forex");

        return Result.success(chartData);
    }

    // 获取forex 各年龄段 交易数量数据
    @GetMapping("/forex/age/count")
    public Result<ChartData> getforexAgeCountData(@RequestParam LocalDateTime startTime, @RequestParam LocalDateTime endTime) {
        ChartData chartData = businessService.getAgeCountData(startTime, endTime, "forex");

        return Result.success(chartData);
    }

    // 获取forex 各金额范围 交易数量数据
    @GetMapping("/atm/range")
    public Result<ChartData> getforexRangeData(@RequestParam LocalDateTime startTime, @RequestParam LocalDateTime endTime) {
        ChartData chartData = businessService.getRangeData(startTime, endTime, "forex");

        return Result.success(chartData);
    }

    // 获取forex 兑换种类金额
    @GetMapping("/forex/type")
    public Result<ChartData> getforexTypeData(@RequestParam LocalDateTime startTime, @RequestParam LocalDateTime endTime) {
        ChartData chartData = businessService.getforexTypeData(startTime, endTime, "forex");

        return Result.success(chartData);
    }

    // 获取forex 兑换目的数量
    @GetMapping("/forex/purpose")
    public Result<ChartData> getforexPurposeData(@RequestParam LocalDateTime startTime, @RequestParam LocalDateTime endTime) {
        ChartData chartData = businessService.getforexPurposeData(startTime, endTime, "forex");

        return Result.success(chartData);
    }


}
