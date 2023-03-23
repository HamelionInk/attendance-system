package com.codeinside.attendancesystem.service.impl;

import com.codeinside.attendancesystem.dto.request.RequestLessonDto;
import com.codeinside.attendancesystem.dto.response.ResponseLessonDto;
import com.codeinside.attendancesystem.entity.Group;
import com.codeinside.attendancesystem.entity.Lesson;
import com.codeinside.attendancesystem.entity.Student;
import com.codeinside.attendancesystem.exception.DateMatchesException;
import com.codeinside.attendancesystem.exception.GroupNotFoundException;
import com.codeinside.attendancesystem.exception.LessonNotFoundException;
import com.codeinside.attendancesystem.exception.StudentNotFoundException;
import com.codeinside.attendancesystem.mapper.LessonMapper;
import com.codeinside.attendancesystem.repository.GroupRepository;
import com.codeinside.attendancesystem.repository.LessonRepository;
import com.codeinside.attendancesystem.repository.StudentRepository;
import com.codeinside.attendancesystem.service.AttendanceService;
import com.codeinside.attendancesystem.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonMapper lessonMapper;
    private final LessonRepository lessonRepository;
    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;
    private final AttendanceService attendanceService;

    @Override
    public void saveLesson(RequestLessonDto requestLessonDto) {
        Group group = groupRepository
                .findById(requestLessonDto.getGroupId())
                .orElseThrow(GroupNotFoundException::new);

        Lesson lesson = lessonMapper.requestLessonDtoToLesson(requestLessonDto);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(lesson.getStartDate());
        calendar.add(Calendar.HOUR, 1);
        lesson.setEndDate(calendar.getTime());

        dateMatches(requestLessonDto);

        lessonRepository.save(lesson);
        attendanceService.initializationAttendanceEntity(group.getStudents(), lesson.getId());
    }

    public void dateMatches(RequestLessonDto requestLessonDto) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        List<Lesson> lessons = groupRepository
                .findById(requestLessonDto.getGroupId())
                .orElseThrow(GroupNotFoundException::new)
                .getLessons();

        if(lessons.isEmpty() || requestLessonDto.getStartDate() == null) {
            return;
        }

        for(Lesson lesson : lessons) {
            String currentDate = simpleDateFormat.format(requestLessonDto.getStartDate());
            String lessonDate = simpleDateFormat.format(lesson.getStartDate());
            if(lessonDate.equals(currentDate)) {
                throw new DateMatchesException();
            }
        }
    }

    @Override
    public ResponseLessonDto getLesson(Long id) {
        Lesson lesson = lessonRepository
                .findById(id)
                .orElseThrow(LessonNotFoundException::new);
        return lessonMapper.lessonToResponseLessonDto(lesson);
    }

    @Override
    public List<ResponseLessonDto> getLessons(Long offset, Long limit) {
        List<Lesson> lessons = lessonRepository
                .selectAllWithOffsetAndLimit(offset, limit)
                .orElseThrow(LessonNotFoundException::new);
        return lessonMapper.lessonsToResponseLessonDtos(lessons);
    }

    @Transactional
    @Override
    public List<ResponseLessonDto> getLessonsForStudent(Long studentId) {
        Student student = studentRepository
                .findById(studentId)
                .orElseThrow(StudentNotFoundException::new);
        List<Lesson> lessonList = lessonRepository
                .selectLessonsByGroupId(student.getGroupId())
                .orElseThrow(LessonNotFoundException::new);
        return lessonMapper.lessonsToResponseLessonDtos(lessonList);
    }

    @Override
    public void updateLesson(RequestLessonDto requestLessonDto, Long id) {
        Lesson lesson = lessonRepository
                .findById(id)
                .orElseThrow(LessonNotFoundException::new);
        dateMatches(requestLessonDto);
        Lesson lessonUpdated = lessonMapper.requestLessonDtoToLessonForPatch(requestLessonDto, lesson);
        lessonRepository.save(lessonUpdated);
    }

    @Transactional
    @Override
    public void updateAttendanceLessonForStudent(Long lessonId, Long studentId, Boolean attendance) {
        lessonRepository
                .findById(lessonId)
                .orElseThrow(LessonNotFoundException::new);
        studentRepository
                .findById(studentId)
                .orElseThrow(StudentNotFoundException::new);
        lessonRepository.updateAttendanceLessonForStudent(lessonId, studentId, attendance);
    }

    @Override
    public void deleteLesson(Long id) {
        lessonRepository
                .findById(id)
                .orElseThrow(LessonNotFoundException::new);
        lessonRepository.deleteById(id);
    }

    @Transactional
    @Scheduled(cron = "0 0 * * * *")
    public void endLesson() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Lesson> lessons = lessonRepository.findAll();
        lessons.forEach(data -> {
            String classesDate = simpleDateFormat.format(data.getEndDate());
            String currentDate = simpleDateFormat.format(new Date());
            if(classesDate.equals(currentDate)) {
                lessonRepository.updateEndOfClass(data.getId(), true);
            }
        });
    }
}
