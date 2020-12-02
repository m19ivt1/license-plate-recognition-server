package ru.nntu.lprserver.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.nntu.lprserver.controller.dto.LprResponseDto;
import ru.nntu.lprserver.model.LprResult;
import ru.nntu.lprserver.model.SupportedCountry;
import ru.nntu.lprserver.service.LprService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

/**
 * Controller which implements {@code /lpr} REST API service.
 */
@RestController
@RequestMapping("lpr")
@Slf4j
public class LprController {

    @Autowired
    private LprService lprService;

    /**
     * Handles {@link Exception} exceptions.
     *
     * @param e exception object
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleInternalError(Exception e) {
        log.warn("Handling internal error, return HTTP 500 status", e);
    }

    /**
     * Implements {@code /lpr/recognize} REST API endpoint.
     *
     * @param licensePlateImage license plate image
     * @param countryCode requested country code
     * @return HTTP response
     */
    @PostMapping("/recognize")
    public ResponseEntity<LprResponseDto> recognize(
            @RequestParam("license_plate_image") MultipartFile licensePlateImage,
            @RequestParam("country_code") String countryCode,
            HttpServletRequest request) {

        log.trace("License plate recognition request from client ({})", request.getRemoteAddr());

        // validate if counter code is supported
        SupportedCountry country = SupportedCountry.searchByCode(countryCode);
        if (country == null) {
            log.trace("Invalid country code \"{}\"", countryCode);
            return ResponseEntity.badRequest().build();
        }

        // get image data from request
        byte[] imageBytes;
        try {
            imageBytes = licensePlateImage.getBytes();
        } catch (IOException e) {
            log.warn("Error processing incoming image data", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        // call license plate recognition service
        Optional<LprResult> result = lprService.recognize(imageBytes, country);
        if (result.isEmpty()) {
            log.trace("License plate recognition service could not recognize number on requested image");
            // return 404 if no license plate was found
            return ResponseEntity.notFound().build();
        } else {
            log.trace("License plate is recognized, number: {}, confidence: {}",
                    result.get().getNumber(), result.get().getConfidence());
            // return successful response
            LprResponseDto responseDto =
                    new LprResponseDto(result.get().getNumber(), result.get().getConfidence());
            return ResponseEntity.ok(responseDto);
        }
    }
}
