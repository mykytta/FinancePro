package com.mykyta.stockapi.repository;

import com.mykyta.stockapi.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {
    @Query(value = "SELECT * FROM stocks ORDER BY volume DESC LIMIT 5", nativeQuery = true)
    List<Stock> getTopByVolume();
    @Query(value = "SELECT DISTINCT * FROM stocks ORDER BY stock_change DESC LIMIT 5", nativeQuery = true)
    List<Stock> getTopByChange();

    Optional<Stock> findBySymbol(String symbol);

}
