package com.codeinside.attendancesystem.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import util.validator.marker.OnCreate;
import util.validator.marker.OnUpdate;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class RequestLessonDto {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @FutureOrPresent(groups = { OnCreate.class, OnUpdate.class })
    @NotNull(message = "{startDate.notNull}", groups = OnCreate.class)
    private Date startDate;
    @NotBlank(message = "{lessonName.notBlank}", groups = OnCreate.class)
    private String lessonName;
    @NotBlank(message = "{coachName.notBlank}", groups = OnCreate.class)
    private String coachName;
    @NotNull(message = "{groupId.notNull}", groups = OnCreate.class)
    private Long groupId;
    @NotNull(message = "{coachId.notNull}", groups = OnCreate.class)
    private Long coachId;

}
