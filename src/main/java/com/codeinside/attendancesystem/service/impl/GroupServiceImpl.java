package com.codeinside.attendancesystem.service.impl;

import com.codeinside.attendancesystem.dto.request.patch.RequestGroupPatchDto;
import com.codeinside.attendancesystem.dto.request.post.RequestGroupDto;
import com.codeinside.attendancesystem.dto.response.ResponseGroupDto;
import com.codeinside.attendancesystem.entity.Group;
import com.codeinside.attendancesystem.exception.GroupNotFoundException;
import com.codeinside.attendancesystem.mapper.GroupMapper;
import com.codeinside.attendancesystem.repository.GroupRepository;
import com.codeinside.attendancesystem.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository, GroupMapper groupMapper) {
        this.groupRepository = groupRepository;
        this.groupMapper = groupMapper;
    }

    @Override
    public void saveGroup(RequestGroupDto requestGroupDto) {
        Group group = groupMapper.requestGroupDtoToGroup(requestGroupDto);
        groupRepository.save(group);
    }

    @Override
    public ResponseGroupDto getGroup(Long groupId) {
        Optional<Group> groupOptional = groupRepository.findById(groupId);
        if(groupOptional.isEmpty()) {
            throw new GroupNotFoundException();
        }
        return groupMapper.groupToResponseGroupDto(groupOptional.get());
    }

    @Override
    public List<ResponseGroupDto> getGroups(Long offset, Long limit) {
        List<Group> groupList = groupRepository.selectAllWithOffsetAndLimit(offset, limit);
        if(groupList.isEmpty()) {
            throw new GroupNotFoundException();
        }
        return groupMapper.GroupsToResponseGroupDtos(groupList);
    }

    @Override
    public void updateGroup(RequestGroupPatchDto requestGroupPatchDto, Long id) {
        Optional<Group> groupOptional = groupRepository.findById(id);
        if(groupOptional.isEmpty()) {
            throw new GroupNotFoundException();
        }
        Group group = groupMapper.requestGroupDtoToGroupForPatch(requestGroupPatchDto, groupOptional.get());
        groupRepository.save(group);
    }

    @Override
    public void deleteGroup(Long id) {
        Optional<Group> groupOptional = groupRepository.findById(id);
        if(groupOptional.isEmpty()) {
            throw new GroupNotFoundException();
        }
        groupRepository.deleteById(id);
    }
}
