package org.example.geolocationservice.dtos;

public record ApiResponse<T>(
        boolean success,
        String message,
        T data
) {}
