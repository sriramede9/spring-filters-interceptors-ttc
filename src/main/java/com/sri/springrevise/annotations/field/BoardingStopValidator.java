package com.sri.springrevise.annotations.field;

import com.sri.springrevise.configuration.TransitProperties;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BoardingStopValidator implements ConstraintValidator<ValidBoardingStop, String> {

    private final TransitProperties transitProperties;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return true;

        // Dynamic check against your YAML list
        return transitProperties.getAllowedStops().contains(value);
    }
}