package com.example.tuneguessrserver.enums;

public class AuthStatus {
    public static final int MULTIPLE_ERRORS = 1;
    public static final int NICKNAME_REQUIRED = 2;
    public static final int NICKNAME_LENGTH = 3;
    public static final int EMAIL_REQUIRED = 4;
    public static final int EMAIL_FORMAT = 5;
    public static final int PASSWORD_REQUIRED = 6;
    public static final int PASSWORD_LENGTH = 7;
    public static final int EMAIL_SENT = 20;
    public static final int EMAIL_EXISTS = 21;
    public static final int NICKNAME_EXISTS = 22;
    public static final int AUTHENTICATION_SUCCESS = 30;
    public static final int INVALID_CREDENTIALS = 31;
    public static final int EMAIL_CONFIRMED = 40;
    public static final int INVALID_TOKEN = 41;
    public static final int TOKEN_EXPIRED = 42;
    public static final int TOKEN_ALREADY_CONFIRMED = 43;
    public static String getMessage(int status) {
        switch (status) {
            case MULTIPLE_ERRORS:
                return "Multiple errors";
            case EMAIL_SENT:
                return "Email sent";
            case EMAIL_EXISTS:
                return "Email already exists";
            case NICKNAME_EXISTS:
                return "Nickname already exists";
            case AUTHENTICATION_SUCCESS:
                return "Authentication successful";
            case INVALID_CREDENTIALS:
                return "Invalid credentials";
            case EMAIL_CONFIRMED:
                return "Email confirmed";
            case INVALID_TOKEN:
                return "Token not found";
            case TOKEN_EXPIRED:
                return "Token expired";
            case TOKEN_ALREADY_CONFIRMED:
                return "Token already confirmed";
            case NICKNAME_REQUIRED:
                return "Nickname is required";
            case NICKNAME_LENGTH:
                return "Nickname length must be between 3 and 20";
            case EMAIL_REQUIRED:
                return "Email is required";
            case EMAIL_FORMAT:
                return "Invalid email format";
            case PASSWORD_REQUIRED:
                return "Password is required";
            case PASSWORD_LENGTH:
                return "Password length must be between 6 and 40";
            default:
                return "Unknown status";
        }
    }
}
