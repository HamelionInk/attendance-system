package com.codeinside.attendancesystem.service.impl;

import com.codeinside.attendancesystem.dto.request.RequestAdminDto;
import com.codeinside.attendancesystem.dto.response.ResponseAdminDto;
import com.codeinside.attendancesystem.entity.Admin;
import com.codeinside.attendancesystem.enums.TypeUser;
import com.codeinside.attendancesystem.exception.AdminNotFoundException;
import com.codeinside.attendancesystem.mapper.AdminMapper;
import com.codeinside.attendancesystem.repository.AdminRepository;
import com.codeinside.attendancesystem.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final AdminMapper adminMapper;

    @Autowired
    public AdminServiceImpl(AdminRepository adminRepository, AdminMapper adminMapper) {
        this.adminRepository = adminRepository;
        this.adminMapper = adminMapper;
    }

    @Override
    public void saveAdmin(RequestAdminDto requestAdminDto) {
        Admin admin = adminMapper.requestAdminDtoToAdmin(requestAdminDto);
        admin.getPerson().setTypeUser(TypeUser.ADMIN);
        adminRepository.save(admin);
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
