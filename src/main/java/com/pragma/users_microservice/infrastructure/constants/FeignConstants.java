package com.pragma.users_microservice.infrastructure.constants;

public class FeignConstants {
    private FeignConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String FEIGN_CLIENT_NAME = "foodcourt-microservice";
    public static final String FEIGN_CLIENT_URL = "localhost:8090/restaurant";
}
