package com.codeinside.attendancesystem.service.impl;

import com.codeinside.attendancesystem.dto.request.RequestAdminDto;
import com.codeinside.attendancesystem.dto.response.ResponseAdminDto;
import com.codeinside.attendancesystem.entity.Admin;
import com.codeinside.attendancesystem.enums.TypeUser;
import com.codeinside.attendancesystem.exception.AdminNotFoundException;
import com.codeinside.attendancesystem.exception.NumberPhoneAlreadyExistException;
import com.codeinside.attendancesystem.mapper.AdminMapper;
import com.codeinside.attendancesystem.repository.AdminRepository;
import com.codeinside.attendancesystem.repository.PersonRepository;
import com.codeinside.attendancesystem.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final AdminMapper adminMapper;
    private final PersonRepository personRepository;

    @Override
    public void saveAdmin(RequestAdminDto requestAdminDto) {
        Admin admin = adminMapper.requestAdminDtoToAdmin(requestAdminDto);
        admin.getPerson().setTypeUser(TypeUser.ADMIN);
        numberPhoneAlreadyExist(admin.getPerson().getNumberPhone());
        adminRepository.save(admin);
    }

    public void numberPhoneAlreadyExist(String numberPhone) {
        if(personRepository.findByNumberPhone(numberPhone) != null) {
            throw new NumberPhoneAlreadyExistException();
        }
    }

    @Override
    public ResponseAdminDto getAdmin(Long id) {
        Optional<Admin> adminOptional = adminRepository.findById(id);
        if(adminOptional.isEmpty()) {
            throw new AdminNotFoundException();
        }
        return adminMapper.adminToResponseAdminDto(adminOptional.get());
    }

    @Override
    public List<ResponseAdminDto> getAdmins(Long offset, Long limit) {
        List<Admin> admins = adminRepository.selectAllWithOffsetAndLimit(offset, limit);
        if(admins.isEmpty()) {
            throw new AdminNotFoundException();
        }
        return adminMapper.adminsToResponseAdminDtos(admins);
    }

    @Override
    public void updateAdmin(Long id, RequestAdminDto requestAdminDto) {
        Optional<Admin> adminOptional = adminRepository.findById(id);
        if(adminOptional.isEmpty()) {
            throw new AdminNotFoundException();
        }
        Admin admin = adminMapper.requestAdminDtoToAdminForPatch(requestAdminDto, adminOptional.get());
        if(requestAdminDto.getPerson().getNumberPhone() != null) {
            numberPhoneAlreadyExist(admin.getPerson().getNumberPhone());
        }
        adminRepository.save(admin);
    }

    @Override
    public void deleteAdmin(Long id) {
        Optional<Admin> adminOptional = adminRepository.findById(id);
        if(adminOptional.isEmpty()) {
            throw new AdminNotFoundException();
        }
        adminRepository.deleteById(id);

    }
}
