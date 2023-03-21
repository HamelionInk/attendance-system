package com.codeinside.attendancesystem.unit.service;

import com.codeinside.attendancesystem.dto.request.RequestGroupDto;
import com.codeinside.attendancesystem.dto.response.ResponseGroupDto;
import com.codeinside.attendancesystem.entity.Group;
import com.codeinside.attendancesystem.exception.GroupNotFoundException;
import com.codeinside.attendancesystem.mapper.GroupMapper;
import com.codeinside.attendancesystem.repository.GroupRepository;
import com.codeinside.attendancesystem.service.impl.GroupServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GroupServiceTest {

    @Mock
    private GroupRepository groupRepository;
    @Mock
    private GroupMapper groupMapper;
    @InjectMocks
    private GroupServiceImpl groupService;

    private Group group;

    @BeforeEach
    public void setUp() {
        group = new Group();
        group.setGroupName("TestName");
    }

    @AfterEach
    public void tearDown() {

    }

    @Test
    public void saveGroup() {
        when(groupMapper.requestGroupDtoToGroup(any())).thenReturn(group);

        groupService.saveGroup(any());

        verify(groupRepository, times(1)).save(any());
    }


    @Test
    public void getGroup() {
        when(groupRepository.findById(any())).thenReturn(Optional.ofNullable(group));
        ResponseGroupDto responseGroupDto = new ResponseGroupDto();
        responseGroupDto.setGroupName(group.getGroupName());
        when(groupMapper.groupToResponseGroupDto(group)).thenReturn(responseGroupDto);

        ResponseGroupDto groupActual = groupService.getGroup(any());

        Assertions.assertEquals(group.getGroupName(), groupActual.getGroupName());

    }

    @Test
    public void getGroupExpectedException() {
        when(groupRepository.findById(any())).thenReturn(Optional.empty());

        Assertions.assertThrows(GroupNotFoundException.class, () -> groupService.getGroup(any()));
    }

    @Test
    public void getGroups() {
        List<Group> groups = new ArrayList<>();
        groups.add(group);
        when(groupRepository.selectAllWithOffsetAndLimit(any(), any())).thenReturn(groups);
        List<ResponseGroupDto> responseGroupDtos = new ArrayList<>();
        ResponseGroupDto responseGroupDto = new ResponseGroupDto();
        responseGroupDto.setGroupName(group.getGroupName());
        responseGroupDtos.add(responseGroupDto);
        when(groupMapper.GroupsToResponseGroupDtos(groups)).thenReturn(responseGroupDtos);

        List<ResponseGroupDto> groupActual = groupService.getGroups(any(),any());

        Assertions.assertEquals(groupActual.size(), groups.size());
    }

    @Test
    public void getGroupsExpectedException() {
        when(groupRepository.selectAllWithOffsetAndLimit(any(), any())).thenReturn(new ArrayList<>());

        Assertions.assertThrows(GroupNotFoundException.class, () -> groupService.getGroups(any(),any()));
    }

    @Test
    public void updateGroup() {
        when(groupRepository.findById(any())).thenReturn(Optional.ofNullable(group));
        Group groupDto = new Group();
        groupDto.setGroupName("New");
        when(groupMapper.requestGroupDtoToGroupForPatch(any(), any())).thenReturn(groupDto);

        groupService.updateGroup(new RequestGroupDto(), any());

        verify(groupRepository, times(1)).save(any());
    }

    @Test
    public void updateGroupExpectedException() {
        when(groupRepository.findById(any())).thenReturn(Optional.empty());

        Assertions.assertThrows(GroupNotFoundException.class, () -> groupService.updateGroup(new RequestGroupDto(), any()));
    }

    @Test
    public void deleteGroup() {
        when(groupRepository.findById(any())).thenReturn(Optional.ofNullable(group));

        groupService.deleteGroup(any());

        verify(groupRepository, times(1)).deleteById(any());
    }

    @Test
    public void deleteGroupExpectedException() {
        when(groupRepository.findById(any())).thenReturn(Optional.empty());

        Assertions.assertThrows(GroupNotFoundException.class, () -> groupService.updateGroup(new RequestGroupDto(), any()));
    }
}
