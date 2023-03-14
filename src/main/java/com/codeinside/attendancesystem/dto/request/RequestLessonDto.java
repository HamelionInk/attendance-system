package com.codeinside.attendancesystem.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import util.annotation.DateMatches;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@DateMatches
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public String getCoachName() {
        return coachName;
    }

    public void setCoachName(String coachName) {
        this.coachName = coachName;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getCoachId() {
        return coachId;
    }

    public void setCoachId(Long coachId) {
        this.coachId = coachId;
    }
}
