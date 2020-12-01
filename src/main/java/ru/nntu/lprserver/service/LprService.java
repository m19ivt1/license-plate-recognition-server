package ru.nntu.lprserver.service;

import ru.nntu.lprserver.model.LprResult;
import ru.nntu.lprserver.model.SupportedCountry;

import java.util.Optional;

/**
 * License plate recognition service.
 */
public interface LprService {

    /**
     * Recognizes license plate on image.
     *
     * @param imageData array of bytes which represents image
     * @param country   requested country code
     * @return {@code LprResult} object which contains recognized license plate and result's confidence
     */
    Optional<LprResult> recognize(byte[] imageData, SupportedCountry country);
}
