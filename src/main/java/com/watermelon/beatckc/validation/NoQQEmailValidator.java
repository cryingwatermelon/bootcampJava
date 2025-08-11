package com.watermelon.beatckc.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NoQQEmailValidator implements ConstraintValidator<NoQQEmailPattern, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        //判断邮箱是否合法的规则,并且不允许QQ邮箱。
        String lowerEmail = s.toLowerCase();
        return !lowerEmail.endsWith("@qq.com") && !lowerEmail.endsWith("@qq.cn");
    }
}
