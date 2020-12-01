package ru.nntu.lprserver.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * License plate recognition response DTO.
 */
@Data
@AllArgsConstructor
public class LprResponseDto {

    private String number;

    private float confidence;
}
