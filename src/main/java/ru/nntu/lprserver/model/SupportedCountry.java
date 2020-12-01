package ru.nntu.lprserver.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum which represents supported license plate countries.
 */
@AllArgsConstructor
@Getter
public enum SupportedCountry {

    /**
     * USA.
     */
    UNITED_STATES("us"),

    /**
     * Europe countries.
     */
    EUROPE("eu");

    private final String code;

    /**
     * Searches enum value by country code text.
     *
     * @param countryCode country code
     * @return enum value
     */
    public static SupportedCountry searchByCode(String countryCode) {
        for (SupportedCountry country : SupportedCountry.values()) {
            if (country.getCode().equals(countryCode)) {
                return country;
            }
        }
        return null;
    }
}
