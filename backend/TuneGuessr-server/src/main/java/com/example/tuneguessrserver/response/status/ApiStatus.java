package com.example.tuneguessrserver.response.status;

public class ApiStatus {
    public static final int UNEXPECTED_ERROR = 101;
    public static final int CHALLENGE_NOT_FOUND = 102;
    public static final int NOT_CHALLENGE_OWNER = 103;
    public static final int SONG_NOT_FOUND = 104;
    public static final int USER_NOT_FOUND = 105;
    public static final int CHALLENGE_NAME_REQUIRED = 106;
    public static final int CHALLENGE_NAME_LENGTH = 107;
    public static final int CHALLENGE_DESCRIPTION_LENGTH = 108;
    public static final int SONG_TITLE_REQUIRED = 109;
    public static final int SONG_TITLE_LENGTH = 111;
    public static final int SONG_ARTIST_REQUIRED = 112;
    public static final int SONG_ARTIST_LENGTH = 113;
    public static final int SONG_URL_REQUIRED = 114;
    public static final int SONG_URL_LENGTH = 115;



    public static String getMessage(int status) {
        switch (status) {
            case CHALLENGE_NOT_FOUND:
                return "Challenge not found";
            case NOT_CHALLENGE_OWNER:
                return "You are not the owner of this challenge";
            case SONG_NOT_FOUND:
                return "Song not found";
            case USER_NOT_FOUND:
                return "User not found";
            case CHALLENGE_NAME_REQUIRED:
                return "Challenge name is required";
            case CHALLENGE_NAME_LENGTH:
                return "Challenge name must be at most 40 characters long";
            case CHALLENGE_DESCRIPTION_LENGTH:
                return "Challenge description must be at most 200 characters long";
            case SONG_TITLE_REQUIRED:
                return "Song title is required";
            case SONG_TITLE_LENGTH:
                return "Song title must be at most 100 characters long";
            case SONG_ARTIST_REQUIRED:
                return "Song artist is required";
            case SONG_ARTIST_LENGTH:
                return "Song artist must be at most 100 characters long";
            case SONG_URL_REQUIRED:
                return "Song URL is required";
            case SONG_URL_LENGTH:
                return "Song URL must be at most 200 characters long";
            default:
                return "An unexpected error occurred";
        }
    }
}
