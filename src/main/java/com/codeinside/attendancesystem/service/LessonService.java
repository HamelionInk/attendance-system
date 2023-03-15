package com.codeinside.attendancesystem.service;

import com.codeinside.attendancesystem.dto.request.patch.RequestLessonPatchDto;
import com.codeinside.attendancesystem.dto.request.post.RequestLessonDto;
import com.codeinside.attendancesystem.dto.response.ResponseLessonDto;

import java.util.List;

public interface LessonService {

    void saveLesson(RequestLessonDto requestLessonDto);
    ResponseLessonDto getLesson(Long id);
    List<ResponseLessonDto> getLessons(Long offset, Long limit);
    List<ResponseLessonDto> getLessonsForStudent(Long studentId);
    void deleteLesson(Long id);
    void updateLesson(RequestLessonPatchDto requestLessonPatchDto, Long id);
    void updateAttendanceLessonForStudent(Long lessonId, Long studentId, Boolean attendance);
}
