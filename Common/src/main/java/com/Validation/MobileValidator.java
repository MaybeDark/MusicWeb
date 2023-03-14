package com.Validation;

import com.Validation.annotation.Mobile;
import org.springframework.util.PatternMatchUtils;
import org.springframework.util.StringUtils;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MobileValidator implements ConstraintValidator<Mobile,String> {
    private boolean required = false;
    private static final String REGEX_MOBILE ="((\\+86|0086)?\\s*)((134[0-8]\\d{7})|(((13([0-3]|[5-9]))|(14[5-9])|15([0-3]|[5-9])|(16(2|[5-7]))|17([0-3]|[5-8])|18[0-9]|19(1|[8-9]))\\d{8})|(14(0|1|4)0\\d{7})|(1740([0-5]|[6-9]|[10-12])\\d{7}))";

    @Override
    public void initialize(Mobile constraintAnnotation){
        required = constraintAnnotation.required();
    }
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(required){
            if(StringUtils.hasText(value)){
                return PatternMatchUtils.simpleMatch(REGEX_MOBILE,value);
            }else{
                return false;
            }
        }else {
            return PatternMatchUtils.simpleMatch(REGEX_MOBILE,value);
        }
    }
}
