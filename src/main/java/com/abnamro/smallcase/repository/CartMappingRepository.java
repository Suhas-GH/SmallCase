package com.abnamro.smallcase.repository;

import com.abnamro.smallcase.model.CartMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CartMappingRepository extends JpaRepository<CartMapping,Long> {
    @Query("SELECT COUNT(cm) FROM CartMapping cm WHERE cm.cart.cartId = ?1")
    Long cartCount(Long cartId);

    @Transactional
    @Modifying
    @Query("DELETE FROM CartMapping WHERE baskets.basketId = ?1 AND cart.cartId = ?2")
    void deleteBasketFromCart(Long basketId, Long cartId);

    @Query("SELECT COUNT(cm) FROM CartMapping cm WHERE cm.cart.cartId = ?1 AND cm.baskets.basketId = ?2")
    Long existsByCartIdAndBasketId(Long cartId, Long basketId);

    @Query("SELECT COUNT(cm) FROM CartMapping cm WHERE cm.baskets.basketId = ?1")
    Long existsByBasketId(Long basketId);

    @Query("FROM CartMapping WHERE cart.cartId = ?1")
    List<CartMapping> findAllByCartCartId(Long cartId);
}
