package com.example.POC2.Repository;

import com.example.POC2.Model.CartMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CartMappingRepository extends JpaRepository<CartMapping,Long> {
    @Query("SELECT COUNT(cm) FROM CartMapping cm WHERE cm.cart.CartId = ?1")
    Long cartCount(Long CartId);

    @Transactional
    @Modifying
    @Query("DELETE FROM CartMapping WHERE baskets.BasketId = ?1 AND cart.CartId = ?2")
    void deleteBasketFromCart(Long BasketId, Long CartId);

    @Query("SELECT COUNT(cm) FROM CartMapping cm WHERE cm.cart.CartId = ?1 AND cm.baskets.BasketId = ?2")
    Long existsByCartIdAndBasketId(Long CartId, Long BasketId);

    @Query("SELECT COUNT(cm) FROM CartMapping cm WHERE cm.baskets.BasketId = ?1")
    Long existsByBasketId(Long BasketId);

    @Query("FROM CartMapping WHERE cart.CartId = ?1")
    List<CartMapping> findAllByCart_CartId(Long CartId);
}
