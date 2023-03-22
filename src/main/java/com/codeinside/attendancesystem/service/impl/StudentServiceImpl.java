package com.codeinside.attendancesystem.service.impl;

import com.codeinside.attendancesystem.dto.request.RequestStudentDto;
import com.codeinside.attendancesystem.dto.response.ResponseGroupDto;
import com.codeinside.attendancesystem.dto.response.ResponseStudentDto;
import com.codeinside.attendancesystem.entity.Student;
import com.codeinside.attendancesystem.enums.TypeUser;
import com.codeinside.attendancesystem.exception.NumberPhoneAlreadyExistException;
import com.codeinside.attendancesystem.exception.OutOfNumberOfStudentsException;
import com.codeinside.attendancesystem.exception.OutOfRangeAgeException;
import com.codeinside.attendancesystem.exception.StudentNotFoundException;
import com.codeinside.attendancesystem.mapper.StudentMapper;
import com.codeinside.attendancesystem.repository.PersonRepository;
import com.codeinside.attendancesystem.repository.StudentRepository;
import com.codeinside.attendancesystem.service.GroupService;
import com.codeinside.attendancesystem.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentMapper studentMapper;
    private final StudentRepository studentRepository;
    private final PersonRepository personRepository;
    private final GroupService groupService;

    @Override
    public void saveStudent(RequestStudentDto requestStudentDto) {
        Student student = studentMapper.requestStudentDtoToStudent(requestStudentDto);
        student.getPerson().setTypeUser(TypeUser.STUDENT);
        numberPhoneAlreadyExist(student.getPerson().getNumberPhone());
        studentRepository.save(student);
    }

    public void numberPhoneAlreadyExist(String numberPhone) {
        if(personRepository.findByNumberPhone(numberPhone) != null) {
            throw new NumberPhoneAlreadyExistException();
        }
    }

    @Transactional
    @Override
    public void addStudentForGroup(Long studentId, Long groupId) {
        ResponseGroupDto responseGroupDto = groupService.getGroup(groupId);
        ResponseStudentDto responseStudentDto = getStudent(studentId);

        if(!checkRangeAge(responseGroupDto.getMinAge(), responseGroupDto.getMaxAge(), responseStudentDto.getPerson().getAge())) {
            throw new OutOfRangeAgeException();
        }

        if(!checkOutOfNumberOfStudents(responseGroupDto.getStudents().size(), responseGroupDto.getNumberOfStudents())) {
            throw new OutOfNumberOfStudentsException();
        }

        studentRepository.updateGroupIdForStudent(groupId, studentId);
    }

    private Boolean checkOutOfNumberOfStudents(int numbersOfStudent, int limitStudents) {
        return numbersOfStudent <= limitStudents;
    }

    private Boolean checkRangeAge(int min, int max, int init) {
        return init >= min && init <= max;
    }

    @Override
    public ResponseStudentDto getStudent(Long id) {
        Optional<Student> studentOptional = studentRepository.findById(id);
        if(studentOptional.isEmpty()) {
            throw new StudentNotFoundException();
        }
        return studentMapper.studentToResponseStudentDto(studentOptional.get());
    }

    @Override
    public List<ResponseStudentDto> getStudents(Long offset, Long limit) {
        List<Student> students = studentRepository.selectAllWithOffsetAndLimit(offset, limit);
        if(students.isEmpty()) {
            throw new StudentNotFoundException();
        }
        return studentMapper.studentsToResponseStudentDtos(students);
    }

    @Override
    public void updateStudent(RequestStudentDto requestStudentDto, Long id) {
        Optional<Student> studentOptional = studentRepository.findById(id);
        if(studentOptional.isEmpty()) {
            throw new StudentNotFoundException();
        }
        Student student = studentMapper.requestStudentDtoToStudentForPatch(requestStudentDto, studentOptional.get());
        if(requestStudentDto.getPerson().getNumberPhone() != null) {
            numberPhoneAlreadyExist(student.getPerson().getNumberPhone());
        }
        studentRepository.save(student);
    }

    @Transactional
    @Override
    public void excludeStudentForGroup(Long studentId) {
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        if(studentOptional.isEmpty()) {
            throw new StudentNotFoundException();
        }
        studentRepository.updateGroupIdValueOnNullForStudent(studentId);
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
