package util.annotation;

import util.validator.MinMaxAgeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MinMaxAgeValidator.class)
@Documented
public @interface MinMaxAgeValid {
    String message() default "Минимальное значение не может быть больше максимального";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
