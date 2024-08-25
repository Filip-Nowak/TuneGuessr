package com.example.tuneguessrserver.enums;

public class AuthStatus {
    public static final int MULTIPLE_ERRORS = 1;
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
                return "Invalid token";
            case TOKEN_EXPIRED:
                return "Token expired";
            case TOKEN_ALREADY_CONFIRMED:
                return "Token already confirmed";
            default:
                return "Unknown status";
        }
    }
}
