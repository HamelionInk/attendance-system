package com.codeinside.attendancesystem.dto.request;

public class RequestStudentDto {

    private RequestPersonDto person;
    private Long groupId;

    public RequestPersonDto getPerson() {
        return person;
    }

    public void setPerson(RequestPersonDto person) {
        this.person = person;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
}
