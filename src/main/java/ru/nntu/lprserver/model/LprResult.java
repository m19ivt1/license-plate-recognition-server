package ru.nntu.lprserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * License plate recognition result model object.
 */
@Data
@AllArgsConstructor
public class LprResult {

    /**
     * Recognized license plate number.
     */
    private String number;

    /**
     * Recognition confidence level.
     */
    private float confidence;
}
