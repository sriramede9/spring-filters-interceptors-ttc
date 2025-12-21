package com.sri.springrevise.annotations.field;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD}) // Where does it go?
@Retention(RetentionPolicy.RUNTIME) // Keep it alive while the app is running
@Constraint(validatedBy = RouteNameValidator.class) // The "Brain" that does the work
public @interface ValidRouteName {
    String message() default "Invalid Route Name blank or doesn't start with a number"; // Error message
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}