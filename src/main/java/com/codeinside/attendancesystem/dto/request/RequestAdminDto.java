package com.codeinside.attendancesystem.dto.request;

import javax.validation.Valid;

public class RequestAdminDto {

    @Valid
    private RequestPersonDto person;

    public RequestPersonDto getPerson() {
        return person;
    }

    public void setPerson(RequestPersonDto person) {
        this.person = person;
    }
}
