package com.codeinside.attendancesystem.service.impl;

import com.codeinside.attendancesystem.dto.request.RequestStudentDto;
import com.codeinside.attendancesystem.dto.response.ResponseStudentDto;
import com.codeinside.attendancesystem.entity.Group;
import com.codeinside.attendancesystem.entity.Student;
import com.codeinside.attendancesystem.enums.TypeUser;
import com.codeinside.attendancesystem.exception.GroupNotFoundException;
import com.codeinside.attendancesystem.exception.NumberPhoneAlreadyExistException;
import com.codeinside.attendancesystem.exception.OutOfNumberOfStudentsException;
import com.codeinside.attendancesystem.exception.OutOfRangeAgeException;
import com.codeinside.attendancesystem.exception.StudentNotFoundException;
import com.codeinside.attendancesystem.mapper.StudentMapper;
import com.codeinside.attendancesystem.repository.GroupRepository;
import com.codeinside.attendancesystem.repository.PersonRepository;
import com.codeinside.attendancesystem.repository.StudentRepository;
import com.codeinside.attendancesystem.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentMapper studentMapper;
    private final StudentRepository studentRepository;
    private final PersonRepository personRepository;
    private final GroupRepository groupRepository;

    @Override
    public void saveStudent(RequestStudentDto requestStudentDto) {
        Student student = studentMapper.requestStudentDtoToStudent(requestStudentDto);
        student.getPerson().setTypeUser(TypeUser.STUDENT);
        numberPhoneAlreadyExist(student.getPerson().getNumberPhone());
        studentRepository.save(student);
    }

    public void numberPhoneAlreadyExist(String numberPhone) {
        if(personRepository.findByNumberPhone(numberPhone).isPresent()) {
            throw new NumberPhoneAlreadyExistException();
        }
    }

    @Transactional
    @Override
    public void addStudentForGroup(Long studentId, Long groupId) {
        Group group = groupRepository
                .findById(groupId)
                .orElseThrow(GroupNotFoundException::new);
        Student student = studentRepository
                .findById(studentId)
                .orElseThrow(StudentNotFoundException::new);

        if(!checkRangeAge(group.getMinAge(), group.getMaxAge(), student.getPerson().getAge())) {
            throw new OutOfRangeAgeException();
        }

        if(!checkOutOfNumberOfStudents(group.getStudents().size(), group.getNumberOfStudents())) {
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
        Student student = studentRepository
                .findById(id)
                .orElseThrow(StudentNotFoundException::new);
        return studentMapper.studentToResponseStudentDto(student);
    }

    @Override
    public List<ResponseStudentDto> getStudents(Long offset, Long limit) {
        List<Student> students = studentRepository
                .selectAllWithOffsetAndLimit(offset, limit)
                .orElseThrow(StudentNotFoundException::new);
        return studentMapper.studentsToResponseStudentDtos(students);
    }

    @Override
    public void updateStudent(RequestStudentDto requestStudentDto, Long id) {
        Student student = studentRepository
                .findById(id)
                .orElseThrow(StudentNotFoundException::new);
        Student studentUpdated = studentMapper.requestStudentDtoToStudentForPatch(requestStudentDto, student);
        if(requestStudentDto.getPerson().getNumberPhone() != null) {
            numberPhoneAlreadyExist(student.getPerson().getNumberPhone());
        }
        studentRepository.save(studentUpdated);
    }

    @Transactional
    @Override
    public void excludeStudentForGroup(Long studentId) {
        studentRepository
                .findById(studentId)
                .orElseThrow(StudentNotFoundException::new);
        studentRepository.updateGroupIdValueOnNullForStudent(studentId);
    }

    @Override
    public void deleteStudent(Long id) {
        studentRepository
                .findById(id)
                .orElseThrow(StudentNotFoundException::new);
        studentRepository.deleteById(id);
    }
}
