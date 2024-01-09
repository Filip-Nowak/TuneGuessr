package com.example.tuneguessrserver.repository;

import com.example.tuneguessrserver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
