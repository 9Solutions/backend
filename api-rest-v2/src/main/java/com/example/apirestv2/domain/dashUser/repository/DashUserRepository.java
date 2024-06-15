package com.example.apirestv2.domain.dashUser.repository;

import com.example.apirestv2.domain.dashUser.DashUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface DashUserRepository extends JpaRepository<DashUser, Integer> {
    DashUser findByEmail(String email);
}
