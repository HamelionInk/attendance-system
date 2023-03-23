package com.codeinside.attendancesystem.util.validator.constraint;

import com.codeinside.attendancesystem.dto.request.RequestPersonDto;
import com.codeinside.attendancesystem.util.annotation.PasswordMatches;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        RequestPersonDto requestPersonDto = (RequestPersonDto) o;
        if(requestPersonDto.getPassword() == null) {
            return false;
        }
        return requestPersonDto.getPassword().equals(requestPersonDto.getVerifyPassword());
    }
}
