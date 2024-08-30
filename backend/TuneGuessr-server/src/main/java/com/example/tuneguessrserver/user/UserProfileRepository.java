package com.example.tuneguessrserver.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile,Long> {
    List<UserProfile> findByNicknameContains(String nickname);
    Optional<UserProfile> findByNickname(String nickname);
    @Query("SELECT p FROM UserProfile p WHERE p.user.email = :email")
    Optional<UserProfile> findProfileByEmail(String email);

    boolean existsByNickname(String nickname);
}
