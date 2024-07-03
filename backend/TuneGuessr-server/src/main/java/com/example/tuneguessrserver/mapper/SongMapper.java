package com.example.tuneguessrserver.mapper;

import com.example.tuneguessrserver.entity.Song;
import com.example.tuneguessrserver.model.SongModel;

public class SongMapper {
    public static SongModel toModel(Song song) {
        return SongModel.builder()
                .title(song.getName())
                .artist(song.getArtist())
                .url(song.getUrl())
                .build();
    }

}
