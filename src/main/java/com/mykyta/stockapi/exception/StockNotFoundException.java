package com.mykyta.stockapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class StockNotFoundException extends RuntimeException {
    private Long stockId;
    private String stockSymbol;
    public StockNotFoundException(String stockSymbol) {
        super(String.format("Stock '%s' not found", stockSymbol));
        this.stockSymbol = stockSymbol;
    }

    public StockNotFoundException(Long stockId) {
        super(String.format("Stock with such id: '%s' not found", stockId));
        this.stockId = stockId;
    }

}
