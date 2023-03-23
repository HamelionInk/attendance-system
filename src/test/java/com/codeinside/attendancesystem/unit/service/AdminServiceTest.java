package com.codeinside.attendancesystem.unit.service;

import com.codeinside.attendancesystem.dto.request.RequestAdminDto;
import com.codeinside.attendancesystem.dto.request.RequestPersonDto;
import com.codeinside.attendancesystem.dto.response.ResponseAdminDto;
import com.codeinside.attendancesystem.dto.response.ResponsePersonDto;
import com.codeinside.attendancesystem.entity.Admin;
import com.codeinside.attendancesystem.entity.Person;
import com.codeinside.attendancesystem.exception.AdminNotFoundException;
import com.codeinside.attendancesystem.exception.NumberPhoneAlreadyExistException;
import com.codeinside.attendancesystem.mapper.AdminMapper;
import com.codeinside.attendancesystem.repository.AdminRepository;
import com.codeinside.attendancesystem.repository.PersonRepository;
import com.codeinside.attendancesystem.service.impl.AdminServiceImpl;
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
public class AdminServiceTest {

    @Mock
    private AdminRepository adminRepository;
    @Mock
    private PersonRepository personRepository;
    @Mock
    private AdminMapper adminMapper;
    @InjectMocks
    private AdminServiceImpl adminService;
    private Admin admin;

    @BeforeEach
    public void setUp() {
       admin = new Admin();
       Person person = new Person();
       person.setNumberPhone("89085328288");
       admin.setPerson(person);
    }

    @Test
    public void saveAdmin() {
        when(adminMapper.requestAdminDtoToAdmin(any())).thenReturn(admin);

        adminService.saveAdmin(any());

        verify(adminRepository, times(1)).save(any());
    }

    @Test
    public void saveAdminExpectedException() {
        when(adminMapper.requestAdminDtoToAdmin(any())).thenReturn(admin);
        when(personRepository.findByNumberPhone(admin.getPerson().getNumberPhone())).thenReturn(Optional.of(new Person()));

        Assertions.assertThrows(NumberPhoneAlreadyExistException.class, () -> adminService.saveAdmin(any()));

        verify(adminRepository, times(0)).save(any());
    }

    @Test
    public void getAdmin() {
        ResponseAdminDto responseAdminDto = new ResponseAdminDto();
        ResponsePersonDto responsePersonDto = new ResponsePersonDto();
        responsePersonDto.setNumberPhone("89085328288");
        responseAdminDto.setPerson(responsePersonDto);

        when(adminRepository.findById(any())).thenReturn(Optional.ofNullable(admin));
        when(adminMapper.adminToResponseAdminDto(any())).thenReturn(responseAdminDto);
        ResponseAdminDto responseAdminDtoTest = adminService.getAdmin(any());

        Assertions.assertEquals(admin.getPerson().getNumberPhone(), responseAdminDtoTest.getPerson().getNumberPhone());
        verify(adminRepository, times(1)).findById(any());

    }

    @Test
    public void getAdminExpectedException() {
        when(adminRepository.findById(any())).thenReturn(Optional.empty());

        Assertions.assertThrows(AdminNotFoundException.class, () -> adminService.getAdmin(any()));
        verify(adminRepository, times(1)).findById(any());
    }

    @Test
    public void getAdmins() {
        List<Admin> admins = new ArrayList<>();
        admins.add(admin);
        ResponseAdminDto responseAdminDto = new ResponseAdminDto();
        ResponsePersonDto responsePersonDto = new ResponsePersonDto();
        responsePersonDto.setNumberPhone("89085328288");
        responseAdminDto.setPerson(responsePersonDto);
        List<ResponseAdminDto> responseAdminDtos = new ArrayList<>();
        responseAdminDtos.add(responseAdminDto);

        when(adminRepository.selectAllWithOffsetAndLimit(any(), any())).thenReturn(Optional.of(admins));
        when(adminMapper.adminsToResponseAdminDtos(any())).thenReturn(responseAdminDtos);
        adminService.getAdmins(any(), any());

        Assertions.assertEquals(admins.size(), responseAdminDtos.size());
        verify(adminRepository, times(1)).selectAllWithOffsetAndLimit(any(), any());
    }

    @Test
    public void getAdminsExpectedException() {
        when(adminRepository.selectAllWithOffsetAndLimit(any(), any())).thenReturn(Optional.empty());

        Assertions.assertThrows(AdminNotFoundException.class, () -> adminService.getAdmins(any(), any()));

        verify(adminRepository, times(1)).selectAllWithOffsetAndLimit(any(), any());
    }

    @Test
    public void updateAdmin() {
        RequestAdminDto requestAdminDto = new RequestAdminDto();
        RequestPersonDto requestPersonDto = new RequestPersonDto();
        requestPersonDto.setNumberPhone("89085328281");
        requestAdminDto.setPerson(requestPersonDto);
        when(adminRepository.findById(any())).thenReturn(Optional.ofNullable(admin));
        when(adminMapper.requestAdminDtoToAdminForPatch(any(), any())).thenReturn(admin);

        adminService.updateAdmin(any(), requestAdminDto);

        verify(adminRepository, times(1)).save(any());
    }

    @Test
    public void updateAdminExpectedExceptionAdminNotFound() {
        when(adminRepository.findById(any())).thenReturn(Optional.empty());

        Assertions.assertThrows(AdminNotFoundException.class, () -> adminService.updateAdmin(any(), new RequestAdminDto()));

        verify(adminRepository, times(0)).save(any());
    }

    @Test
    public void updateAdminExpectedExceptionNumberPhoneAlreadyExist() {
        RequestAdminDto requestAdminDto = new RequestAdminDto();
        RequestPersonDto requestPersonDto = new RequestPersonDto();
        requestPersonDto.setNumberPhone("89085328288");
        requestAdminDto.setPerson(requestPersonDto);

        when(adminRepository.findById(any())).thenReturn(Optional.ofNullable(admin));
        when(adminMapper.requestAdminDtoToAdminForPatch(any(), any())).thenReturn(admin);
        when(personRepository.findByNumberPhone(any())).thenReturn(Optional.ofNullable(admin.getPerson()));

        Assertions.assertThrows(NumberPhoneAlreadyExistException.class, () -> adminService.updateAdmin(any(), requestAdminDto));
        verify(adminRepository, times(0)).save(any());
    }

    @Test
    public void deleteAdmin() {
        when(adminRepository.findById(any())).thenReturn(Optional.ofNullable(admin));

        adminService.deleteAdmin(any());

        verify(adminRepository, times(1)).deleteById(any());
    }

    @Test
    public void deleteAdminExpectedException() {
        when(adminRepository.findById(any())).thenReturn(Optional.empty());

        Assertions.assertThrows(AdminNotFoundException.class, () -> adminService.deleteAdmin(any()));

        verify(adminRepository, times(0)).deleteById(any());
    }
}
