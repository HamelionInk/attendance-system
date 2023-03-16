package com.codeinside.attendancesystem.dto.request;

import lombok.Data;
import util.annotation.MinLessMaxAge;
import util.validator.marker.OnCreate;
import util.validator.marker.OnUpdate;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@MinLessMaxAge(groups = { OnCreate.class, OnUpdate.class })
@Data
public class RequestGroupDto {

    @NotBlank(message = "{groupName.notNull}", groups = OnCreate.class)
    private String groupName;

    @Min(value = 6, message = "{maxAge.min}", groups = { OnCreate.class, OnUpdate.class })
    @Max(value = 100, message = "{maxAge.max}", groups = { OnCreate.class, OnUpdate.class })
    @NotNull(message = "{maxAge.notNull}", groups = OnCreate.class)
    private Integer maxAge;

    @Min(value = 6, message = "{minAge.min}", groups = { OnCreate.class, OnUpdate.class })
    @Max(value = 100, message = "{minAge.max}", groups = { OnCreate.class, OnUpdate.class })
    @NotNull(message = "{minAge.notNull}", groups = OnCreate.class)
    private Integer minAge;

    @NotNull(message = "{numberOfStudents.notNull}", groups = OnCreate.class)
    private Integer numberOfStudents;

}
