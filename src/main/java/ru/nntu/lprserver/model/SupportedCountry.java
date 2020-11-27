package ru.nntu.lprserver.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SupportedCountry {

    UNITED_STATES("us"),
    EUROPE("eu");

    private final String code;

    public static SupportedCountry searchByCode(String countryCode) {
        for (SupportedCountry country : SupportedCountry.values()) {
            if (country.getCode().equals(countryCode)) {
                return country;
            }
        }

        return null;
    }
}
