package com.codeinside.attendancesystem.dto.request.patch;

import util.annotation.MinLessMaxAge;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@MinLessMaxAge

public class RequestGroupPatchDto {

    private String groupName;

    @Min(value = 6, message = "{maxAge.min}")
    @Max(value = 100, message = "{maxAge.max}")
    private Integer maxAge;

    @Min(value = 6, message = "{minAge.min}")
    @Max(value = 100, message = "{minAge.max}")
    private Integer minAge;

    private Integer numberOfStudents;

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
}
