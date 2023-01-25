package com.cydeo.javahedgehogsproject.service;

import java.math.BigDecimal;
import java.util.Map;

import com.cydeo.javahedgehogsproject.dto.UsdDto;

public interface DashboardService {

     Map<String, BigDecimal> dashboardNumbers();
     BigDecimal calculateTotalCost();
     BigDecimal calculateTotalSales();
     BigDecimal calculateProfitLoss();

    UsdDto getCurrency();

}
