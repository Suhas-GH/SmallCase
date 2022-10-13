package com.example.smallcase.repository;

import com.example.smallcase.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {
    boolean existsByUserId(Long userId);
    Cart findByUserId(Long userId);
}
