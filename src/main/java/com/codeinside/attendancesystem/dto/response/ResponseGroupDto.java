package com.codeinside.attendancesystem.dto.response;

import java.util.List;

public class ResponseGroupDto {

    private Long id;
    private String groupName;
    private Integer maxAge;
    private Integer minAge;
    private Integer numberOfStudents;
    private List<ResponseStudentDto> students;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
    }

    public Integer getMinAge() {
        return minAge;
    }

    public void setMinAge(Integer minAge) {
        this.minAge = minAge;
    }

    public Integer getNumberOfStudents() {
        return numberOfStudents;
    }

    public void setNumberOfStudents(Integer numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }

    public List<ResponseStudentDto> getStudents() {
        return students;
    }

    public void setStudents(List<ResponseStudentDto> students) {
        this.students = students;
    }
}
