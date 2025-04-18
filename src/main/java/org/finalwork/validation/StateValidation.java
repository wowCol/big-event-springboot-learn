package org.finalwork.validation;

import org.finalwork.anno.State;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StateValidation implements ConstraintValidator<State, String> {

    // 提供校验规则
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value.equals("已发布") || value.equals("草稿")) {
            return true;
        }
        return false;
    }
}
