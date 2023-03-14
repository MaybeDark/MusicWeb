package com.Validation;

import com.Constant.constant;
import com.Validation.annotation.FormData;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class FormDataValidator implements ConstraintValidator<FormData,String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value != null) {
            for (String str : constant.illegal_character) {
                if (value.contains(str)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}