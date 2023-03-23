package com.codeinside.attendancesystem.unit.controller;

import com.codeinside.attendancesystem.controller.GroupController;
import com.codeinside.attendancesystem.dto.request.RequestGroupDto;
import com.codeinside.attendancesystem.exception.GroupNotFoundException;
import com.codeinside.attendancesystem.exception.handler.ResponseExceptionHandler;
import com.codeinside.attendancesystem.service.GroupService;
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
public class GroupControllerTest {

    @Mock
    private GroupService groupService;

    @InjectMocks
    private GroupController groupController;

    private MockMvc mockMvc;

    private RequestGroupDto requestGroupDto;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(groupController).setControllerAdvice(new ResponseExceptionHandler()).build();
        requestGroupDto = new RequestGroupDto();
        requestGroupDto.setGroupName("group");
        requestGroupDto.setMaxAge(15);
        requestGroupDto.setMinAge(10);
        requestGroupDto.setNumberOfStudents(20);
    }

    @AfterEach
    public void tearDown() {

    }

    @Test
    public void postMappingSaveGroupStatusIsOk() throws Exception {
        mockMvc.perform(
                        post("/groups")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(requestGroupDto)))
                .andExpect(status().isOk());
        verify(groupService, times(1)).saveGroup(any());
    }

    @Test
    public void postMappingSaveGroupStatusBadRequest() throws Exception {
        requestGroupDto.setMinAge(20);
        requestGroupDto.setMaxAge(10);
        mockMvc.perform(
                        post("/groups")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(requestGroupDto)))
                .andExpect(status().isBadRequest());
        verify(groupService, times(0)).saveGroup(any());
    }

    @Test
    public void getMappingGetGroupStatusIsOk() throws Exception {
        mockMvc.perform(
                        get("/groups/{id}", 1)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(groupService, times(1)).getGroup(any());
    }

    @Test
    public void getMappingGetGroupStatusNotFound() throws Exception {
        doThrow(GroupNotFoundException.class).when(groupService).getGroup(any());
        mockMvc.perform(
                        get("/groups/{id}", 1)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(groupService, times(1)).getGroup(any());
    }

    @Test
    public void getMappingGetGroupsStatusIsOk() throws Exception {
        mockMvc.perform(
                        get("/groups")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(groupService, times(1)).getGroups(any(), any());
    }

    @Test
    public void getMappingGetGroupsStatusNotFound() throws Exception {
        doThrow(GroupNotFoundException.class).when(groupService).getGroups(any(), any());
        mockMvc.perform(
                        get("/groups")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(groupService, times(1)).getGroups(any(), any());
    }

    @Test
    public void patchMappingUpdateGroupStatusIsOk() throws Exception {
        mockMvc.perform(
                        patch("/groups/{id}", 1)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(requestGroupDto)))
                .andExpect(status().isOk());
        verify(groupService, times(1)).updateGroup(any(), any());
    }

    @Test
    public void patchMappingUpdateGroupStatusNotFound() throws Exception {
        doThrow(GroupNotFoundException.class).when(groupService).updateGroup(any(), any());
        mockMvc.perform(
                        patch("/groups/{id}", 1)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(requestGroupDto)))
                .andExpect(status().isNotFound());
        verify(groupService, times(1)).updateGroup(any(), any());
    }

    @Test
    public void patchMappingUpdateGroupStatusBadRequest() throws Exception {
        requestGroupDto.setMaxAge(20);
        requestGroupDto.setMinAge(30);
        mockMvc.perform(
                        patch("/groups/{id}", 1)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(requestGroupDto)))
                .andExpect(status().isBadRequest());
        verify(groupService, times(0)).updateGroup(any(), any());
    }

    @Test
    public void deleteMappingDeleteGroupStatusIsOk() throws Exception {
        mockMvc.perform(
                        delete("/groups/{id}", 1)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(groupService, times(1)).deleteGroup(any());
    }

    @Test
    public void deleteMappingDeleteGroupStatusNotFound() throws Exception {
        doThrow(GroupNotFoundException.class).when(groupService).deleteGroup(any());
        mockMvc.perform(
                        delete("/groups/{id}", 1)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(groupService, times(1)).deleteGroup(any());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
