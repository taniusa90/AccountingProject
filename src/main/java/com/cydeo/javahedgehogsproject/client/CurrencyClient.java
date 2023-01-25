package com.cydeo.javahedgehogsproject.client;

import com.cydeo.javahedgehogsproject.dto.CurrencyDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(url = "https://cdn.jsdelivr.net", name = "CURRENCY-CLIENT")
public interface CurrencyClient {

    @GetMapping("/gh/fawazahmed0/currency-api@1/latest/currencies/usd.json")
    CurrencyDto getCurrencies();

}
