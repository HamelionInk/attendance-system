package com.codeinside.attendancesystem.dto.request.post;

import javax.validation.Valid;

public class RequestStudentDto {

    @Valid
    private RequestPersonDto person;

    public RequestPersonDto getPerson() {
        return person;
    }

    public void setPerson(RequestPersonDto person) {
        this.person = person;
    }

}
