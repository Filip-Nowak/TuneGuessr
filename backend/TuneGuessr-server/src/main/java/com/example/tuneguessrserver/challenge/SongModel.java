package com.example.tuneguessrserver.challenge;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SongModel implements Serializable {
    private String title;
    private String artist;
    private String url;
}
