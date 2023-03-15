package com.codeinside.attendancesystem.dto.request.patch;

import lombok.Data;

import javax.validation.Valid;

@Data
public class RequestCoachPatchDto {

    @Valid
    private RequestPersonPatchDto person;

}
