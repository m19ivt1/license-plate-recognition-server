package ru.nntu.lprserver.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorResponseDto {

    @JsonProperty("error_description")
    private String errorDescription;
}
