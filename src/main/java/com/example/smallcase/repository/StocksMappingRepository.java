package com.example.smallcase.repository;

import com.example.smallcase.model.StocksMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface StocksMappingRepository extends JpaRepository<StocksMapping, Long> {
    @Query("SELECT COUNT(sm) FROM StocksMapping sm WHERE sm.stocks.stockId = ?1")
    Long stocksExisting(Long stockId);

    @Transactional
    @Modifying
    @Query("DELETE FROM StocksMapping WHERE baskets.basketId = ?1")
    void deleteMappingByBasketId(Long basketId);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO stocks_mapping (qty,stocks_id,basket_id) VALUES (?1,?2,?3)", nativeQuery = true)
    void saveMappings(int qty, Long stockId, Long basketId);

    @Query(value = "SELECT stocks_id FROM stocks_mapping WHERE mapping_id = ?1", nativeQuery = true)
    Long getStockId(Long id);
}
