package util.validator;

import com.codeinside.attendancesystem.dto.request.post.RequestLessonDto;
import com.codeinside.attendancesystem.entity.Lesson;
import com.codeinside.attendancesystem.exception.GroupNotFoundException;
import com.codeinside.attendancesystem.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import util.annotation.DateMatches;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.SimpleDateFormat;
import java.util.List;

public class DateMatchesValidator implements ConstraintValidator<DateMatches, Object> {

    private final GroupRepository groupRepository;

    @Autowired
    public DateMatchesValidator(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        RequestLessonDto requestLessonDto = (RequestLessonDto) o;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        boolean flag = false;

        List<Lesson> lessons = groupRepository
                .findById(requestLessonDto.getGroupId())
                .orElseThrow(GroupNotFoundException::new)
                .getLessons();

        if(lessons.isEmpty()) {
            return true;
        }

        for(Lesson lesson : lessons) {
            String currentDate = simpleDateFormat.format(requestLessonDto.getStartDate());
            String classDate = simpleDateFormat.format(lesson.getStartDate());
            flag = !classDate.equals(currentDate);
        }
        return flag;
    }
}