package com.codeinside.attendancesystem.dto.response;

public class ResponseAttendanceDto {

    private String studentName;
    private Boolean attendance;

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Boolean getAttendance() {
        return attendance;
    }

    public void setAttendance(Boolean attendance) {
        this.attendance = attendance;
    }
}
