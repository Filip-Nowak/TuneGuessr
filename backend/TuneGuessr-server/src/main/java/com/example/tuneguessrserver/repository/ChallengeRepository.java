package com.example.tuneguessrserver.repository;

import com.example.tuneguessrserver.entity.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ChallengeRepository extends JpaRepository<Challenge,Long> {
    Challenge findChallengeByName(String name);
    List<Challenge> findByNameContains(String name);
}
