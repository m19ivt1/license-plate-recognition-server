package ru.nntu.lprserver.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.nntu.lprserver.controller.dto.LicensePlateRecognitionResponseDto;

@RestController
@RequestMapping("lpr")
public class LicensePlateRecognitionController {

    @PostMapping("/recognize")
    public LicensePlateRecognitionResponseDto recognize(
            @RequestParam("license_plate_image") MultipartFile licensePlateImage,
            @RequestParam("country_code") String countryCode) {

        return null;
    }
}
