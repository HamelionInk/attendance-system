package com.codeinside.attendancesystem.mapper;

import com.codeinside.attendancesystem.dto.request.RequestLessonDto;
import com.codeinside.attendancesystem.dto.response.ResponseLessonDto;
import com.codeinside.attendancesystem.entity.Lesson;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface LessonMapper {

    ResponseLessonDto LessonToResponseLessonDto(Lesson lesson);
    Lesson requestLessonDtoToLesson(RequestLessonDto requestLessonDto);
    Lesson requestLessonDtoToLessonForPatch(RequestLessonDto requestLessonDto, @MappingTarget Lesson lesson);
    List<ResponseLessonDto> lessonListToResponseLessonListDto(List<Lesson> lessonList);
}
