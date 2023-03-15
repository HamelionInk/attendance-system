package com.codeinside.attendancesystem.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class ResponseStudentDto {

    private Long id;
    private ResponsePersonDto person;
    private Long groupId;
    private List<ResponseAttendanceDto> attendances;

}
