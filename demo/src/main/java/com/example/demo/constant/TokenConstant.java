package com.example.demo.constant;

public class TokenConstant {
    public static final String ACCESS_TOKEN = "accessToken";
    public static final String REFRESH_TOKEN = "refreshToken";
    public static final long ACCESS_TOKEN_EXPIRATION_TIME = 1000 * 60 * 60 * 12;
//    public static final long ACCESS_TOKEN_EXPIRATION_TIME = 1000 * 10;
    public static final long REFRESH_TOKEN_EXPIRATION_TIME = ACCESS_TOKEN_EXPIRATION_TIME * 4;

    public static final String REFRESH_TOKEN_START_TIME = ":refreshTokenStartTime";
}
