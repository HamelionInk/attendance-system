package com.codeinside.attendancesystem.unit.service;

import com.codeinside.attendancesystem.mapper.StudentMapper;
import com.codeinside.attendancesystem.repository.PersonRepository;
import com.codeinside.attendancesystem.repository.StudentRepository;
import com.codeinside.attendancesystem.service.GroupService;
import com.codeinside.attendancesystem.service.impl.StudentServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;
    @Mock
    private StudentMapper studentMapper;
    @Mock
    private GroupService groupService;
    @Mock
    private PersonRepository personRepository;
    @InjectMocks
    private StudentServiceImpl studentService;

    @BeforeEach
    public void setUp() {

    }

    @AfterEach
    public void tearDown() {

    }

    @Test
    public void saveStudent() {

    }

    @Test
    public void saveStudentExpectedException() {

    }

    @Test
    public void addStudentForGroup() {

    }

    @Test
    public void addStudentForGroupExpectedException() {

    }

    @Test
    public void getStudent() {

    }

    @Test
    public void getStudentExpectedException() {

    }

    @Test
    public void getStudents() {

    }

    @Test
    public void getStudentsExpectedException() {

    }

    @Test
    public void updateStudent() {

    }

    @Test
    public void updateStudentExpectedException() {

    }

    @Test
    public void excludeStudentForGroup() {

    }

    @Test
    public void excludeStudentForGroupExpectedException() {

    }

    @Test
    public void deleteStudent() {

    }

    @Test
    public void deleteStudentExpectedException() {

    }
}
