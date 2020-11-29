package ru.nntu.lprserver.service;

import ru.nntu.lprserver.model.LprResult;
import ru.nntu.lprserver.model.SupportedCountry;

import java.util.Optional;

public interface LprService {

    Optional<LprResult> recognize(byte[] imageData, SupportedCountry country);
}
