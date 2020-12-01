package ru.nntu.lprserver.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Error response DTO.
 */
public class ErrorResponseDto {

    @JsonProperty("error_description")
    private String errorDescription;
}
