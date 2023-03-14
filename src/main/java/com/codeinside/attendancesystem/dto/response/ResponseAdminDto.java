package com.codeinside.attendancesystem.dto.response;

public class ResponseAdminDto {

    private Long id;

    private ResponsePersonDto person;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ResponsePersonDto getPerson() {
        return person;
    }

    public void setPerson(ResponsePersonDto person) {
        this.person = person;
    }
}
