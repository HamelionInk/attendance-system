package com.codeinside.attendancesystem.util.validator.constraint;

import com.codeinside.attendancesystem.dto.request.RequestGroupDto;
import com.codeinside.attendancesystem.util.annotation.MinLessMaxAge;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MinValueLessMaxValidator implements ConstraintValidator<MinLessMaxAge, Object> {

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        RequestGroupDto requestGroupDto = (RequestGroupDto) o;
        return requestGroupDto.getMinAge() < requestGroupDto.getMaxAge();
    }
}
