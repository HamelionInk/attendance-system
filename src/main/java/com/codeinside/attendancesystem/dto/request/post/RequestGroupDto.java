package com.codeinside.attendancesystem.dto.request.post;

import lombok.Data;
import util.annotation.MinLessMaxAge;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@MinLessMaxAge
@Data
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

}
