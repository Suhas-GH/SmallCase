package com.example.POC2.Repository;
import com.example.POC2.Model.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {
    ApplicationUser findByUserName(String userName);
    boolean existsByUserName(String userName);
}

