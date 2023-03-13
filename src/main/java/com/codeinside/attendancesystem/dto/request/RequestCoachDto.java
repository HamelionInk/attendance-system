package com.codeinside.attendancesystem.dto.request;

import com.codeinside.attendancesystem.entity.Person;

public class RequestCoachDto {

   private RequestPersonDto person;

    public RequestPersonDto getPerson() {
        return person;
    }

    public void setPerson(RequestPersonDto person) {
        this.person = person;
    }
}
