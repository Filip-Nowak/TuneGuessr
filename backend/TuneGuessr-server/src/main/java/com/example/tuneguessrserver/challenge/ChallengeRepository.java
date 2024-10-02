package com.example.tuneguessrserver.challenge;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChallengeRepository extends JpaRepository<Challenge,Long> {
    Challenge findChallengeByName(String name);
    List<Challenge> findByNameContains(String name);
    @Query("SELECT c FROM Challenge c JOIN FETCH c.songs WHERE c.id = (:id)")
    Optional<Challenge> findByIdAndFetchSongsEagerly(@Param("id") Long id);

}
