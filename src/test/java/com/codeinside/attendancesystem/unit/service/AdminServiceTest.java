package com.codeinside.attendancesystem.unit.service;

import com.codeinside.attendancesystem.entity.Admin;
import com.codeinside.attendancesystem.entity.Person;
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
import org.springframework.security.core.parameters.P;

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
        when(personRepository.findByNumberPhone(admin.getPerson().getNumberPhone())).thenReturn(null);
        adminService.saveAdmin(any());
        verify(adminRepository, times(1)).save(any());
    }

    @Test
    public void saveAdminExpectedException() {
        NumberPhoneAlreadyExistException exception = Assertions.assertThrows(NumberPhoneAlreadyExistException.class, () -> {
            when(adminMapper.requestAdminDtoToAdmin(any())).thenReturn(admin);
            when(personRepository.findByNumberPhone(admin.getPerson().getNumberPhone())).thenReturn(new Person());
            adminService.saveAdmin(any());
        });
        verify(adminRepository, times(0)).save(any());
    }
}
