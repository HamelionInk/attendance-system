package com.codeinside.attendancesystem.service.impl;

import com.codeinside.attendancesystem.dto.request.patch.RequestStudentPatchDto;
import com.codeinside.attendancesystem.dto.request.post.RequestStudentDto;
import com.codeinside.attendancesystem.dto.response.ResponseGroupDto;
import com.codeinside.attendancesystem.dto.response.ResponseStudentDto;
import com.codeinside.attendancesystem.entity.Student;
import com.codeinside.attendancesystem.enums.TypeUser;
import com.codeinside.attendancesystem.exception.OutOfNumberOfStudentsException;
import com.codeinside.attendancesystem.exception.OutOfRangeAgeException;
import com.codeinside.attendancesystem.exception.StudentNotFoundException;
import com.codeinside.attendancesystem.mapper.StudentMapper;
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
    private final GroupService groupService;

    @Override
    public void saveStudent(RequestStudentDto requestStudentDto) {
        Student student = studentMapper.requestStudentDtoToStudent(requestStudentDto);
        student.getPerson().setTypeUser(TypeUser.STUDENT);
        studentRepository.save(student);
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
        return studentMapper.StudentToResponseStudentDto(studentOptional.get());
    }

    @Override
    public List<ResponseStudentDto> getStudents(Long offset, Long limit) {
        List<Student> students = studentRepository.selectAllWithOffsetAndLimit(offset, limit);
        if(students.isEmpty()) {
            throw new StudentNotFoundException();
        }
        return studentMapper.StudentsToResponseStudentDtos(students);
    }

    @Override
    public void updateStudent(RequestStudentPatchDto requestStudentPatchDto, Long id) {
        Optional<Student> studentOptional = studentRepository.findById(id);
        if(studentOptional.isEmpty()) {
            throw new StudentNotFoundException();
        }
        Student student = studentMapper.requestStudentDtoToStudentForPatch(requestStudentPatchDto, studentOptional.get());
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
