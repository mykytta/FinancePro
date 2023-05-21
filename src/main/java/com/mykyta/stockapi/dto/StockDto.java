package com.mykyta.stockapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
//import com.mykyta.stockapi.model.Stock;
import com.mykyta.stockapi.entity.Stock;
import lombok.Data;

@Data
public class StockDto {
    private Long id;
    @JsonProperty(value = "symbol")
    private String symbol;
    @JsonProperty(value = "companyName")
    private String companyName;
    @JsonProperty(value = "currency")
    private String currency;
    @JsonProperty(value = "latestPrice")
    private double price;
    @JsonProperty(value = "change")
    private double change;
    @JsonProperty(value = "previousVolume")
    private Long volume;

    public Stock toStock(){
        Stock stock = new Stock();
        stock.setSymbol(symbol);
        stock.setCompanyName(companyName);
        stock.setCurrency(currency);
        stock.setPrice(price);
        stock.setChange(change);
        stock.setVolume(volume);

        return stock;
    }

    public StockDto fromStock(Stock stock){
        StockDto stockDto = new StockDto();
        stockDto.setId(stock.getId());
        stockDto.setSymbol(stock.getSymbol());
        stockDto.setCompanyName(stock.getCompanyName());
        stockDto.setCurrency(stock.getCurrency());
        stockDto.setPrice(stock.getPrice());
        stockDto.setChange(stock.getChange());
        stockDto.setVolume(stock.getVolume());

        return stockDto;
    }
}

