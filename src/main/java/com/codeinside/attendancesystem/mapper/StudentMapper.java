package com.codeinside.attendancesystem.mapper;

import com.codeinside.attendancesystem.dto.request.patch.RequestStudentPatchDto;
import com.codeinside.attendancesystem.dto.request.post.RequestStudentDto;
import com.codeinside.attendancesystem.dto.response.ResponseStudentDto;
import com.codeinside.attendancesystem.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface StudentMapper {

    Student requestStudentDtoToStudent(RequestStudentDto requestStudentDto);
    Student requestStudentDtoToStudentForPatch(RequestStudentPatchDto requestStudentPatchDto, @MappingTarget Student student);

    List<ResponseStudentDto> StudentsToResponseStudentDtos(List<Student> students);
    ResponseStudentDto StudentToResponseStudentDto(Student student);
}
