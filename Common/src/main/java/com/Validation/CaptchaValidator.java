package com.Validation;

import com.Validation.annotation.Captcha;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CaptchaValidator implements ConstraintValidator<Captcha,String> {

    private int     length   = 0;
    private String REGEX_Captcha ="^[A-Za-z0-9]{4}$";

    @Override
    public void initialize(Captcha constraintAnnotation){
        length   = constraintAnnotation.length();
        REGEX_Captcha = "^[A-Za-z0-9]{"+length+"}$";
    }
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(!StringUtils.hasText(value)){
            return false;
        }
        if(value.length() == length){
            Pattern r = Pattern.compile(REGEX_Captcha);
            Matcher m = r.matcher(value);
            return m.matches();
        }else {
            return false;
        }

    }
}
