package com.codeinside.attendancesystem.dto.request.post;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import util.annotation.DateMatches;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@DateMatches
@Data
public class RequestLessonDto {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "{startDate.notNull}")
    private Date startDate;
    @NotBlank(message = "{lessonName.notBlank}")
    private String lessonName;
    @NotBlank(message = "{coachName.notBlank}")
    private String coachName;
    @NotNull(message = "{groupId.notNull}")
    private Long groupId;
    @NotNull(message = "{coachId.notNull}")
    private Long coachId;

}
