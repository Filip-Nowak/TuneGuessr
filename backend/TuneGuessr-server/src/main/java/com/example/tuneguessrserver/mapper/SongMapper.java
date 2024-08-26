package com.example.tuneguessrserver.mapper;

import com.example.tuneguessrserver.entity.Song;
import com.example.tuneguessrserver.model.SongModel;

import java.util.List;
import java.util.stream.Collectors;

public class SongMapper {
    public static SongModel toModel(Song song) {
        return SongModel.builder()
                .title(song.getName())
                .artist(song.getArtist())
                .url(song.getUrl())
                .build();
    }
    public static List<SongModel> toModel(List<Song> songs) {
        return songs.stream().map(SongMapper::toModel).collect(Collectors.toList());
    }

}
