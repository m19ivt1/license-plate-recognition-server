package ru.nntu.lprserver.service.impl;

import com.openalpr.jni.Alpr;
import com.openalpr.jni.AlprException;
import com.openalpr.jni.AlprPlate;
import com.openalpr.jni.AlprResults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.nntu.lprserver.model.LprResult;
import ru.nntu.lprserver.model.SupportedCountry;
import ru.nntu.lprserver.model.exception.LprErrorException;
import ru.nntu.lprserver.service.LprService;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of {@code LprService} which uses open source {@code OpenALPR} library.
 */
@Service
@Slf4j
public class OpenALPRServiceImpl implements LprService {

    @Value("${openalpr.configuration-file-path}")
    private String openAlprConfigurationFilePath;

    @Value("${openalpr.runtime-dir-path}")
    private String openAlprRuntimeDirPath;

    @Override
    public Optional<LprResult> recognize(byte[] imageData, SupportedCountry country) {
        log.trace("Started license plate number recognition, countryCode = {}", country.getCode());
        Alpr alpr = null;
        try {
            log.debug("Loading OpenALPR client");
            alpr = new Alpr(country.getCode(), openAlprConfigurationFilePath, openAlprRuntimeDirPath);
            AlprResults results = alpr.recognize(imageData);
            if (results.getPlates().isEmpty()) {
                return Optional.empty();
            }

            List<AlprPlate> topPlates = results.getPlates().get(0).getTopNPlates();
            if (topPlates.isEmpty()) {
                log.trace("No license plate recognized");
                return Optional.empty();
            }

            log.debug("{} license plate variants recognized", topPlates.size());

            AlprPlate result = topPlates.get(0);
            log.trace("License plate number is recognized, number: {}, confidence: {}",
                    result.getCharacters(), result.getOverallConfidence());

            return Optional.of(new LprResult(result.getCharacters(), result.getOverallConfidence()));
        } catch (AlprException e) {
            log.warn("OpenALPR error happened", e);
            throw new LprErrorException("License plate recognition error happened", e);
        } finally {
            if (alpr != null) {
                if (alpr.isLoaded()) {
                    log.debug("Unloading OpenALPR client");
                    alpr.unload();
                }
            }
        }
    }
}
