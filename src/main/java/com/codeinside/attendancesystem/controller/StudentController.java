package com.codeinside.attendancesystem.controller;

import com.codeinside.attendancesystem.dto.request.RequestStudentDto;
import com.codeinside.attendancesystem.dto.response.ResponseStudentDto;
import com.codeinside.attendancesystem.service.StudentService;
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
import com.codeinside.attendancesystem.util.validator.marker.OnCreate;
import com.codeinside.attendancesystem.util.validator.marker.OnUpdate;

import java.util.List;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @Operation(summary = "Создать аккаунт студента для школы - Доступы: ADMIN")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное выполнение запроса. Студент создан создано - OK",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RequestStudentDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Ошибка в запросе при создании - Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Пользователь с таким номером телефона уже существует - CONFLICT",
                    content = @Content)})
    @PostMapping
    public ResponseEntity<?> saveStudent(@RequestBody @Validated(value = OnCreate.class) RequestStudentDto requestStudentDto) {
        studentService.saveStudent(requestStudentDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Добавить студента по его 'id' к группе по ее 'id' - Доступы: ADMIN, TRAINER")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное выполнение запроса. Студент добавлен в группу - OK",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Группа или студент с таким 'id' не найдена - Not Found",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Группа уже содержит максимальное количество участников" +
                    " или возраст студента слишком большой для группы - Bad Request",
                    content = @Content) })
    @PostMapping("/{student_id}/group/{group_id}")
    public ResponseEntity<?> addStudentForGroup(@PathVariable (value = "student_id") Long studentId,
                                                @PathVariable (value = "group_id") Long groupId) {
        studentService.addStudentForGroup(studentId, groupId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Получить информацию о студенте по его 'id'- Доступы: ADMIN, TRAINER, STUDENT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное выполнение запроса. Студент найден - OK",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseStudentDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Студента с таким 'id' не найдено - Not Found",
                    content = @Content) })
    @GetMapping("/{id}")
    public ResponseEntity<ResponseStudentDto> getStudent(@PathVariable (name = "id") Long id) {
        ResponseStudentDto responseStudentDto = studentService.getStudent(id);
        return new ResponseEntity<>(responseStudentDto, HttpStatus.OK);
    }

    @Operation(summary = "Получить список всех студентов, для пагинации используются необязательные параметры offset и limit - Доступы: ADMIN, TRAINER, STUDENT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное выполнение запроса. Список студентов найден - OK",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseStudentDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Список студентов пустой - Not Found",
                    content = @Content) })
    @Parameters(value = {
            @Parameter(name = "offset", description = "Сколько строк нужно пропустить"),
            @Parameter(name = "limit", description = "Ограничение на количество получаемых данных после offset") })
    @GetMapping
    public ResponseEntity<List<ResponseStudentDto>> getStudents(@RequestParam(name = "offset", required = false) Long offset,
                                                                @RequestParam (name = "limit", required = false) Long limit) {
        List<ResponseStudentDto> responseStudentsDto = studentService.getStudents(offset, limit);
        return new ResponseEntity<>(responseStudentsDto, HttpStatus.OK);
    }

    @Operation(summary = "Обновить информацию о студенте по его 'id' - Доступы: ADMIN")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное выполнение запроса. Студент обновлен - OK",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseStudentDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Студента с таким 'id' не найдено - Not Found",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Ошибка в запросе при обновлении - Bad Request",
                    content = @Content) })
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateStudent(@RequestBody @Validated(value = OnUpdate.class) RequestStudentDto requestStudentDto,
                              @PathVariable (name = "id") Long id) {
        studentService.updateStudent(requestStudentDto, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Исключить студента из группы по 'id' студента - Доступы: ADMIN, TRAINER")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное выполнение запроса. Группа удалена - OK",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Группа с таким 'id' не найдена - Not Found",
                    content = @Content) })
    @PatchMapping("/student/{id}")
    public ResponseEntity<?> excludeStudentForGroup(@PathVariable (name = "id") Long id) {
        studentService.excludeStudentForGroup(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Удалить студента по его 'id' - Доступы: ADMIN")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное выполнение запроса. Студент удален - OK",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Студента с таким 'id' не найдено - Not Found",
                    content = @Content) })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable (name = "id") Long id) {
        studentService.deleteStudent(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
