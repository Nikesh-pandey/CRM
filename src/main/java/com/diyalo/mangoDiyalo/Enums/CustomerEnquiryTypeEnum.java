package com.diyalo.mangoDiyalo.Enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum CustomerEnquiryTypeEnum {
    GENERAL_INQUIRY("General Inquiry"),
    BUSINESS_INQUIRY("Business Inquiry");

    private final String value;

    CustomerEnquiryTypeEnum(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static CustomerEnquiryTypeEnum fromValue(String value) {
        return Arrays.stream(values())
                .filter(v -> v.value.equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid Type: " + value));

    }

}
