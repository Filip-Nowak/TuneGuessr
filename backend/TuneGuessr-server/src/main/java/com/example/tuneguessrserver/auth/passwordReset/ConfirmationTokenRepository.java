package com.example.tuneguessrserver.auth.passwordReset;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {
    ConfirmationToken findByToken(String token);

    List<ConfirmationToken> findByUserId(long id);
}
