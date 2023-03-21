package com.codeinside.attendancesystem.unit.service;

import com.codeinside.attendancesystem.mapper.LessonMapper;
import com.codeinside.attendancesystem.repository.AttendanceRepository;
import com.codeinside.attendancesystem.repository.GroupRepository;
import com.codeinside.attendancesystem.repository.LessonRepository;
import com.codeinside.attendancesystem.repository.StudentRepository;
import com.codeinside.attendancesystem.service.impl.LessonServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LessonServiceTest {

    @Mock
    private LessonRepository lessonRepository;
    @Mock
    private LessonMapper lessonMapper;
    @Mock
    private GroupRepository groupRepository;
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private AttendanceRepository attendanceRepository;
    @InjectMocks
    private LessonServiceImpl lessonService;

    @BeforeEach
    public void setUp() {

    }

    @AfterEach
    public void tearDown() {}

    @Test
    public void saveLesson() {

    }

    @Test
    public void saveLessonExpectedException() {

    }

    @Test
    public void getLesson() {

    }

    @Test
    public void getLessonExpectedException() {

    }

    public void getLessons() {

    }

    @Test
    public void getLessonsExpectedException() {

    }

    @Test
    public void getLessonForStudent() {

    }

    @Test
    public void getLessonForStudentExpectedException() {

    }

    @Test
    public void updateLesson() {

    }

    @Test
    public void updateLessonExpectedException() {

    }

    @Test
    public void updateAttendanceLessonForStudent() {

    }

    @Test
    public void updateAttendanceLessonForStudentExpectedException() {

    }

    @Test
    public void deleteLesson() {

    }

    @Test
    public void deleteLessonExpectedException() {

    }
}
