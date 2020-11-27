package ru.nntu.lprserver.service;

import ru.nntu.lprserver.model.LprResult;
import ru.nntu.lprserver.model.SupportedCountry;

public interface LprService {

    LprResult recognize(byte[] imageData, SupportedCountry country);
}
