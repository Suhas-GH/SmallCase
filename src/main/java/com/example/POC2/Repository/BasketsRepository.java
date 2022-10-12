package com.example.POC2.Repository;

import com.example.POC2.Model.Baskets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface BasketsRepository extends JpaRepository<Baskets, Long> {
    @Transactional
    @Modifying
    @Query("UPDATE Baskets b SET b.BasketName = ?1 , b.Description = ?2 WHERE b.BasketId = ?3")
    void updateDetails(String basketName, String basketDescription, Long basketId);
}
