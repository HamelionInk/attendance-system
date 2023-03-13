package com.codeinside.attendancesystem.controller;

import com.codeinside.attendancesystem.dto.request.RequestGroupDto;
import com.codeinside.attendancesystem.dto.response.ResponseCoachDto;
import com.codeinside.attendancesystem.dto.response.ResponseGroupDto;
import com.codeinside.attendancesystem.service.GroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/groups")
@Validated
public class GroupController {

    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @Operation(summary = "Получить информацию о группе по ее 'id'")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное выполнение запроса. Группа найдена - OK",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseGroupDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Группы с таким 'id' не существует - Not Found",
                    content = @Content) })
    @GetMapping("/{id}")
    public ResponseEntity<ResponseGroupDto> getGroup(@PathVariable (name = "id") Long groupId) {
        ResponseGroupDto responseGroupDto = groupService.getGroup(groupId);
        return ResponseEntity.ok(responseGroupDto);
    }

    @Operation(summary = "Получить информацию о всех группах, для пагинации используются необязательные параметры offset и limit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное выполнение запроса. Список групп найден - OK",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseGroupDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Список групп пустой - Not Found",
                    content = @Content) })
    @Parameters(value = {
            @Parameter(name = "offset", description = "Сколько строк нужно пропустить"),
            @Parameter(name = "limit", description = "Ограничение на количество получаемых данных после offset") })
    @GetMapping("/all")
    public ResponseEntity<List<ResponseGroupDto>> getGroups(@RequestParam (name = "offset", required = false) Long offset,
                                                            @RequestParam (name = "limit", required = false) Long limit) {
        List<ResponseGroupDto> responseGroupDtos = groupService.getGroups(offset, limit);
        return ResponseEntity.ok(responseGroupDtos);
    }

    @Operation(summary = "Удалить группу по ее 'id'")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное выполнение запроса. Группа удалена - OK",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Группы с таким 'id' не существует - Not Found",
                    content = @Content) })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGroup(@PathVariable (name = "id") Long id) {
        groupService.deleteGroup(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Обновить информацию о группе по ее 'id'")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное выполнение запроса. Группа обновлена - OK",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RequestGroupDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Ошибка в запросе при обновлении - Bad Request",
                    content = @Content) })
    @PatchMapping("/{id}")
    // Обновить информацию о группе
    public ResponseEntity<?> updateGroup(@RequestBody @Valid RequestGroupDto requestGroupDto,
                                         @PathVariable (name = "id") Long id) {
        groupService.updateGroup(requestGroupDto, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Создать группу")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешное выполнение запроса. Группа создана - OK",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RequestGroupDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Ошибка в запросе при создании - Bad Request",
                    content = @Content) })
    @PostMapping()
    public ResponseEntity<?> saveGroup(@RequestBody @Valid RequestGroupDto requestGroupDto) {
        groupService.saveGroup(requestGroupDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}