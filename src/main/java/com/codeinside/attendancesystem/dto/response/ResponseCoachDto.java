package com.codeinside.attendancesystem.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class ResponseCoachDto {

    private Long id;
    private ResponsePersonDto person;
    private List<ResponseLessonDto> lessons;

}
