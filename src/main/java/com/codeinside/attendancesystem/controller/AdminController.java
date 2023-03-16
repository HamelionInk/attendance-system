package com.codeinside.attendancesystem.controller;

import com.codeinside.attendancesystem.dto.request.RequestAdminDto;
import com.codeinside.attendancesystem.dto.response.ResponseAdminDto;
import com.codeinside.attendancesystem.service.AdminService;
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
@RequestMapping("/admins")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @Operation(summary = "Создать аккаунт администратора сервиса - Доступы: ADMIN")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное выполнение запроса. Администратор создан - OK",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RequestAdminDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Ошибка в запросе при создании - Bad Request",
                    content = @Content) })
    @PostMapping
    public ResponseEntity<?> saveAdmin(@RequestBody @Validated(value = OnCreate.class) RequestAdminDto requestAdminDto) {
        adminService.saveAdmin(requestAdminDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Получить информацию об администраторе по его 'id' - Доступы: ADMIN")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное выполнение запроса. Администратор найден - OK",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseAdminDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Администратор с таким 'id' не найден - Not Found",
                    content = @Content) })
    @GetMapping("/{id}")
    public ResponseEntity<ResponseAdminDto> getAdmin(@PathVariable (name = "id") Long id) {
        ResponseAdminDto responseAdminDto = adminService.getAdmin(id);
        return ResponseEntity.ok(responseAdminDto);
    }

    @Operation(summary = "Получить список всех администраторов, " +
            "для пагинации используются необяательные параметры offset и limit - Доступы: ADMIN")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное выполнение запроса. Список администраторов найден - OK",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseAdminDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Список администраторов пустой - Not Found",
                    content = @Content) })
    @Parameters( value = {
            @Parameter(name = "offset", description = "Сколько строк нужно пропустить"),
            @Parameter(name = "limit", description = "Ограничение на количество получаемых данных после offset") })
    @GetMapping("/all")
    public ResponseEntity<List<ResponseAdminDto>> getAdmins(@RequestParam (name = "offset", required = false) Long offset,
                          @RequestParam (name = "limit", required = false) Long limit) {
        List<ResponseAdminDto> responseAdminDtos = adminService.getAdmins(offset, limit);
        return ResponseEntity.ok(responseAdminDtos);
    }

    @Operation(summary = "Обновить информацию об администраторе по его 'id' - Доступы: ADMIN")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное выполнение запроса. Администратор обновлен - OK",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseAdminDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Администратора с таким 'id' не найдено - Not Found",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Ошибка в запросе при создании - Bad Request",
                    content = @Content)})
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateAdmin(@PathVariable (name = "id") Long id,
                            @RequestBody @Validated(OnUpdate.class) RequestAdminDto requestAdminDto) {
        adminService.updateAdmin(id, requestAdminDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Удалить информацию об администраторе по его 'id' - Доступы: ADMIN")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное выполнение запроса. Администратор удален - OK",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseAdminDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Администратора с таким 'id' не найдено - Not Found",
                    content = @Content) })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAdmin(@PathVariable (name = "id") Long id) {
        adminService.deleteAdmin(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
