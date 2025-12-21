package com.sri.springrevise.annotations.field;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RouteNameValidator implements ConstraintValidator<ValidRouteName, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // 1. Always check for null first to avoid NullPointerException
        if (value == null || value.isBlank()) {
            return false;
        }

        // 2. Logic: Must start with a number (1-3 digits) followed by a name
        // Example: "501 Queen" or "29 Dufferin"
        return value.matches("^\\d{1,3}\\s[a-zA-Z\\s]+$");
    }
}