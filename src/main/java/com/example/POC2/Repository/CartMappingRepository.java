package com.example.POC2.Repository;

import com.example.POC2.Model.CartMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CartMappingRepository extends JpaRepository<CartMapping,Long> {
    @Query("SELECT COUNT(*) FROM CartMapping WHERE cart_id = ?1")
    Long cartCount(Long CartId);

    @Transactional
    @Modifying
    @Query("DELETE FROM CartMapping WHERE basket_id = ?1 AND cart_id = ?2")
    void deleteBasketFromCart(Long BasketId, Long CartId);

    @Query("SELECT COUNT(*) FROM CartMapping WHERE cart_id = ?1 AND basket_id = ?2")
    Long existsByCartIdAndBasketId(Long CartId, Long BasketId);

    @Query("SELECT COUNT(*) FROM CartMapping WHERE basket_id = ?1")
    Long existsByBasketId(Long BasketId);
}
