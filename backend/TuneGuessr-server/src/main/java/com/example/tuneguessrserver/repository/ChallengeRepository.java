package com.example.tuneguessrserver.repository;

import com.example.tuneguessrserver.entity.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ChallengeRepository extends JpaRepository<Challenge,Long> {
}
