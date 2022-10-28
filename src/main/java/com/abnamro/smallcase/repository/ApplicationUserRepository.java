package com.abnamro.smallcase.repository;
import com.abnamro.smallcase.model.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {
    ApplicationUser findByUserName(String userName);
    boolean existsByUserName(String userName);
}

