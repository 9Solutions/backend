package com.caixadesapato.api.repository;

import com.caixadesapato.api.model.DashUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DashUserRepository extends JpaRepository<DashUser, Long> {
    Optional<DashUser> findByEmail(String email);
}
