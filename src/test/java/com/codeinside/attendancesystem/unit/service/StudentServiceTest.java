package com.codeinside.attendancesystem.unit.service;

import com.codeinside.attendancesystem.dto.request.RequestPersonDto;
import com.codeinside.attendancesystem.dto.request.RequestStudentDto;
import com.codeinside.attendancesystem.dto.response.ResponseGroupDto;
import com.codeinside.attendancesystem.dto.response.ResponsePersonDto;
import com.codeinside.attendancesystem.dto.response.ResponseStudentDto;
import com.codeinside.attendancesystem.entity.Person;
import com.codeinside.attendancesystem.entity.Student;
import com.codeinside.attendancesystem.exception.NumberPhoneAlreadyExistException;
import com.codeinside.attendancesystem.exception.OutOfRangeAgeException;
import com.codeinside.attendancesystem.exception.StudentNotFoundException;
import com.codeinside.attendancesystem.mapper.StudentMapper;
import com.codeinside.attendancesystem.repository.PersonRepository;
import com.codeinside.attendancesystem.repository.StudentRepository;
import com.codeinside.attendancesystem.service.GroupService;
import com.codeinside.attendancesystem.service.impl.StudentServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    private Student student;

    @BeforeEach
    public void setUp() {
        student = new Student();
        Person person = new Person();
        person.setAge(45);
        person.setNumberPhone("89085328288");
        student.setPerson(person);
    }

    @AfterEach
    public void tearDown() {

    }

    @Test
    public void saveStudent() {
        when(studentMapper.requestStudentDtoToStudent(any())).thenReturn(student);

        studentService.saveStudent(any());

        verify(studentRepository, times(1)).save(any());

    }

    @Test
    public void saveStudentExpectedException() {
        when(studentMapper.requestStudentDtoToStudent(any())).thenReturn(student);
        when(personRepository.findByNumberPhone(any())).thenReturn(new Person());

        Assertions.assertThrows(NumberPhoneAlreadyExistException.class, () -> studentService.saveStudent(any()));

        verify(studentRepository, times(0)).save(any());

    }

    @Test
    public void addStudentForGroup() {
        ResponseGroupDto responseGroupDto = new ResponseGroupDto();
        responseGroupDto.setGroupName("TestName");
        responseGroupDto.setNumberOfStudents(34);
        responseGroupDto.setMaxAge(20);
        responseGroupDto.setMinAge(10);
        when(groupService.getGroup(any())).thenReturn(responseGroupDto);
        ResponseStudentDto responseStudentDto = new ResponseStudentDto();
        ResponsePersonDto responsePersonDto = new ResponsePersonDto();
        responsePersonDto.setAge(15);
        responseStudentDto.setGroupId(12L);
        responseStudentDto.setPerson(responsePersonDto);
        List<ResponseStudentDto> responseStudentDtos = new ArrayList<>();
        responseGroupDto.setStudents(responseStudentDtos);
        when(studentRepository.findById(any())).thenReturn(Optional.ofNullable(student));
        when(studentMapper.studentToResponseStudentDto(any())).thenReturn(responseStudentDto);

        studentService.addStudentForGroup(3L, 2L);

        verify(studentRepository, times(1)).updateGroupIdForStudent(2L, 3L);

    }

    @Test
    public void addStudentForGroupExpectedException() {
        ResponseGroupDto responseGroupDto = new ResponseGroupDto();
        responseGroupDto.setGroupName("TestName");
        responseGroupDto.setNumberOfStudents(34);
        responseGroupDto.setMaxAge(20);
        responseGroupDto.setMinAge(10);
        when(groupService.getGroup(any())).thenReturn(responseGroupDto);
        ResponseStudentDto responseStudentDto = new ResponseStudentDto();
        ResponsePersonDto responsePersonDto = new ResponsePersonDto();
        responsePersonDto.setAge(30);
        responseStudentDto.setGroupId(12L);
        responseStudentDto.setPerson(responsePersonDto);
        List<ResponseStudentDto> responseStudentDtos = new ArrayList<>();
        responseGroupDto.setStudents(responseStudentDtos);
        when(studentRepository.findById(any())).thenReturn(Optional.ofNullable(student));
        when(studentMapper.studentToResponseStudentDto(any())).thenReturn(responseStudentDto);

        Assertions.assertThrows(OutOfRangeAgeException.class, () -> studentService.addStudentForGroup(3L, 2L));

        verify(studentRepository, times(0)).updateGroupIdForStudent(2L, 3L);
    }

    @Test
    public void getStudent() {
        when(studentRepository.findById(any())).thenReturn(Optional.ofNullable(student));
        ResponseStudentDto responseStudentDto = new ResponseStudentDto();
        ResponsePersonDto responsePersonDto = new ResponsePersonDto();
        responsePersonDto.setNumberPhone(student.getPerson().getNumberPhone());
        responseStudentDto.setPerson(responsePersonDto);
        when(studentMapper.studentToResponseStudentDto(any())).thenReturn(responseStudentDto);

        studentService.getStudent(any());

        Assertions.assertEquals(student.getPerson().getNumberPhone(), responseStudentDto.getPerson().getNumberPhone());

    }

    @Test
    public void getStudentExpectedException() {
        when(studentRepository.findById(any())).thenReturn(Optional.empty());

        Assertions.assertThrows(StudentNotFoundException.class, () -> studentService.getStudent(any()));
    }

    @Test
    public void getStudents() {
        List<Student> students = new ArrayList<>();
        students.add(student);
        when(studentRepository.selectAllWithOffsetAndLimit(any(), any())).thenReturn(students);
        List<ResponseStudentDto> responseStudentDtos = new ArrayList<>();
        ResponseStudentDto responseStudentDto = new ResponseStudentDto();
        responseStudentDtos.add(responseStudentDto);
        when(studentMapper.studentsToResponseStudentDtos(any())).thenReturn(responseStudentDtos);

        studentService.getStudents(any(),any());

        Assertions.assertEquals(students.size(), responseStudentDtos.size());

    }

    @Test
    public void getStudentsExpectedException() {
        when(studentRepository.selectAllWithOffsetAndLimit(any(), any())).thenReturn(new ArrayList<>());

        Assertions.assertThrows(StudentNotFoundException.class, () -> studentService.getStudents(any(), any()));
    }

    @Test
    public void updateStudent() {
        when(studentRepository.findById(any())).thenReturn(Optional.ofNullable(student));
        when(studentMapper.requestStudentDtoToStudentForPatch(any(), any())).thenReturn(student);
        RequestStudentDto requestStudentDto = new RequestStudentDto();
        RequestPersonDto requestPersonDto = new RequestPersonDto();
        requestPersonDto.setNumberPhone(student.getPerson().getNumberPhone());
        requestStudentDto.setPerson(requestPersonDto);

        studentService.updateStudent(requestStudentDto, any());

        verify(studentRepository, times(1)).save(any());
    }

    @Test
    public void updateStudentExpectedException() {
        when(studentRepository.findById(any())).thenReturn(Optional.empty());

        Assertions.assertThrows(StudentNotFoundException.class, () -> studentService.updateStudent(new RequestStudentDto(), any()));
    }

    @Test
    public void excludeStudentForGroup() {
        when(studentRepository.findById(any())).thenReturn(Optional.ofNullable(student));

        studentService.excludeStudentForGroup(any());

        verify(studentRepository, times(1)).updateGroupIdValueOnNullForStudent(any());

    }

    @Test
    public void excludeStudentForGroupExpectedException() {
        when(studentRepository.findById(any())).thenReturn(Optional.empty());

        Assertions.assertThrows(StudentNotFoundException.class, () -> studentService.excludeStudentForGroup(any()));
        verify(studentRepository, times(0)).updateGroupIdValueOnNullForStudent(any());
    }

    @Test
    public void deleteStudent() {
        when(studentRepository.findById(any())).thenReturn(Optional.ofNullable(student));

        studentService.deleteStudent(any());

        verify(studentRepository, times(1)).deleteById(any());
    }

    @Test
    public void deleteStudentExpectedException() {
        when(studentRepository.findById(any())).thenReturn(Optional.empty());

        Assertions.assertThrows(StudentNotFoundException.class, () -> studentService.deleteStudent(any()));

    }
}
