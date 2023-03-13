package com.codeinside.attendancesystem.service.impl;

import com.codeinside.attendancesystem.dto.request.RequestGroupDto;
import com.codeinside.attendancesystem.dto.response.ResponseGroupDto;
import com.codeinside.attendancesystem.entity.Group;
import com.codeinside.attendancesystem.entity.Student;
import com.codeinside.attendancesystem.exception.GroupNotFoundException;
import com.codeinside.attendancesystem.exception.OutOfNumberOfStudentsException;
import com.codeinside.attendancesystem.exception.OutOfRangeAgeException;
import com.codeinside.attendancesystem.exception.StudentNotFoundException;
import com.codeinside.attendancesystem.mapper.GroupMapper;
import com.codeinside.attendancesystem.repository.GroupRepository;
import com.codeinside.attendancesystem.repository.StudentRepository;
import com.codeinside.attendancesystem.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;
    private final GroupMapper groupMapper;

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository, StudentRepository studentRepository, GroupMapper groupMapper) {
        this.groupRepository = groupRepository;
        this.studentRepository = studentRepository;
        this.groupMapper = groupMapper;
    }

    @Override
    public void saveGroup(RequestGroupDto requestGroupDto) {
        Group group = groupMapper.requestGroupDtoToGroup(requestGroupDto);
        groupRepository.save(group);
    }

    @Override
    public ResponseGroupDto getGroup(Long groupId) {
        Optional<Group> group = groupRepository.findById(groupId);
        ResponseGroupDto responseGroupDto = new ResponseGroupDto();
        if(group.isPresent()) {
            responseGroupDto = groupMapper.groupToResponseGroupDto(group.get());
        }
        return responseGroupDto;
    }

    @Override
    public List<ResponseGroupDto> getGroups(Long offset, Long limit) {
        List<Group> groupList = groupRepository.selectAllWithOffsetAndLimit(offset, limit);
        if(groupList.isEmpty()) {
            throw new GroupNotFoundException();
        }
        return groupMapper.GroupListToResponseGroupListDto(groupList);
    }

    @Override
    public void deleteGroup(Long id) {
        getGroup(id);
        groupRepository.deleteById(id);
    }

    @Override
    public void updateGroup(RequestGroupDto requestGroupDto, Long id) {
        Optional<Group> groupOptional = groupRepository.findById(id);
        if(groupOptional.isEmpty()) {
            throw new GroupNotFoundException();
        }
        Group group = groupMapper.requestGroupDtoToGroupForPatch(requestGroupDto, groupOptional.get());
        groupRepository.save(group);
    }
}
