package com.example.tuneguessrserver.challenge;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChallengeRepository extends JpaRepository<Challenge,Long> {
    Challenge findChallengeByName(String name);
    List<Challenge> findByNameContains(String name);
}
