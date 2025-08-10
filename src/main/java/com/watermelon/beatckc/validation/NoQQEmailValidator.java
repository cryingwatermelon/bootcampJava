package com.watermelon.beatckc.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NoQQEmailValidator implements ConstraintValidator<NoQQEmailPattern, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        //判断邮箱是否合法的规则
        System.out.println(s);
        return false;
    }
}
