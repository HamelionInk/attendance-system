package com.codeinside.attendancesystem.service;

import com.codeinside.attendancesystem.dto.request.patch.RequestStudentPatchDto;
import com.codeinside.attendancesystem.dto.request.post.RequestStudentDto;
import com.codeinside.attendancesystem.dto.response.ResponseStudentDto;

import java.util.List;

public interface StudentService {

    void saveStudent(RequestStudentDto requestStudentDto);
    void addStudentForGroup(Long studentId, Long groupId);
    List<ResponseStudentDto> getStudents(Long offset, Long limit);
    ResponseStudentDto getStudent(Long id);
    void updateStudent(RequestStudentPatchDto requestStudentPatchDto, Long id);
    void deleteStudent(Long id);
    void excludeStudentForGroup(Long studentId);
}
