package com.codeinside.attendancesystem.mapper;

import com.codeinside.attendancesystem.dto.request.RequestStudentDto;
import com.codeinside.attendancesystem.dto.response.ResponseStudentDto;
import com.codeinside.attendancesystem.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface StudentMapper {

    Student requestStudentDtoToStudent(RequestStudentDto requestStudentDto);
    Student requestStudentDtoToStudentForPatch(RequestStudentDto requestStudentDto, @MappingTarget Student student);

    List<ResponseStudentDto> studentsToResponseStudentDtos(List<Student> students);
    ResponseStudentDto studentToResponseStudentDto(Student student);
}
