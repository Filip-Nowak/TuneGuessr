package com.example.tuneguessrserver.challenge;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SongRepostitory extends JpaRepository<Song, Long> {
}
