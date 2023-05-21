package com.mykyta.stockapi.service;

import com.mykyta.stockapi.dto.StockDto;
import com.mykyta.stockapi.entity.Stock;
import com.mykyta.stockapi.entity.User;
import com.mykyta.stockapi.exception.StockAlreadyExistsException;
import com.mykyta.stockapi.exception.StockNotFoundException;
import com.mykyta.stockapi.exception.UserNotFoundException;
import com.mykyta.stockapi.repository.StockRepository;
import com.mykyta.stockapi.repository.UserRepository;
import com.mykyta.stockapi.security.UserPrincipal;
import com.mykyta.stockapi.util.StockWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService{

    private final StockRepository stockRepository;

    private final UserRepository userRepository;

    private final StockWriter stockWriter;
    @Override
    public StockDto addStock(UserPrincipal userPrincipal, String stockSymbol) {
        User user = getUserFromPrincipal(userPrincipal);

        Stock stock = stockRepository.findBySymbol(stockSymbol)
                .orElseGet(() -> {
                    Stock newStock = stockWriter.writeStockToDB(stockSymbol);
                    if (newStock == null) {
                        throw new StockNotFoundException(stockSymbol);
                    }
                    stockRepository.save(newStock);
                    return newStock;
                });

        if (user.getStocks().contains(stock)) {
            throw new StockAlreadyExistsException(stockSymbol);
        }

        user.getStocks().add(stock);
        userRepository.save(user);

        return new StockDto().fromStock(stock);
    }

    @Override
    public void deleteStock(UserPrincipal userPrincipal, Long stockId) {
        User user = getUserFromPrincipal(userPrincipal);

        Optional<Stock> optionalStock = user.getStocks().stream()
                .filter(stock -> stock.getId().equals(stockId))
                .findFirst();

        if (optionalStock.isEmpty()) {
            throw new StockNotFoundException(stockId);
        }

        Stock stock = optionalStock.get();
        user.getStocks().remove(stock);
        userRepository.save(user);
    }

    @Override
    public StockDto getStock(Long stockId) {
        Optional<Stock> optionalStock = stockRepository.findById(stockId);

        if (optionalStock.isEmpty()) {
            throw new StockNotFoundException(stockId);
        }

        return new StockDto().fromStock(optionalStock.get());
    }

    @Override
    public List<StockDto> getAllUserStocks(UserPrincipal userPrincipal) {
        User user = getUserFromPrincipal(userPrincipal);
        return user.getStocks().stream()
                .map(stock -> new StockDto().fromStock(stock))
                .toList();
    }

    public void updateStockPrices() {
        List<Stock> stocks = stockRepository.findAll();

        for (Stock stock : stocks) {
            CompletableFuture.runAsync(() -> {
                Stock updatedStock = stockWriter.writeStockToDB(stock.getSymbol());
                if (updatedStock != null) {
                    stock.setPrice(updatedStock.getPrice());
                    stock.setChange(updatedStock.getChange());
                    stock.setVolume(updatedStock.getVolume());

                    stockRepository.save(stock);
                }
            });
        }
    }

    private User getUserFromPrincipal(UserPrincipal userPrincipal) {
        Long userId = userPrincipal.getId();
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }
}
