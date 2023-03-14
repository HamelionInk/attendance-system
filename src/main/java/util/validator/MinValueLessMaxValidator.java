package util.validator;

import com.codeinside.attendancesystem.dto.request.RequestGroupDto;
import util.annotation.MinLessMaxAge;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MinValueLessMaxValidator implements ConstraintValidator<MinLessMaxAge, Object> {

    @Override
    public void initialize(MinLessMaxAge constraintAnnotation) {

    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        RequestGroupDto requestGroupDto = (RequestGroupDto) o;
        return requestGroupDto.getMinAge() < requestGroupDto.getMaxAge();
    }
}
