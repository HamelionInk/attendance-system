package com.codeinside.attendancesystem.mapper;

import com.codeinside.attendancesystem.dto.request.RequestGroupDto;
import com.codeinside.attendancesystem.dto.response.ResponseGroupDto;
import com.codeinside.attendancesystem.entity.Group;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface GroupMapper {

    ResponseGroupDto groupToResponseGroupDto(Group group);
    List<ResponseGroupDto> GroupListToResponseGroupListDto(List<Group> groupList);
    Group requestGroupDtoToGroup(RequestGroupDto requestGroupDto);
    Group requestGroupDtoToGroupForPatch(RequestGroupDto requestGroupDto, @MappingTarget Group group);

}
