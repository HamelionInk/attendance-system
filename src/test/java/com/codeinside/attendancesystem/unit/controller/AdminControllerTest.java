package com.codeinside.attendancesystem.unit.controller;

import com.codeinside.attendancesystem.controller.AdminController;
import com.codeinside.attendancesystem.dto.request.RequestAdminDto;
import com.codeinside.attendancesystem.dto.request.RequestPersonDto;
import com.codeinside.attendancesystem.exception.AdminNotFoundException;
import com.codeinside.attendancesystem.exception.NumberPhoneAlreadyExistException;
import com.codeinside.attendancesystem.exception.handler.ResponseExceptionHandler;
import com.codeinside.attendancesystem.service.AdminService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class AdminControllerTest {
    @Mock
    private AdminService adminService;
    @InjectMocks
    private AdminController adminController;
    private MockMvc mockMvc;
    private RequestAdminDto requestAdminDto;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(adminController).setControllerAdvice(new ResponseExceptionHandler()).build();
        requestAdminDto = new RequestAdminDto();
        RequestPersonDto requestPersonDto = new RequestPersonDto();
        requestPersonDto.setFirstName("ADMIN");
        requestPersonDto.setLastName("Admin");
        requestPersonDto.setPassword("admin");
        requestPersonDto.setVerifyPassword("admin");
        requestPersonDto.setAge(34);
        requestPersonDto.setEmail("email@gmail.com");
        requestPersonDto.setNumberPhone("89085328281");
        requestAdminDto.setPerson(requestPersonDto);
    }

    @AfterEach
    public void tearDown() {

    }

    @Test
    public void postMappingSaveAdminStatusIsOk() throws Exception {
        mockMvc.perform(
                post("/admins")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestAdminDto)))
                .andExpect(status().isOk());
        verify(adminService, times(1)).saveAdmin(any());
    }

    @Test
    public void postMappingSaveAdminStatusBadRequest() throws Exception {
        requestAdminDto.getPerson().setVerifyPassword("ffefr");
        mockMvc.perform(
                post("/admins")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestAdminDto)))
                .andExpect(status().isBadRequest());
        verify(adminService, times(0)).saveAdmin(any());
    }

    @Test
    public void postMappingSaveAdminStatusConflict() throws Exception {
        doThrow(NumberPhoneAlreadyExistException.class).when(adminService).saveAdmin(any());
        mockMvc.perform(
                post("/admins")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestAdminDto)))
                .andExpect(status().isConflict());
        verify(adminService, times(1)).saveAdmin(any());
    }

    @Test
    public void getMappingGetAdminStatusIsOk() throws Exception {
        mockMvc.perform(
                get("/admins/{id}", 1))
                .andExpect(status().isOk());
        verify(adminService, times(1)).getAdmin(any());
    }

    @Test
    public void getMappingGetAdminStatusNotFound() throws Exception {
        doThrow(AdminNotFoundException.class).when(adminService).getAdmin(any());
        mockMvc.perform(
                get("/admins/{id}", 1)
        ).andExpect(status().isNotFound());
        verify(adminService, times(1)).getAdmin(any());
    }

    @Test
    public void getMappingGetAdminsStatusIsOk() throws Exception {
        mockMvc.perform(
                get("/admins")
        ).andExpect(status().isOk());
        verify(adminService, times(1)).getAdmins(any(), any());

    }

    @Test
    public void getMappingGetAdminsStatusNotFound() throws Exception {
        doThrow(AdminNotFoundException.class).when(adminService).getAdmins(any(), any());
        mockMvc.perform(
                get("/admins")
        ).andExpect(status().isNotFound());
        verify(adminService, times(1)).getAdmins(any(), any());

    }

    @Test
    public void patchMappingUpdateAdminStatusIsOk() throws Exception {
        mockMvc.perform(
                patch("/admins/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestAdminDto))
        ).andExpect(status().isOk());
        verify(adminService, times(1)).updateAdmin(any(), any());

    }

    @Test
    public void patchMappingUpdateAdminStatusBadRequest() throws Exception {
        requestAdminDto.getPerson().setEmail("gger");
        mockMvc.perform(
                patch("/admins/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestAdminDto))
        ).andExpect(status().isBadRequest());
        verify(adminService, times(0)).updateAdmin(any(), any());

    }

    @Test
    public void patchMappingUpdateAdminStatusNotFound() throws Exception {
        doThrow(AdminNotFoundException.class).when(adminService).updateAdmin(any(), any());
        mockMvc.perform(
                patch("/admins/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestAdminDto))
        ).andExpect(status().isNotFound());
        verify(adminService, times(1)).updateAdmin(any(), any());
    }

    @Test
    public void patchMappingUpdateAdminStatusConflict() throws Exception {
        doThrow(NumberPhoneAlreadyExistException.class).when(adminService).updateAdmin(any(), any());
        mockMvc.perform(
                patch("/admins/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestAdminDto))
        ).andExpect(status().isConflict());
        verify(adminService, times(1)).updateAdmin(any(), any());
    }

    @Test
    public void deleteMappingDeleteAdminStatusIsOk() throws Exception {
        mockMvc.perform(
                delete("/admins/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestAdminDto))
        ).andExpect(status().isOk());
        verify(adminService, times(1)).deleteAdmin(any());
    }

    @Test
    public void deleteMappingDeleteAdminStatusNotFound() throws Exception {
        doThrow(AdminNotFoundException.class).when(adminService).deleteAdmin(any());
        mockMvc.perform(
                delete("/admins/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestAdminDto))
        ).andExpect(status().isNotFound());
        verify(adminService, times(1)).deleteAdmin(any());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
