package com.example.tuneguessrserver.repository;

import com.example.tuneguessrserver.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile,Long> {
}
