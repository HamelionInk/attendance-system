package com.codeinside.attendancesystem.service.impl;

import com.codeinside.attendancesystem.dto.request.RequestStudentDto;
import com.codeinside.attendancesystem.dto.response.ResponseStudentDto;
import com.codeinside.attendancesystem.entity.Student;
import com.codeinside.attendancesystem.enums.TypeUser;
import com.codeinside.attendancesystem.exception.StudentNotFoundException;
import com.codeinside.attendancesystem.mapper.StudentMapper;
import com.codeinside.attendancesystem.repository.StudentRepository;
import com.codeinside.attendancesystem.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentMapper studentMapper;
    private final StudentRepository studentRepository;

    @Autowired
    public StudentServiceImpl(StudentMapper studentMapper, StudentRepository studentRepository) {
        this.studentMapper = studentMapper;
        this.studentRepository = studentRepository;
    }

    @Override
    public void saveStudent(RequestStudentDto requestStudentDto) {
        Student student = studentMapper.requestStudentDtoToStudent(requestStudentDto);
        student.getPerson().setTypeUser(TypeUser.STUDENT);
        studentRepository.save(student);
    }

    @Override
    public List<ResponseStudentDto> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        if(students.isEmpty()) {
            throw new StudentNotFoundException();
        }
        return studentMapper.StudentListToResponseStudentListDto(students);
    }

    @Override
    public ResponseStudentDto getStudent(Long id) {
        Optional<Student> studentOptional = studentRepository.findById(id);
        if(studentOptional.isEmpty()) {
            throw new StudentNotFoundException();
        }
        return studentMapper.StudentToResponseStudentDto(studentOptional.get());
    }

    @Override
    public void updateStudent(RequestStudentDto requestStudentDto, Long id) {
        Optional<Student> studentOptional = studentRepository.findById(id);
        if(studentOptional.isEmpty()) {
            throw new StudentNotFoundException();
        }
        Student student = studentMapper.requestStudentDtoToStudentForPatch(requestStudentDto, studentOptional.get());
        studentRepository.save(student);
    }

    @Override
    public void deleteStudent(Long id) {
        Optional<Student> studentOptional = studentRepository.findById(id);
        if(studentOptional.isEmpty()) {
            throw new StudentNotFoundException();
        }
        studentRepository.deleteById(id);
    }
}
