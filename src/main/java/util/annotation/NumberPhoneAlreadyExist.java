package util.annotation;

import util.validator.NumberPhoneAlreadyExistValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NumberPhoneAlreadyExistValidator.class)
@Documented
public @interface NumberPhoneAlreadyExist {

    String message() default "Пользователь с таким номером уже существует";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
