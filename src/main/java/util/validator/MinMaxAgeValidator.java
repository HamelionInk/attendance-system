package util.validator;

import com.codeinside.attendancesystem.dto.request.RequestGroupDto;
import util.annotation.MinMaxAgeValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MinMaxAgeValidator implements ConstraintValidator<MinMaxAgeValid, Object> {

    @Override
    public void initialize(MinMaxAgeValid constraintAnnotation) {

    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        RequestGroupDto requestGroupDto = (RequestGroupDto) o;
        return requestGroupDto.getMinAge() < requestGroupDto.getMaxAge();
    }
}
