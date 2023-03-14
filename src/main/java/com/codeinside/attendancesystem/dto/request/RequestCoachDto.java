package com.codeinside.attendancesystem.dto.request;

import com.codeinside.attendancesystem.entity.Person;

import javax.validation.Valid;

public class RequestCoachDto {

    @Valid
    private RequestPersonDto person;

    public RequestPersonDto getPerson() {
        return person;
    }

    public void setPerson(RequestPersonDto person) {
        this.person = person;
    }
}
