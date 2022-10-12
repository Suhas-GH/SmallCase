package com.example.POC2.Repository;

import com.example.POC2.Model.Stocks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface StocksRepository extends JpaRepository<Stocks, Long> {
    @Transactional
    @Modifying
    @Query("UPDATE Stocks SET StockName = ?1 , StockPrice = ?2 WHERE StockId = ?3")
    void modifyStocks(String StocksName, Float StockPrice, Long StockId);
    @Query("SELECT COUNT(s) FROM Stocks s WHERE s.StockId IN ?1")
    int checkStocks(List<Long> Ids);
}
