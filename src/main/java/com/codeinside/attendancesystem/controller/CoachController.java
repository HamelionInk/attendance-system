package com.codeinside.attendancesystem.controller;

import com.codeinside.attendancesystem.dto.request.RequestCoachDto;
import com.codeinside.attendancesystem.dto.response.ResponseCoachDto;
import com.codeinside.attendancesystem.service.CoachService;
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
@RequestMapping("/coaches")
@RequiredArgsConstructor
public class CoachController {

    private final CoachService coachService;

    @Operation(summary = "Создать аккаунт тренера для школы - Доступы: ADMIN")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное выполнение запроса. Тренер создан - OK",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RequestCoachDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Ошибка в запросе при создании - Bad Request",
                    content = @Content) })
    @PostMapping
    public ResponseEntity<?> saveCoach(@RequestBody @Validated(value = OnCreate.class) RequestCoachDto requestCoachDto) {
        coachService.saveCoach(requestCoachDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Получить информацию о тренере по его 'id' - Доступы: ADMIN, TRAINER, STUDENT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное выполнение запроса. Тренер найден - OK",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseCoachDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Тренер с таким 'id' не найден - Not Found",
                    content = @Content) })
    @GetMapping("/{id}")
    public ResponseEntity<ResponseCoachDto> getCoach(@PathVariable (name = "id") Long id) {
        ResponseCoachDto responseCoachDto = coachService.getCoach(id);
        return ResponseEntity.ok(responseCoachDto);
    }

    @Operation(summary = "Получить список всех тренеров, для пагинации используются необязательные параметры offset и limit - Доступы: ADMIN, TRAINER, STUDENT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное выполнение запроса. Список тренеров найден - OK",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseCoachDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Список тренеров пустой - Not Found",
                    content = @Content) })
    @Parameters(value = {
            @Parameter(name = "offset", description = "Сколько строк нужно пропустить"),
            @Parameter(name = "limit", description = "Ограничение на количество получаемых данных после offset") })
    @GetMapping("/all")
    public ResponseEntity<List<ResponseCoachDto>> getCoaches(@RequestParam (name = "offset", required = false) Long offset,
                                                             @RequestParam (name = "limit", required = false) Long limit) {
        List<ResponseCoachDto> responseTrainersDto = coachService.getCoaches(offset, limit);
        return ResponseEntity.ok(responseTrainersDto);
    }

    @Operation(summary = "Обновить информацию о тренере по его 'id' - Доступы: ADMIN")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное выполнение запроса. Информация обновлена - OK",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RequestCoachDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Ошибка в запросе при обновлении - Bad Request",
                    content = @Content) })
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateCoach(@RequestBody @Validated(value = OnUpdate.class) RequestCoachDto requestCoachDto,
                                         @PathVariable (name = "id") Long id) {
        coachService.updateCoach(requestCoachDto, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Удалить аккаунт тренера из школы по его 'id' - Доступы: ADMIN")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное выполнение запроса. Тренер удален - OK",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Тренера с таким 'id' не существует - Not Found",
                    content = @Content) })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCoach(@PathVariable (name = "id") Long id) {
        coachService.deleteCoach(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
