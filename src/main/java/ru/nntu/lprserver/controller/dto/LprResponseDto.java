package ru.nntu.lprserver.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * License plate recognition response DTO.
 */
@Data
@AllArgsConstructor
public class LprResponseDto {

    /**
     * Recognized number.
     */
    private String number;

    /**
     * Recognition confidence.
     */
    private float confidence;
}
