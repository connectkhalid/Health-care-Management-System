package com.khalid.hms.UserProfile.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = { ValidRoleValidator.class })
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidRole {
    String message() default "Invalid role. Must be PATIENT or ADMIN";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
