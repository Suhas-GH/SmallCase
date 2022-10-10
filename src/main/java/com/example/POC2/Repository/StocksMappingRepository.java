package com.example.POC2.Repository;

import com.example.POC2.Model.Stocks;
import com.example.POC2.Model.StocksMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface StocksMappingRepository extends JpaRepository<StocksMapping, Long> {
    @Query("SELECT COUNT(*) FROM StocksMapping WHERE stocks_id = ?1")
    Long stocksExisting(Long StockId);

    @Transactional
    @Modifying
    @Query("DELETE FROM StocksMapping WHERE basket_id = ?1")
    void deleteMappingByBasketId(Long BasketId);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO stocks_mapping (qty,stocks_id,basket_id) VALUES (?1,?2,?3)", nativeQuery = true)
    void saveMappings(int QTY, Long StockId, Long BasketId);

    @Query(value = "SELECT stocks_id FROM stocks_mapping WHERE mapping_id = ?1", nativeQuery = true)
    Long getStockId(Long id);
}
