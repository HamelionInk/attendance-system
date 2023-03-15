package com.codeinside.attendancesystem.dto.request.post;

import lombok.Data;

import javax.validation.Valid;

@Data
public class RequestCoachDto {

    @Valid
    private RequestPersonDto person;

}
