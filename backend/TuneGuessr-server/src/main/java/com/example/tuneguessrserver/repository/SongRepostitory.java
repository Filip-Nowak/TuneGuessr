package com.example.tuneguessrserver.repository;

import com.example.tuneguessrserver.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongRepostitory extends JpaRepository<Song, Long> {
}
