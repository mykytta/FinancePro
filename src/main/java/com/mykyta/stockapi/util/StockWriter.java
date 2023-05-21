package com.mykyta.stockapi.util;

import com.mykyta.stockapi.dto.StockDto;
import com.mykyta.stockapi.entity.Stock;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class StockWriter {

    private final RestTemplate restTemplate;

    @Value("${iexapi.token}")
    private String token;
    public Stock writeStockToDB(String company){
        return restTemplate.getForObject("https://cloud.iexapis.com/stable/stock/" +
                company +
                "/quote?token=" + token, StockDto.class).toStock();
    }
}
