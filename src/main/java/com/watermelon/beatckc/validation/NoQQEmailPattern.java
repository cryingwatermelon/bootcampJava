package com.watermelon.beatckc.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NoQQEmailValidator.class)
public @interface NoQQEmailPattern {
    String message() default "不许用QQ邮箱";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
