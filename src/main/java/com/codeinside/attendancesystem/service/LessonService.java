package com.codeinside.attendancesystem.service;

import com.codeinside.attendancesystem.dto.request.RequestLessonDto;
import com.codeinside.attendancesystem.dto.response.ResponseLessonDto;

import java.util.List;

public interface LessonService {

    void saveLesson(RequestLessonDto requestLessonDto);
    ResponseLessonDto getLesson(Long id);
    List<ResponseLessonDto> getLessons(Long offset, Long limit);
    List<ResponseLessonDto> getLessonsForStudent(Long studentId);
    void deleteLesson(Long id);
    void updateLesson(RequestLessonDto requestLessonDto, Long id);
    void updateAttendanceLessonForStudent(Long lessonId, Long studentId, Boolean attendance);
}
