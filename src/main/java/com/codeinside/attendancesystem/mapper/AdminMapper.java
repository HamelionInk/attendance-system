package com.codeinside.attendancesystem.mapper;

import com.codeinside.attendancesystem.dto.request.patch.RequestAdminPatchDto;
import com.codeinside.attendancesystem.dto.request.post.RequestAdminDto;
import com.codeinside.attendancesystem.dto.response.ResponseAdminDto;
import com.codeinside.attendancesystem.entity.Admin;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AdminMapper {

    Admin requestAdminDtoToAdmin(RequestAdminDto requestAdminDto);
    Admin requestAdminDtoToAdminForPatch(RequestAdminPatchDto requestAdminPatchDto, @MappingTarget Admin admin);
    ResponseAdminDto adminToResponseAdminDto(Admin admin);
    List<ResponseAdminDto> adminsToResponseAdminDtos(List<Admin> admins);
}
