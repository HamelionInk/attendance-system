package com.codeinside.attendancesystem.dto.response;

public class ResponseStudentDto {

    private ResponsePersonDto person;
    private Long groupId;

    public ResponsePersonDto getPerson() {
        return person;
    }

    public void setPerson(ResponsePersonDto person) {
        this.person = person;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
}
