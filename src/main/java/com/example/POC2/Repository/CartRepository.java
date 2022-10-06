package com.example.POC2.Repository;

import com.example.POC2.Model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {
    boolean existsByUserId(Long UserId);
    Cart findByUserId(Long UserId);
}
