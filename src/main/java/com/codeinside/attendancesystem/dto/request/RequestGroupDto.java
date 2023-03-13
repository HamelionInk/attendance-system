package com.codeinside.attendancesystem.dto.request;

import util.annotation.MinMaxAgeValid;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@MinMaxAgeValid
public class RequestGroupDto {

    private String groupName;

    @Min(value = 6)
    @Max(value = 100)
    private Integer maxAge;

    @Min(value = 6)
    @Max(value = 100)
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
