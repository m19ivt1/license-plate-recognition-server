package ru.nntu.lprserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.nntu.lprserver.controller.dto.LprResponseDto;
import ru.nntu.lprserver.controller.exception.InternalErrorException;
import ru.nntu.lprserver.model.LprResult;
import ru.nntu.lprserver.model.SupportedCountry;
import ru.nntu.lprserver.service.LprService;

import java.io.IOException;

@RestController
@RequestMapping("lpr")
public class LprController {

    @Autowired
    private LprService lprService;

    @ExceptionHandler(InternalErrorException.class)
    public ResponseEntity<Void> handleInternalError() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PostMapping("/recognize")
    public ResponseEntity<LprResponseDto> recognize(
            @RequestParam("license_plate_image") MultipartFile licensePlateImage,
            @RequestParam("country_code") String countryCode) {

        SupportedCountry country = SupportedCountry.searchByCode(countryCode);
        if (country == null) {
            return ResponseEntity.badRequest().build();
        }

        byte[] imageBytes;
        try {
            imageBytes = licensePlateImage.getBytes();
        } catch (IOException e) {
            throw new InternalErrorException(e);
        }

        LprResult result = lprService.recognize(imageBytes, country);
        LprResponseDto responseDto = new LprResponseDto(result.getNumber(), result.getConfidence());
        return ResponseEntity.ok(responseDto);
    }
}
