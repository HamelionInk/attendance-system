package com.codeinside.attendancesystem.service;

import com.codeinside.attendancesystem.dto.request.patch.RequestGroupPatchDto;
import com.codeinside.attendancesystem.dto.request.post.RequestGroupDto;
import com.codeinside.attendancesystem.dto.response.ResponseGroupDto;

import java.util.List;

public interface GroupService {

    void saveGroup(RequestGroupDto requestGroupDto);
    ResponseGroupDto getGroup(Long groupId);
    List<ResponseGroupDto> getGroups(Long offset, Long limit);
    void deleteGroup(Long id);
    void updateGroup(RequestGroupPatchDto requestGroupPatchDto, Long id);
}
