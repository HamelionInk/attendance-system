package com.codeinside.attendancesystem.dto.request.patch;

import lombok.Data;

import javax.validation.Valid;

@Data
public class RequestAdminPatchDto {

    @Valid
    private RequestPersonPatchDto person;

}
