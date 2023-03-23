package com.codeinside.attendancesystem.service.impl;

import com.codeinside.attendancesystem.dto.request.RequestGroupDto;
import com.codeinside.attendancesystem.dto.response.ResponseGroupDto;
import com.codeinside.attendancesystem.entity.Group;
import com.codeinside.attendancesystem.exception.GroupNotFoundException;
import com.codeinside.attendancesystem.mapper.GroupMapper;
import com.codeinside.attendancesystem.repository.GroupRepository;
import com.codeinside.attendancesystem.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;

    @Override
    public void saveGroup(RequestGroupDto requestGroupDto) {
        Group group = groupMapper.requestGroupDtoToGroup(requestGroupDto);
        groupRepository.save(group);
    }

    @Override
    public ResponseGroupDto getGroup(Long groupId) {
        Group group = groupRepository
                .findById(groupId)
                .orElseThrow(GroupNotFoundException::new);
        return groupMapper.groupToResponseGroupDto(group);
    }

    @Override
    public List<ResponseGroupDto> getGroups(Long offset, Long limit) {
        List<Group> groupList = groupRepository
                .selectAllWithOffsetAndLimit(offset, limit)
                .orElseThrow(GroupNotFoundException::new);
        return groupMapper.GroupsToResponseGroupDtos(groupList);
    }

    @Override
    public void updateGroup(RequestGroupDto requestGroupDto, Long id) {
        Group group = groupRepository
                .findById(id)
                .orElseThrow(GroupNotFoundException::new);
        Group groupUpdated = groupMapper.requestGroupDtoToGroupForPatch(requestGroupDto, group);
        groupRepository.save(groupUpdated);
    }

    @Override
    public void deleteGroup(Long id) {
        groupRepository
                .findById(id)
                .orElseThrow(GroupNotFoundException::new);
        groupRepository.deleteById(id);
    }
}
