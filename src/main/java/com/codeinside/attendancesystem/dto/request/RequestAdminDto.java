package com.codeinside.attendancesystem.dto.request;

import lombok.Data;

import javax.validation.Valid;

@Data
public class RequestAdminDto {

    @Valid
    private RequestPersonDto person;

}
