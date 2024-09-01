package com.example.tuneguessrserver.challenge.requests;

import com.example.tuneguessrserver.response.status.ApiStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
public class AddSongModel {
    @NotBlank(message = "" + ApiStatus.SONG_TITLE_REQUIRED)
    @Length(max = 100, message = "" + ApiStatus.SONG_TITLE_LENGTH)
    private String title;
    @NotBlank(message = "" + ApiStatus.SONG_ARTIST_REQUIRED)
    @Length(max = 100, message = "" + ApiStatus.SONG_ARTIST_LENGTH)
    private String artist;
    @NotBlank(message = "" + ApiStatus.SONG_URL_REQUIRED)
    @Length(max = 200, message = "" + ApiStatus.SONG_URL_LENGTH)
    private String url;
}
