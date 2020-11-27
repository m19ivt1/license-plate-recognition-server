package ru.nntu.lprserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LprResult {

    private String number;

    private float confidence;
}
