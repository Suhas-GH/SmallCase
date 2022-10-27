package com.abnamro.smallcase.repository;

import com.abnamro.smallcase.model.Baskets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface BasketsRepository extends JpaRepository<Baskets, Long> {
    @Transactional
    @Modifying
    @Query("UPDATE Baskets b SET b.basketName = ?1 , b.description = ?2 WHERE b.basketId = ?3")
    void updateDetails(String basketName, String basketDescription, Long basketId);
}
