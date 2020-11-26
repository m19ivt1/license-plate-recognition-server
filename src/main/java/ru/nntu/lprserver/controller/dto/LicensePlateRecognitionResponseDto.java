package ru.nntu.lprserver.controller.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class LicensePlateRecognitionResponseDto {

    private String number;

    private BigDecimal confidence;
}
