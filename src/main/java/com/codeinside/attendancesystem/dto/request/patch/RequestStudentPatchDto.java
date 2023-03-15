package com.codeinside.attendancesystem.dto.request.patch;

import lombok.Data;

import javax.validation.Valid;

@Data
public class RequestStudentPatchDto {

    @Valid
    private RequestPersonPatchDto person;

}
