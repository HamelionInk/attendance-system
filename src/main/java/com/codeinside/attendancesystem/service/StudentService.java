package com.codeinside.attendancesystem.service;

import com.codeinside.attendancesystem.dto.request.RequestStudentDto;
import com.codeinside.attendancesystem.dto.response.ResponseStudentDto;

import java.util.List;

public interface StudentService {

    void saveStudent(RequestStudentDto requestStudentDto);
    List<ResponseStudentDto> getAllStudents();
    ResponseStudentDto getStudent(Long id);
    void updateStudent(RequestStudentDto requestStudentDto, Long id);
    void deleteStudent(Long id);
}
