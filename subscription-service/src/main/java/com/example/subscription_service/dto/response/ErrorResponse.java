package com.example.subscription_service.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    private LocalDateTime timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;
}
