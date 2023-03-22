package com.codeinside.attendancesystem.unit.service;

import com.codeinside.attendancesystem.dto.request.RequestLessonDto;
import com.codeinside.attendancesystem.dto.response.ResponseGroupDto;
import com.codeinside.attendancesystem.dto.response.ResponseLessonDto;
import com.codeinside.attendancesystem.dto.response.ResponseStudentDto;
import com.codeinside.attendancesystem.entity.Group;
import com.codeinside.attendancesystem.entity.Lesson;
import com.codeinside.attendancesystem.entity.Student;
import com.codeinside.attendancesystem.exception.LessonNotFoundException;
import com.codeinside.attendancesystem.mapper.LessonMapper;
import com.codeinside.attendancesystem.repository.AttendanceRepository;
import com.codeinside.attendancesystem.repository.GroupRepository;
import com.codeinside.attendancesystem.repository.LessonRepository;
import com.codeinside.attendancesystem.repository.StudentRepository;
import com.codeinside.attendancesystem.service.AttendanceService;
import com.codeinside.attendancesystem.service.StudentService;
import com.codeinside.attendancesystem.service.impl.LessonServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LessonServiceTest {

    @Mock
    private LessonRepository lessonRepository;
    @Mock
    private LessonMapper lessonMapper;
    @Mock
    private GroupRepository groupRepository;
    @Mock
    private StudentService studentService;
    @Mock
    private AttendanceService attendanceService;
    @InjectMocks
    private LessonServiceImpl lessonService;
    private Group group;
    private Lesson lesson;

    @BeforeEach
    public void setUp() {
        List<Student> students = new ArrayList<>();
        Student student = new Student();
        students.add(student);

        group = new Group();
        group.setId(3L);
        group.setGroupName("TestName");
        group.setStudents(students);

        lesson = new Lesson();
        lesson.setLessonName("TestNameLesson");
        lesson.setCoachId(3L);
        lesson.setGroupId(3L);
        lesson.setStartDate(new Date());

        List<Lesson> lessons = new ArrayList<>();
        lessons.add(lesson);
        group.setLessons(lessons);
    }

    @AfterEach
    public void tearDown() {}

    @Test
    public void saveLesson() {
        RequestLessonDto requestLessonDto = new RequestLessonDto();
        requestLessonDto.setGroupId(3L);
        when(groupRepository.findById(any())).thenReturn(Optional.ofNullable(group));
        when(lessonMapper.requestLessonDtoToLesson(any())).thenReturn(lesson);

        lessonService.saveLesson(requestLessonDto);

        verify(lessonRepository, times(1)).save(any());
    }

    @Test
    public void getLesson() {
        when(lessonRepository.findById(any())).thenReturn(Optional.ofNullable(lesson));
        ResponseLessonDto responseLessonDto = new ResponseLessonDto();
        responseLessonDto.setLessonName(lesson.getLessonName());
        when(lessonMapper.lessonToResponseLessonDto(any())).thenReturn(responseLessonDto);

        lessonService.getLesson(any());

        Assertions.assertEquals(lesson.getLessonName(), responseLessonDto.getLessonName());

    }

    @Test
    public void getLessonExpectedException() {
        when(lessonRepository.findById(any())).thenReturn(Optional.empty());

        Assertions.assertThrows(LessonNotFoundException.class, () -> lessonService.getLesson(any()));
    }

    @Test
    public void getLessons() {
        List<Lesson> lessons = new ArrayList<>();
        lessons.add(lesson);
        when(lessonRepository.selectAllWithOffsetAndLimit(any(), any())).thenReturn(lessons);
        ResponseLessonDto responseLessonDto = new ResponseLessonDto();
        responseLessonDto.setLessonName(lesson.getLessonName());
        List<ResponseLessonDto> responseLessonDtos = new ArrayList<>();
        responseLessonDtos.add(responseLessonDto);
        when(lessonMapper.lessonsToResponseLessonDtos(any())).thenReturn(responseLessonDtos);

        lessonService.getLessons(any(), any());

        Assertions.assertEquals(lessons.size(), responseLessonDtos.size());
    }

    @Test
    public void getLessonsExpectedException() {
        when(lessonRepository.selectAllWithOffsetAndLimit(any(), any())).thenReturn(new ArrayList<>());

        Assertions.assertThrows(LessonNotFoundException.class, () -> lessonService.getLessons(any(), any()));
    }

    @Test
    public void getLessonForStudent() {
        ResponseStudentDto student = new ResponseStudentDto();
        student.setGroupId(5L);
        when(studentService.getStudent(any())).thenReturn(student);
        List<Lesson> lessons = new ArrayList<>();
        lessons.add(lesson);
        when(lessonRepository.selectClassesByGroupId(any())).thenReturn(lessons);
        ResponseLessonDto responseLessonDto = new ResponseLessonDto();
        responseLessonDto.setLessonName(lesson.getLessonName());
        List<ResponseLessonDto> responseLessonDtos = new ArrayList<>();
        responseLessonDtos.add(responseLessonDto);
        when(lessonMapper.lessonsToResponseLessonDtos(any())).thenReturn(responseLessonDtos);

        lessonService.getLessonsForStudent(any());

        Assertions.assertEquals(lessons.size(), responseLessonDtos.size());

    }

    @Test
    public void updateLesson() {
        RequestLessonDto requestLessonDto = new RequestLessonDto();
        requestLessonDto.setGroupId(3L);
        when(groupRepository.findById(any())).thenReturn(Optional.ofNullable(group));
        when(lessonRepository.findById(any())).thenReturn(Optional.ofNullable(lesson));
        when(lessonMapper.requestLessonDtoToLessonForPatch(any(), any())).thenReturn(lesson);

        lessonService.updateLesson(requestLessonDto, any());

        verify(lessonRepository, times(1)).save(any());
    }

    @Test
    public void updateLessonExpectedException() {
        when(lessonRepository.findById(any())).thenReturn(Optional.empty());

        Assertions.assertThrows(LessonNotFoundException.class, () -> lessonService.updateLesson(new RequestLessonDto(), any()));
    }

    @Test
    public void updateAttendanceLessonForStudent() {
        when(lessonRepository.findById(any())).thenReturn(Optional.ofNullable(lesson));
        ResponseStudentDto student = new ResponseStudentDto();
        student.setId(1L);
        student.setGroupId(5L);
        when(studentService.getStudent(any())).thenReturn(student);

        lessonService.updateAttendanceLessonForStudent(2L, 1L, true);

        verify(lessonRepository, times(1)).updateAttendanceLessonForStudent(2L, 1L, true);
    }

    @Test
    public void updateAttendanceLessonForStudentExpectedException() {
        when(lessonRepository.findById(any())).thenReturn(Optional.empty());

        Assertions.assertThrows(LessonNotFoundException.class, () -> lessonService.updateAttendanceLessonForStudent(2L, 1L, false));
    }

    @Test
    public void deleteLesson() {
        when(lessonRepository.findById(any())).thenReturn(Optional.ofNullable(lesson));

        lessonService.deleteLesson(any());

        verify(lessonRepository, times(1)).deleteById(any());
    }

    @Test
    public void deleteLessonExpectedException() {
        when(lessonRepository.findById(any())).thenReturn(Optional.empty());

        Assertions.assertThrows(LessonNotFoundException.class, () -> lessonService.deleteLesson(any()));
    }
}
