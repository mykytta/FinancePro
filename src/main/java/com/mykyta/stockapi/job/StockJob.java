package com.mykyta.stockapi.job;

import com.mykyta.stockapi.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@EnableScheduling
public class StockJob {

    private final StockService stockService;

    @Scheduled(cron = "0 */5 * * * *")
    public void process() {
        stockService.updateStockPrices();
    }
}
