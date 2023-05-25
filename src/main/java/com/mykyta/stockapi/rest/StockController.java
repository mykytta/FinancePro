package com.mykyta.stockapi.rest;

import com.mykyta.stockapi.dto.StockDto;
import com.mykyta.stockapi.security.UserPrincipal;
import com.mykyta.stockapi.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/stocks")
public class StockController {

    private final StockService stockService;


    @PostMapping("/{symbol}")
    public ResponseEntity<?> addStock(@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable String symbol){

        StockDto addedStock = stockService.addStock(userPrincipal, symbol);
        return ResponseEntity.ok(addedStock);
    }


    @DeleteMapping("/{stockId}")
    public ResponseEntity<String> deleteStock(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                              @PathVariable("stockId") Long stockId) {
        stockService.deleteStock(userPrincipal, stockId);
        return ResponseEntity.ok("Stock deleted successfully");
    }

    @GetMapping("/{stockId}")
    public ResponseEntity<StockDto> getStock(@PathVariable("stockId") Long stockId) {
        StockDto stock = stockService.getStock(stockId);
        return ResponseEntity.ok(stock);
    }

    @GetMapping()
    public ResponseEntity<List<StockDto>> getAllUserStocks(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        List<StockDto> stocks = stockService.getAllUserStocks(userPrincipal);
        return ResponseEntity.ok(stocks);
    }
}
