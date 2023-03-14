package com.codeinside.attendancesystem.dto.response;

import java.util.List;

public class ResponseStudentDto {

    private Long id;
    private ResponsePersonDto person;
    private Long groupId;
    private List<ResponseAttendanceDto> attendances;

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

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public List<ResponseAttendanceDto> getAttendances() {
        return attendances;
    }

    public void setAttendances(List<ResponseAttendanceDto> attendances) {
        this.attendances = attendances;
    }
}
