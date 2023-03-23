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
        if(personRepository.findByNumberPhone(numberPhone).isPresent()) {
            throw new NumberPhoneAlreadyExistException();
        }
    }

    @Override
    public ResponseAdminDto getAdmin(Long id) {
        Admin admin = adminRepository
                .findById(id)
                .orElseThrow(AdminNotFoundException::new);
        return adminMapper.adminToResponseAdminDto(admin);
    }

    @Override
    public List<ResponseAdminDto> getAdmins(Long offset, Long limit) {
        List<Admin> admins = adminRepository
                .selectAllWithOffsetAndLimit(offset, limit)
                .orElseThrow(AdminNotFoundException::new);
        return adminMapper.adminsToResponseAdminDtos(admins);
    }

    @Override
    public void updateAdmin(Long id, RequestAdminDto requestAdminDto) {
        Admin admin = adminRepository
                .findById(id)
                .orElseThrow(AdminNotFoundException::new);
        Admin adminUpdated = adminMapper.requestAdminDtoToAdminForPatch(requestAdminDto, admin);
        if(requestAdminDto.getPerson().getNumberPhone() != null) {
            numberPhoneAlreadyExist(adminUpdated.getPerson().getNumberPhone());
        }
        adminRepository.save(adminUpdated);
    }

    @Override
    public void deleteAdmin(Long id) {
        adminRepository
                .findById(id)
                .orElseThrow(AdminNotFoundException::new);
        adminRepository.deleteById(id);
    }
}
