package com.mykyta.stockapi.exception;

public class StockAlreadyExistsException extends RuntimeException {
    private String stockSymbol;
    public StockAlreadyExistsException(String stockSymbol) {
        super(String.format("Stock '%s' already exists", stockSymbol));
        this.stockSymbol = stockSymbol;
    }
}
