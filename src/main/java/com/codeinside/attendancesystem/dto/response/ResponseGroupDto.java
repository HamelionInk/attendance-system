package com.codeinside.attendancesystem.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class ResponseGroupDto {

    private Long id;
    private String groupName;
    private Integer maxAge;
    private Integer minAge;
    private Integer numberOfStudents;
    private List<ResponseStudentDto> students;

}
