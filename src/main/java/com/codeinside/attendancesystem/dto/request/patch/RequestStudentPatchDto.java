package com.codeinside.attendancesystem.dto.request.patch;

import javax.validation.Valid;

public class RequestStudentPatchDto {

    @Valid
    private RequestPersonPatchDto person;

    public RequestPersonPatchDto getPerson() {
        return person;
    }

    public void setPerson(RequestPersonPatchDto person) {
        this.person = person;
    }
}
