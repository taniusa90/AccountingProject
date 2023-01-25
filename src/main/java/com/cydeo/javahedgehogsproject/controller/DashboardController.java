package com.cydeo.javahedgehogsproject.controller;

import com.cydeo.javahedgehogsproject.enums.InvoiceStatus;
import com.cydeo.javahedgehogsproject.service.DashboardService;
import com.cydeo.javahedgehogsproject.service.InvoiceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final DashboardService dashboardService;
    private final InvoiceService invoiceService;

    public DashboardController(DashboardService dashboardService, InvoiceService invoiceService) {
        this.dashboardService = dashboardService;
        this.invoiceService = invoiceService;
    }


    @GetMapping("/dashboard")
    public String navigateToDashboard(Model model) throws Exception{

        model.addAttribute("dashboardNumbers",dashboardService.dashboardNumbers() );
        model.addAttribute("invoices", invoiceService.findAllApprovedInvoice(InvoiceStatus.APPROVED));
        model.addAttribute("currency", dashboardService.getCurrency());

        return "dashboard";
    }

}
