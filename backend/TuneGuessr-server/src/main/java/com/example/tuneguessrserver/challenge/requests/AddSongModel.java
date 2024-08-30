package com.example.tuneguessrserver.challenge.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddSongModel {
    @NotBlank(message = "Title is required")
    private String title;
    @NotBlank(message = "Artist is required")
    private String artist;
    @NotBlank(message = "URL is required")
    private String url;
}
