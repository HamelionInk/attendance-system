package com.codeinside.attendancesystem.controller;

import com.codeinside.attendancesystem.dto.request.RequestLessonDto;
import com.codeinside.attendancesystem.dto.response.ResponseLessonDto;
import com.codeinside.attendancesystem.service.LessonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import util.validator.marker.OnCreate;
import util.validator.marker.OnUpdate;

import java.util.List;

@RestController
@RequestMapping("/lessons")
@RequiredArgsConstructor
public class LessonController {

    private final LessonService lessonService;

    @Operation(summary = "Создать занятие - Доступы: ADMIN")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное выполнение запроса. Занятие создано - OK",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RequestLessonDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Ошибка в запросе при создании или даты занятий пересекаются" +
                    " - Bad Request",
                    content = @Content) })
    @PostMapping
    public ResponseEntity<?> saveLesson(@RequestBody @Validated(OnCreate.class) RequestLessonDto requestLessonDto) {
        lessonService.saveLesson(requestLessonDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Получить информацию о занятии по его 'id' - Доступы: ADMIN, TRAINER, STUDENT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное выполнение запроса. Занятие найдено - OK",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseLessonDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Занятия с таким 'id' не найдено - Not Found",
                    content = @Content) })
    @GetMapping("/{id}")
    public ResponseEntity<ResponseLessonDto> getLesson(@PathVariable (name = "id") Long id) {
        ResponseLessonDto responseLessonDto = lessonService.getLesson(id);
        return ResponseEntity.ok(responseLessonDto);
    }

    @Operation(summary = "Получить список занятий в школе, для пагинации используются необязательные параметры offset и limit - Доступы: ADMIN, TRAINER, STUDENT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное выполнение запроса. Список занятий найден - OK",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseLessonDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Список занятий пустой - Not Found",
                    content = @Content) })
    @Parameters(value = {
            @Parameter(name = "offset", description = "Сколько строк нужно пропустить"),
            @Parameter(name = "limit", description = "Ограничение на количество получаемых данных после offset") })
    @GetMapping("/all")
    public ResponseEntity<List<ResponseLessonDto>> getLessons(@RequestParam(name = "offset", required = false) Long offset,
                                                              @RequestParam (name = "limit", required = false) Long limit) {
        List<ResponseLessonDto> responseLessonDtoList = lessonService.getLessons(offset, limit);
        return ResponseEntity.ok(responseLessonDtoList);
    }

    @Operation(summary = "Получить информацию о занятиях студента по его 'id' - Доступы: ADMIN, TRAINER, STUDENT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное выполнение запроса. Занятия найдены - OK",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseLessonDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Студента с таким 'id' не найдено - Not Found",
                    content = @Content) })
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<ResponseLessonDto>> getLessonsForStudents(@PathVariable (name = "studentId") Long id) {
        List<ResponseLessonDto> responseLessonDtoList = lessonService.getLessonsForStudent(id);
        return ResponseEntity.ok(responseLessonDtoList);
    }

    @Operation(summary = "Обновить присутствие студента по его 'id' и по 'id' занятия - Доступы: ADMIN, TRAINER, STUDENT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное выполнение запроса. Присутсвие обновлено - OK",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Занятия или студент с таким 'id' не найдено - Not Found",
                    content = @Content) })
    @Parameters(
            @Parameter(name = "attendance", description = "true - студент присутствует, false - студент отсутствует, " +
                    "null - студент прогулял"))
    @PatchMapping("/{lessonId}/student/{studentId}")
    public ResponseEntity<?> updateAttendanceLessonForStudent(@PathVariable (name = "lessonId") Long lessonId,
                                                              @PathVariable (name = "studentId") Long studentId,
                                                              @RequestParam (name = "attendance", required = false) Boolean attendance) {
        lessonService.updateAttendanceLessonForStudent(lessonId, studentId, attendance);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Обновить информацию о занятии по его 'id' - Доступы: ADMIN")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное выполнение запроса. Занятие обновлено - OK",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RequestLessonDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Ошибка в запросе при обновление - Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Занятия с таким 'id' не найдено - Not Found",
                    content = @Content) })
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateLesson(@PathVariable ( name = "id") Long id,
                                          @RequestBody @Validated(value = OnUpdate.class) RequestLessonDto requestLessonDto) {
        lessonService.updateLesson(requestLessonDto, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Удалить занятие по его 'id' - Доступы: ADMIN")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное выполнение запроса. Занятие удалено - OK",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Занятия с таким 'id' не найдено - Not Found",
                    content = @Content) })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLesson(@PathVariable (name = "id") Long id) {
        lessonService.deleteLesson(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
