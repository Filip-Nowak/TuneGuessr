package com.example.tuneguessrserver.repository;

import com.example.tuneguessrserver.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserProfileRepository extends JpaRepository<UserProfile,Long> {
    List<UserProfile> findByNicknameContains(String nickname);
    UserProfile findByNickname(String nickname);
}
