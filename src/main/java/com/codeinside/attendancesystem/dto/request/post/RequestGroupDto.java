package com.codeinside.attendancesystem.dto.request.post;

import util.annotation.MinLessMaxAge;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@MinLessMaxAge
public class RequestGroupDto {

    @NotBlank(message = "{groupName.notNull}")
    private String groupName;

    @Min(value = 6, message = "{maxAge.min}")
    @Max(value = 100, message = "{maxAge.max}")
    @NotNull(message = "{maxAge.notNull}")
    private Integer maxAge;

    @Min(value = 6, message = "{minAge.min}")
    @Max(value = 100, message = "{minAge.max}")
    @NotNull(message = "{minAge.notNull}")
    private Integer minAge;

    @NotNull(message = "{numberOfStudents.notNull}")
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
