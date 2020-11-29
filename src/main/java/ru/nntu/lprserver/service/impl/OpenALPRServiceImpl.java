package ru.nntu.lprserver.service.impl;

import com.openalpr.jni.Alpr;
import com.openalpr.jni.AlprException;
import com.openalpr.jni.AlprPlate;
import com.openalpr.jni.AlprResults;
import org.springframework.stereotype.Service;
import ru.nntu.lprserver.model.LprResult;
import ru.nntu.lprserver.model.SupportedCountry;
import ru.nntu.lprserver.model.exception.LprErrorException;
import ru.nntu.lprserver.service.LprService;

import java.util.List;
import java.util.Optional;

@Service
public class OpenALPRServiceImpl implements LprService {

    // TODO: make this variable configurable
    private static final String ALPR_CONFIGURATION_FILE_PATH = "openalpr.conf";

    // TODO: make this variable configurable
    private static final String ALPR_RUNTIME_DIR_PATH = "./runtime_data";

    @Override
    public Optional<LprResult> recognize(byte[] imageData, SupportedCountry country) {
        Alpr alpr = null;
        try {
            alpr = new Alpr(country.getCode(), ALPR_CONFIGURATION_FILE_PATH, ALPR_RUNTIME_DIR_PATH);
            AlprResults results = alpr.recognize(imageData);
            if (results.getPlates().isEmpty()) {
                return Optional.empty();
            }

            List<AlprPlate> topPlates = results.getPlates().get(0).getTopNPlates();
            if (topPlates.isEmpty()) {
                return Optional.empty();
            }

            AlprPlate result = topPlates.get(0);

            return Optional.of(new LprResult(result.getCharacters(), result.getOverallConfidence()));
        } catch (AlprException e) {
            throw new LprErrorException("License plate recognition error happened", e);
        } finally {
            if (alpr != null) {
                if (alpr.isLoaded()) {
                    alpr.unload();
                }
            }
        }
    }
}
