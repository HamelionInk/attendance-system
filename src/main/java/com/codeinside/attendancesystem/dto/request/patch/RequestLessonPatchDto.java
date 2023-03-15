package com.codeinside.attendancesystem.dto.request.patch;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class RequestLessonPatchDto {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;
    private String lessonName;
    private String coachName;
    private Long groupId;
    private Long coachId;

}
