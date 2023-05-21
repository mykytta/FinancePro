package com.mykyta.stockapi.rest;

import com.mykyta.stockapi.dto.StockDto;
import com.mykyta.stockapi.entity.Stock;
import com.mykyta.stockapi.entity.User;
import com.mykyta.stockapi.repository.StockRepository;
import com.mykyta.stockapi.repository.UserRepository;
import com.mykyta.stockapi.security.UserPrincipal;
import com.mykyta.stockapi.service.StockServiceImpl;
import com.mykyta.stockapi.util.StockWriter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StockControllerTest {

    @Mock
    private StockRepository stockRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private StockWriter stockWriter;


    @Test
    void addStock() {
        UserPrincipal userPrincipal = new UserPrincipal();
        userPrincipal.setId(1L);

        User user = new User();
        user.setId(1L);
        user.setStocks(new ArrayList<>());

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        when(stockRepository.findBySymbol("AAPL")).thenReturn(Optional.empty());

        Stock createdStock = new Stock();
        createdStock.setId(1L);
        createdStock.setSymbol("AAPL");

        when(stockWriter.writeStockToDB("AAPL")).thenReturn(createdStock);

        StockServiceImpl stockService = new StockServiceImpl(stockRepository, userRepository, stockWriter);

        StockDto result = stockService.addStock(userPrincipal, "AAPL");

        assertNotNull(result);
        assertEquals(1L, result.getId()); // Verify the ID of the result
        assertEquals("AAPL", result.getSymbol());

        verify(userRepository).findById(1L);
        verify(stockRepository).findBySymbol("AAPL");
        verify(stockWriter).writeStockToDB("AAPL");
        verify(stockRepository).save(createdStock);
        verify(userRepository).save(user);
    }

    @Test
    void deleteStock() {
        UserPrincipal userPrincipal = new UserPrincipal();
        userPrincipal.setId(1L);

        User user = new User();
        user.setId(1L);
        user.setStocks(new ArrayList<>());

        Stock stock = new Stock();
        stock.setId(1L);

        user.getStocks().add(stock);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        StockServiceImpl stockService = new StockServiceImpl(stockRepository, userRepository, stockWriter);

        stockService.deleteStock(userPrincipal, 1L);

        assertTrue(user.getStocks().isEmpty());

        verify(userRepository).findById(1L);
        verify(userRepository).save(user);
    }

    @Test
    void getStock() {
        Long stockId = 1L;

        Stock stock = new Stock();
        stock.setId(stockId);

        when(stockRepository.findById(stockId)).thenReturn(Optional.of(stock));

        StockServiceImpl stockService = new StockServiceImpl(stockRepository, userRepository, stockWriter);

        StockDto result = stockService.getStock(stockId);

        assertNotNull(result);
        assertEquals(1L, result.getId()); // Verify the ID of the result

        verify(stockRepository).findById(stockId);
    }

    @Test
    void getAllUserStocks() {
        UserPrincipal userPrincipal = new UserPrincipal();
        userPrincipal.setId(1L);

        User user = new User();
        user.setId(1L);
        user.setStocks(new ArrayList<>());

        Stock stock1 = new Stock();
        stock1.setId(1L);
        stock1.setSymbol("AAPL");

        Stock stock2 = new Stock();
        stock2.setId(2L);
        stock2.setSymbol("GOOGL");

        user.getStocks().add(stock1);
        user.getStocks().add(stock2);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        StockServiceImpl stockService = new StockServiceImpl(stockRepository, userRepository, stockWriter);

        List<StockDto> result = stockService.getAllUserStocks(userPrincipal);

        assertNotNull(result);
        assertEquals(2, result.size());

        assertEquals(1L, result.get(0).getId());
        assertEquals("AAPL", result.get(0).getSymbol());

        assertEquals(2L, result.get(1).getId());
        assertEquals("GOOGL", result.get(1).getSymbol());

        verify(userRepository).findById(1L);
    }
}