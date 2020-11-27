package ru.nntu.lprserver.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class LprResponseDto {

    private String number;

    private float confidence;
}
