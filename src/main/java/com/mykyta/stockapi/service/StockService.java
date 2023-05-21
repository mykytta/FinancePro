package com.mykyta.stockapi.service;

import com.mykyta.stockapi.dto.StockDto;
import com.mykyta.stockapi.security.UserPrincipal;

import java.util.List;

public interface StockService {
    StockDto addStock(UserPrincipal userPrincipal, String stockSymbol);
    List<StockDto> getAllUserStocks(UserPrincipal userPrincipal);
    StockDto getStock(Long stockId);
    void  deleteStock(UserPrincipal userPrincipal, Long stockId);
    void updateStockPrices();
}
