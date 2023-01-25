package com.cydeo.javahedgehogsproject.controller;

import com.cydeo.javahedgehogsproject.service.ReportingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reports")
public class ReportingController {

    private final ReportingService reportingService;

    public ReportingController(ReportingService reportingService) {
        this.reportingService = reportingService;
    }

    @GetMapping("/profitLossData")
    public String getProfitLossData(Model model) {
        model.addAttribute("monthlyProfitLossDataMap", reportingService.profitMonthlyLoss());
        return "/report/profit-loss-report";
    }

    @GetMapping("/stockData")
    public String getStockData(Model model) {
        model.addAttribute("invoiceProducts", reportingService.getInvoiceProductsOfApprovedInvoices());
        return "/report/stock-report";
    }

}
