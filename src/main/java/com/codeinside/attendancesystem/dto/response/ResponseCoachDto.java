package com.codeinside.attendancesystem.dto.response;

import java.util.List;

public class ResponseCoachDto {

    private ResponsePersonDto person;

    private List<ResponseLessonDto> lessons;

    public ResponsePersonDto getPerson() {
        return person;
    }

    public void setPerson(ResponsePersonDto person) {
        this.person = person;
    }

    public List<ResponseLessonDto> getLessons() {
        return lessons;
    }

    public void setLessons(List<ResponseLessonDto> lessons) {
        this.lessons = lessons;
    }
}
