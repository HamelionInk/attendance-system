package com.codeinside.attendancesystem.service;

import com.codeinside.attendancesystem.dto.request.RequestAdminDto;
import com.codeinside.attendancesystem.dto.response.ResponseAdminDto;

import java.util.List;

public interface AdminService {
    void saveAdmin(RequestAdminDto requestAdminDto);
    ResponseAdminDto getAdmin(Long id);
    List<ResponseAdminDto> getAdmins(Long offset, Long limit);
    void updateAdmin(Long id, RequestAdminDto requestAdminDto);
    void deleteAdmin(Long id);
}
