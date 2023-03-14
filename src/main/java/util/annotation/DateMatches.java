package util.annotation;

import util.validator.DateMatchesValidator;
import util.validator.MinValueLessMaxValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateMatchesValidator.class)
@Documented
public @interface DateMatches {
    String message() default "Число для занятия уже занято";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
