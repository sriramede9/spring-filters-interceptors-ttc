package com.sri.springrevise.annotations.field;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD}) // Where does it go?
@Retention(RetentionPolicy.RUNTIME) // Keep it alive while the app is running
@Constraint(validatedBy = BoardingStopValidator.class) // The "Brain" that does the work
public @interface ValidBoardingStop {
    String message() default "Invalid Boarding Stop"; // Error message
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}