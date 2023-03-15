package com.codeinside.attendancesystem.dto.request.patch;

import lombok.Data;
import util.annotation.MinLessMaxAge;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@MinLessMaxAge
@Data
public class RequestGroupPatchDto {

    private String groupName;

    @Min(value = 6, message = "{maxAge.min}")
    @Max(value = 100, message = "{maxAge.max}")
    private Integer maxAge;

    @Min(value = 6, message = "{minAge.min}")
    @Max(value = 100, message = "{minAge.max}")
    private Integer minAge;

    private Integer numberOfStudents;

}
