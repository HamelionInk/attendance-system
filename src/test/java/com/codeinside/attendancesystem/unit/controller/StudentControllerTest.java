package com.codeinside.attendancesystem.unit.controller;

import com.codeinside.attendancesystem.controller.StudentController;
import com.codeinside.attendancesystem.dto.request.RequestPersonDto;
import com.codeinside.attendancesystem.dto.request.RequestStudentDto;
import com.codeinside.attendancesystem.exception.GroupNotFoundException;
import com.codeinside.attendancesystem.exception.NumberPhoneAlreadyExistException;
import com.codeinside.attendancesystem.exception.OutOfNumberOfStudentsException;
import com.codeinside.attendancesystem.exception.OutOfRangeAgeException;
import com.codeinside.attendancesystem.exception.StudentNotFoundException;
import com.codeinside.attendancesystem.exception.handler.ResponseExceptionHandler;
import com.codeinside.attendancesystem.service.StudentService;
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
public class StudentControllerTest {

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    private MockMvc mockMvc;

    private RequestStudentDto requestStudentDto;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(studentController).setControllerAdvice(new ResponseExceptionHandler()).build();
        requestStudentDto = new RequestStudentDto();
        RequestPersonDto requestPersonDto = new RequestPersonDto();
        requestPersonDto.setFirstName("Student");
        requestPersonDto.setLastName("Admin");
        requestPersonDto.setPassword("admin");
        requestPersonDto.setVerifyPassword("admin");
        requestPersonDto.setAge(34);
        requestPersonDto.setEmail("email@gmail.com");
        requestPersonDto.setNumberPhone("89085328281");
        requestStudentDto.setPerson(requestPersonDto);
    }

    @AfterEach
    public void tearDown() {

    }

    @Test
    public void postMappingSaveStudentStatusIsOk() throws Exception {
        mockMvc.perform(
                        post("/students")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(requestStudentDto)))
                .andExpect(status().isOk());
        verify(studentService, times(1)).saveStudent(any());

    }

    @Test
    public void postMappingSaveStudentStatusConflict() throws Exception {
        doThrow(NumberPhoneAlreadyExistException.class).when(studentService).saveStudent(any());
        mockMvc.perform(
                        post("/students")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(requestStudentDto)))
                .andExpect(status().isConflict());
        verify(studentService, times(1)).saveStudent(any());
    }

    @Test
    public void postMappingSaveStudentStatusBadRequest() throws Exception {
        requestStudentDto.getPerson().setEmail("r43");
        mockMvc.perform(
                        post("/students")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(requestStudentDto)))
                .andExpect(status().isBadRequest());
        verify(studentService, times(0)).saveStudent(any());
    }

    @Test
    public void postMappingAddStudentForGroupStatusIsOk() throws Exception {
        mockMvc.perform(
                        post("/students/{student_id}/group/{group_id}",1, 1)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(requestStudentDto)))
                .andExpect(status().isOk());
        verify(studentService, times(1)).addStudentForGroup(any(), any());
    }

    @Test
    public void postMappingAddStudentForGroupStatusNotFound() throws Exception {
        doThrow(StudentNotFoundException.class).when(studentService).addStudentForGroup(any(), any());
        mockMvc.perform(
                        post("/students/{student_id}/group/{group_id}",1, 1)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(studentService, times(1)).addStudentForGroup(any(), any());

        doThrow(GroupNotFoundException.class).when(studentService).addStudentForGroup(any(), any());
        mockMvc.perform(
                        post("/students/{student_id}/group/{group_id}",1, 1)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(studentService, times(2)).addStudentForGroup(any(), any());
    }

    @Test
    public void postMappingAddStudentForGroupStatusBadRequest() throws Exception {
        doThrow(OutOfRangeAgeException.class).when(studentService).addStudentForGroup(any(), any());
        mockMvc.perform(
                        post("/students/{student_id}/group/{group_id}",1, 1)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(studentService, times(1)).addStudentForGroup(any(), any());

        doThrow(OutOfNumberOfStudentsException.class).when(studentService).addStudentForGroup(any(), any());
        mockMvc.perform(
                        post("/students/{student_id}/group/{group_id}", 1, 1)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(studentService, times(2)).addStudentForGroup(any(), any());
    }

    @Test
    public void getMappingGetStudentStatusIsOk() throws Exception {
        mockMvc.perform(
                        get("/students/{id}", 1)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(studentService, times(1)).getStudent(any());
    }

    @Test
    public void getMappingGetStudentStatusNotFound() throws Exception {
        doThrow(StudentNotFoundException.class).when(studentService).getStudent(any());
        mockMvc.perform(
                        get("/students/{id}", 1)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(studentService, times(1)).getStudent(any());
    }

    @Test
    public void getMappingGetStudentsStatusIsOk() throws Exception {
        mockMvc.perform(
                        get("/students/all")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(studentService, times(1)).getStudents(any(), any());
    }

    @Test
    public void getMappingGetStudentsStatusNotFound() throws Exception {
        doThrow(StudentNotFoundException.class).when(studentService).getStudents(any(), any());
        mockMvc.perform(
                        get("/students/all")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(studentService, times(1)).getStudents(any(), any());
    }

    @Test
    public void patchMappingUpdateStudentStatusIsOk() throws Exception {
        mockMvc.perform(
                        patch("/students/{id}", 1)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(requestStudentDto)))
                .andExpect(status().isOk());
        verify(studentService, times(1)).updateStudent(any(), any());
    }

    @Test
    public void patchMappingUpdateStudentStatusNotFound() throws Exception {
        doThrow(StudentNotFoundException.class).when(studentService).updateStudent(any(), any());
        mockMvc.perform(
                        patch("/students/{id}", 1)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(requestStudentDto)))
                .andExpect(status().isNotFound());
        verify(studentService, times(1)).updateStudent(any(), any());
    }

    @Test
    public void patchMappingUpdateStudentStatusBadRequest() throws Exception {
        requestStudentDto.getPerson().setEmail("fg34");
        mockMvc.perform(
                        patch("/students/{id}", 1)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(requestStudentDto)))
                .andExpect(status().isBadRequest());
        verify(studentService, times(0)).updateStudent(any(), any());
    }

    @Test
    public void patchMappingExcludeStudentForGroupStatusIsOk() throws Exception {
        mockMvc.perform(
                        patch("/students/student/{id}", 1)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(studentService, times(1)).excludeStudentForGroup(any());
    }

    @Test
    public void patchMappingExcludeStudentForGroupStatusNotFound() throws Exception {
        doThrow(StudentNotFoundException.class).when(studentService).excludeStudentForGroup(any());
        mockMvc.perform(
                        patch("/students/student/{id}", 1)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(studentService, times(1)).excludeStudentForGroup(any());
    }

    @Test
    public void deleteMappingDeleteStudentStatusIsOk() throws Exception {
        mockMvc.perform(
                        delete("/students/{id}", 1)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(studentService, times(1)).deleteStudent(any());
    }

    @Test
    public void deleteMappingDeleteStudentStatusNotFound() throws Exception {
        doThrow(StudentNotFoundException.class).when(studentService).deleteStudent(any());
        mockMvc.perform(
                        delete("/students/{id}", 1)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(studentService, times(1)).deleteStudent(any());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
