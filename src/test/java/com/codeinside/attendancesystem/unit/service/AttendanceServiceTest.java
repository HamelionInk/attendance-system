package com.codeinside.attendancesystem.unit.service;

import com.codeinside.attendancesystem.entity.Person;
import com.codeinside.attendancesystem.entity.Student;
import com.codeinside.attendancesystem.repository.AttendanceRepository;
import com.codeinside.attendancesystem.service.impl.AttendanceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AttendanceServiceTest {
    @Mock
    private AttendanceRepository attendanceRepository;
    @InjectMocks
    private AttendanceServiceImpl attendanceService;
    private Student student;

    @BeforeEach
    public void setUp() {
        Person person = new Person();
        person.setFirstName("test");
        person.setLastName("person");
        person.setPatronymic("per");
        student = new Student();
        student.setId(1L);
        student.setGroupId(23L);
        student.setPerson(person);
    }

    @Test
    public void initializationAttendanceEntity() {
        List<Student> students = new ArrayList<>();
        students.add(student);
        attendanceService.initializationAttendanceEntity(students, 2L);
        verify(attendanceRepository, times(1)).saveAll(any());
    }

}
