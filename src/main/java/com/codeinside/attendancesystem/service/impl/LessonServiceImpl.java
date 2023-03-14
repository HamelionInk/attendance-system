package com.codeinside.attendancesystem.service.impl;

import com.codeinside.attendancesystem.dto.request.RequestLessonDto;
import com.codeinside.attendancesystem.dto.response.ResponseLessonDto;
import com.codeinside.attendancesystem.dto.response.ResponseStudentDto;
import com.codeinside.attendancesystem.entity.Lesson;
import com.codeinside.attendancesystem.entity.Group;
import com.codeinside.attendancesystem.exception.LessonNotFoundException;
import com.codeinside.attendancesystem.exception.GroupNotFoundException;
import com.codeinside.attendancesystem.mapper.LessonMapper;
import com.codeinside.attendancesystem.repository.LessonRepository;
import com.codeinside.attendancesystem.repository.GroupRepository;
import com.codeinside.attendancesystem.service.AttendanceService;
import com.codeinside.attendancesystem.service.LessonService;
import com.codeinside.attendancesystem.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

@Service
public class LessonServiceImpl implements LessonService {

    private final LessonMapper lessonMapper;
    private final LessonRepository lessonRepository;
    private final GroupRepository groupRepository;
    private final StudentService studentService;
    private final AttendanceService attendanceService;

    @Autowired
    public LessonServiceImpl(LessonMapper lessonMapper, LessonRepository lessonRepository,
                             GroupRepository groupRepository, StudentService studentService,
                             AttendanceService attendanceService) {
        this.lessonMapper = lessonMapper;
        this.lessonRepository = lessonRepository;
        this.groupRepository = groupRepository;
        this.studentService = studentService;
        this.attendanceService = attendanceService;
    }

    @Override
    public void saveLesson(RequestLessonDto requestLessonDto) {
        Optional<Group> groupOptional = groupRepository.findById(requestLessonDto.getGroupId());
        if(groupOptional.isEmpty()) {
            throw new GroupNotFoundException();
        }

        Group group = groupOptional.get();
        Lesson lesson = lessonMapper.requestLessonDtoToLesson(requestLessonDto);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(lesson.getStartDate());
        calendar.add(Calendar.HOUR, 1);
        lesson.setEndDate(calendar.getTime());

        lessonRepository.save(lesson);
        attendanceService.initializationAttendanceEntity(group.getStudents(), lesson.getId());
    }

    @Override
    public ResponseLessonDto getLesson(Long id) {
        Optional<Lesson> lessonOptional = lessonRepository.findById(id);
        if(lessonOptional.isEmpty()) {
            throw new LessonNotFoundException();
        }
        return lessonMapper.LessonToResponseLessonDto(lessonOptional.get());
    }

    @Override
    public List<ResponseLessonDto> getLessons(Long offset, Long limit) {
        List<Lesson> lessons = lessonRepository.selectAllWithOffsetAndLimit(offset, limit);
        if(lessons.isEmpty()) {
            throw new LessonNotFoundException();
        }
        return lessonMapper.lessonsToResponseLessonDtos(lessons);
    }

    @Transactional
    @Override
    public List<ResponseLessonDto> getLessonsForStudent(Long studentId) {
        ResponseStudentDto responseStudentDto = studentService.getStudent(studentId);
        List<Lesson> lessonList = lessonRepository.selectClassesByGroupId(responseStudentDto.getGroupId());
        return lessonMapper.lessonsToResponseLessonDtos(lessonList);
    }

    @Override
    public void deleteLesson(Long id) {
        Optional<Lesson> lessonOptional = lessonRepository.findById(id);
        if(lessonOptional.isEmpty()) {
            throw new LessonNotFoundException();
        }

        lessonRepository.deleteById(id);
    }

    @Override
    public void updateLesson(RequestLessonDto requestLessonDto, Long id) {
        Optional<Lesson> lessonOptional = lessonRepository.findById(id);
        if(lessonOptional.isEmpty()) {
            throw new LessonNotFoundException();
        }
        Lesson lesson = lessonMapper.requestLessonDtoToLessonForPatch(requestLessonDto, lessonOptional.get());
        lessonRepository.save(lesson);
    }

    @Transactional
    @Override
    public void updateAttendanceLessonForStudent(Long lessonId, Long studentId, Boolean attendance) {
        lessonRepository.updateAttendanceLessonForStudent(lessonId, studentId, attendance);
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
