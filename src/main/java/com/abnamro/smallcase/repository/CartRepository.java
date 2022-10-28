package com.abnamro.smallcase.repository;

import com.abnamro.smallcase.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {
    boolean existsByUserId(Long userId);
    Cart findByUserId(Long userId);
}
