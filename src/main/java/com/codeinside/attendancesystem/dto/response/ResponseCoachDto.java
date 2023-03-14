package com.codeinside.attendancesystem.dto.response;

import java.util.List;

public class ResponseCoachDto {

    private Long id;

    private ResponsePersonDto person;

    private List<ResponseLessonDto> lessons;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
