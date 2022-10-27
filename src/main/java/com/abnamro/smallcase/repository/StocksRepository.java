package com.abnamro.smallcase.repository;

import com.abnamro.smallcase.model.Stocks;
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
    @Query("UPDATE Stocks s SET s.stockName = ?1 , s.stockPrice = ?2 WHERE s.stockId = ?3")
    void modifyStocks(String stocksName, Float stockPrice, Long stockId);
    @Query("SELECT COUNT(s) FROM Stocks s WHERE s.stockId IN ?1")
    int checkStocks(List<Long> ids);
}
