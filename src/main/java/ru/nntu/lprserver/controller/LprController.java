package ru.nntu.lprserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.nntu.lprserver.controller.dto.ErrorResponseDto;
import ru.nntu.lprserver.controller.dto.LprResponseDto;
import ru.nntu.lprserver.model.LprResult;
import ru.nntu.lprserver.model.SupportedCountry;
import ru.nntu.lprserver.model.exception.LprErrorException;
import ru.nntu.lprserver.service.LprService;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("lpr")
public class LprController {

    @Autowired
    private LprService lprService;

    @ExceptionHandler(LprErrorException.class)
    public ResponseEntity<ErrorResponseDto> handleInternalError(LprErrorException e) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorResponseDto);
    }

    @PostMapping("/recognize")
    public ResponseEntity<LprResponseDto> recognize(
            @RequestParam("license_plate_image") MultipartFile licensePlateImage,
            @RequestParam("country_code") String countryCode) {

        // validate if counter code is supported
        SupportedCountry country = SupportedCountry.searchByCode(countryCode);
        if (country == null) {
            return ResponseEntity.badRequest().build();
        }

        // get image data from request
        byte[] imageBytes;
        try {
            imageBytes = licensePlateImage.getBytes();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        // call license plate recognition service
        Optional<LprResult> result = lprService.recognize(imageBytes, country);
        if (result.isEmpty()) {
            // return 404 if no license plate was found
            return ResponseEntity.notFound().build();
        } else {
            // return successful response
            LprResponseDto responseDto =
                    new LprResponseDto(result.get().getNumber(), result.get().getConfidence());
            return ResponseEntity.ok(responseDto);
        }
    }
}
