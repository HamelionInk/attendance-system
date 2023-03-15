package com.codeinside.attendancesystem.dto.request.post;

import lombok.Data;

import javax.validation.Valid;

@Data
public class RequestStudentDto {

    @Valid
    private RequestPersonDto person;

}
