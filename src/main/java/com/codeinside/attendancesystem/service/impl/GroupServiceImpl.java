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

    @Transactional
    @Override
    public void addStudentForGroup(Long studentId, Long groupId) {
        Optional<Group> groupOptional = groupRepository.findById(groupId);
        Group group = groupOptional.orElseThrow(GroupNotFoundException::new);

        Optional<Student> studentOptional = studentRepository.findById(studentId);
        Student student = studentOptional.orElseThrow(StudentNotFoundException::new);

        if(!checkRangeAge(group.getMinAge(), group.getMaxAge(), student.getPerson().getAge())) {
            throw new OutOfRangeAgeException();
        }

        if(!checkOutOfNumberOfStudents(group.getStudents().size(), group.getNumberOfStudents())) {
            throw new OutOfNumberOfStudentsException();
        }

        groupRepository.updateGroupIdForStudent(groupId, studentId);
    }

    private Boolean checkOutOfNumberOfStudents(int numbersOfStudent, int limitStudents) {
        return numbersOfStudent <= limitStudents;
    }

    private Boolean checkRangeAge(int min, int max, int init) {
        return init >= min && init <= max;
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
    public List<ResponseGroupDto> getGroups() {
        List<Group> groupList= groupRepository.findAll();
        return groupMapper.GroupListToResponseGroupListDto(groupList);
    }

    @Override
    public void deleteGroup(Long id) {
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

    @Override
    public void deleteStudentForGroup(Long studentId) {

    }
}
