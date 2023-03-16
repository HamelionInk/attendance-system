package com.codeinside.attendancesystem.unit.controller;

import com.codeinside.attendancesystem.controller.CoachController;
import com.codeinside.attendancesystem.dto.request.RequestCoachDto;
import com.codeinside.attendancesystem.dto.request.RequestPersonDto;
import com.codeinside.attendancesystem.exception.CoachNotFoundException;
import com.codeinside.attendancesystem.exception.NumberPhoneAlreadyExistException;
import com.codeinside.attendancesystem.exception.handler.ResponseExceptionHandler;
import com.codeinside.attendancesystem.repository.CoachRepository;
import com.codeinside.attendancesystem.service.CoachService;
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

import java.time.Period;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class CoachControllerTest {

    @Mock
    private CoachService coachService;

    @InjectMocks
    private CoachController coachController;

    private MockMvc mockMvc;

    private RequestCoachDto requestCoachDto;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(coachController).setControllerAdvice(new ResponseExceptionHandler()).build();
        requestCoachDto = new RequestCoachDto();
        RequestPersonDto requestPersonDto = new RequestPersonDto();
        requestPersonDto.setFirstName("ADMIN");
        requestPersonDto.setLastName("Admin");
        requestPersonDto.setPassword("admin");
        requestPersonDto.setVerifyPassword("admin");
        requestPersonDto.setAge(34);
        requestPersonDto.setEmail("email@gmail.com");
        requestPersonDto.setNumberPhone("89085328281");
        requestCoachDto.setPerson(requestPersonDto);
    }

    @AfterEach
    public void tearDown() {

    }

    @Test
    public void postMappingSaveCoachStatusIsOk() throws Exception {
        mockMvc.perform(
                        post("/coaches")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(requestCoachDto)))
                .andExpect(status().isOk());
        verify(coachService, times(1)).saveCoach(any());
    }

    @Test
    public void postMappingSaveCoachStatusBadRequest() throws Exception {
        requestCoachDto.getPerson().setVerifyPassword("fefefefe");
        mockMvc.perform(
                        post("/coaches")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(requestCoachDto)))
                .andExpect(status().isBadRequest());
        verify(coachService, times(0)).saveCoach(any());
    }

    @Test
    public void postMappingSaveCoachStatusConflict() throws Exception {
        doThrow(NumberPhoneAlreadyExistException.class).when(coachService).saveCoach(any());
        mockMvc.perform(
                        post("/coaches")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(requestCoachDto)))
                .andExpect(status().isConflict());
        verify(coachService, times(1)).saveCoach(any());
    }

    @Test
    public void getMappingGetCoachStatusIsOk() {

    }

    @Test
    public void getMappingGetCoachStatusNotFound() {

    }

    @Test
    public void getMappingGetCoachesStatusIsOk() {

    }

    @Test
    public void getMappingGetCoachesStatusNotFound() {

    }

    @Test
    public void patchMappingUpdateCoachStatusIsOk() {

    }

    @Test
    public void patchMappingUpdateCoachStatusNotFound() {

    }

    @Test
    public void patchMappingUpdateCoachStatusBadRequest() {

    }

    @Test
    public void patchMappingUpdateCoachStatusConflict() {

    }

    @Test
    public void deleteMappingDeleteCoachStatusIsOk() {

    }

    @Test
    public void deleteMappingDeleteCoachStatusNotFound() {

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
