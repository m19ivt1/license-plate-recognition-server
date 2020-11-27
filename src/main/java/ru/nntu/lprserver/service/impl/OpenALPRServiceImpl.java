package ru.nntu.lprserver.service.impl;

import com.openalpr.jni.*;
import org.springframework.stereotype.Service;
import ru.nntu.lprserver.model.LprResult;
import ru.nntu.lprserver.model.SupportedCountry;
import ru.nntu.lprserver.service.LprService;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.nntu.lprserver.model.SupportedCountry.EUROPE;
import static ru.nntu.lprserver.model.SupportedCountry.UNITED_STATES;

@Service
public class OpenALPRServiceImpl implements LprService {

    // TODO: make this variable configurable
    private static final String ALPR_CONFIGURATION_FILE_PATH = "openalpr.conf";

    // TODO: make this variable configurable
    private static final String ALPR_RUNTIME_DIR_PATH = "./runtime_data";

    private final Map<SupportedCountry, Alpr> alprs = new HashMap<>();

    @PostConstruct
    private void init() {
        alprs.put(UNITED_STATES,
                new Alpr(UNITED_STATES.getCode(), ALPR_CONFIGURATION_FILE_PATH, ALPR_RUNTIME_DIR_PATH));
        alprs.put(EUROPE,
                new Alpr(EUROPE.getCode(), ALPR_CONFIGURATION_FILE_PATH, ALPR_RUNTIME_DIR_PATH));
    }

    @PreDestroy
    private void destroy() {
        for (Alpr alpr: alprs.values()) {
            alpr.unload();
        }
    }

    @Override
    public LprResult recognize(byte[] imageData, SupportedCountry country) {
        try {
            Alpr alpr = alprs.get(country);
            AlprResults results = alpr.recognize(imageData);
            if (results.getPlates().isEmpty()) {
                return null;
            }

            List<AlprPlate> topPlates = results.getPlates().get(0).getTopNPlates();

            if (topPlates.isEmpty()) {
                return null;
            }

            AlprPlate result = topPlates.get(0);

            return new LprResult(result.getCharacters(), result.getOverallConfidence());
        } catch (AlprException e) {
            // TODO: change it
            throw new IllegalArgumentException();
        }
    }
}
