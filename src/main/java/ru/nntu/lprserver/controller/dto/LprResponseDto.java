package ru.nntu.lprserver.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LprResponseDto {

    private String number;

    private float confidence;
}
