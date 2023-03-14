package util.validator;

import com.codeinside.attendancesystem.dto.request.RequestPersonDto;
import com.codeinside.attendancesystem.entity.Person;
import com.codeinside.attendancesystem.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import util.annotation.NumberPhoneAlreadyExist;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class NumberPhoneAlreadyExistValidator implements ConstraintValidator<NumberPhoneAlreadyExist, Object> {

    private final PersonRepository personRepository;

    @Autowired
    public NumberPhoneAlreadyExistValidator(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        RequestPersonDto requestPersonDto = (RequestPersonDto) o;
        List<Person> persons = personRepository.findAll();
        for (Person person : persons) {
            if (person.getNumberPhone().equals(requestPersonDto.getNumberPhone())) {
                return false;
            }
        }
        return true;
    }
}
