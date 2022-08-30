package com.example.evently.dto.error;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDto {
    private String code;
    private String message;
    private String emoji;
}
