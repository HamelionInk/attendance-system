package com.codeinside.attendancesystem.unit.controller;

import com.codeinside.attendancesystem.controller.LessonController;
import com.codeinside.attendancesystem.dto.request.RequestLessonDto;
import com.codeinside.attendancesystem.exception.LessonNotFoundException;
import com.codeinside.attendancesystem.exception.StudentNotFoundException;
import com.codeinside.attendancesystem.exception.handler.ResponseExceptionHandler;
import com.codeinside.attendancesystem.service.LessonService;
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

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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
public class LessonControllerTest {

    @Mock
    private LessonService lessonService;

    @InjectMocks
    private LessonController lessonController;

    private MockMvc mockMvc;

    private RequestLessonDto requestLessonDto;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(lessonController).setControllerAdvice(new ResponseExceptionHandler()).build();
        requestLessonDto = new RequestLessonDto();
        requestLessonDto.setLessonName("Old");
        requestLessonDto.setCoachId(1L);
        requestLessonDto.setGroupId(1L);
        requestLessonDto.setCoachName("TestName");
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR, 1);
        requestLessonDto.setStartDate(calendar.getTime());
    }

    @AfterEach
    public void tearDown() {

    }

    @Test
    public void postMappingSaveLessonStatusIsOk() throws Exception {
        mockMvc.perform(
                        post("/lessons")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(requestLessonDto)))
                .andExpect(status().isOk());
        verify(lessonService, times(1)).saveLesson(any());
    }

    @Test
    public void postMappingSaveLessonStatusBadRequest() throws Exception {
        requestLessonDto.setStartDate(null);
        mockMvc.perform(
                        post("/lessons")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(requestLessonDto)))
                .andExpect(status().isBadRequest());
        verify(lessonService, times(0)).saveLesson(any());
    }

    @Test
    public void getMappingGetLessonStatusIsOk() throws Exception {
        mockMvc.perform(
                        get("/lessons/{id}", 1)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(lessonService, times(1)).getLesson(any());
    }

    @Test
    public void getMappingGetLessonStatusNotFound() throws Exception {
        doThrow(LessonNotFoundException.class).when(lessonService).getLesson(any());
        mockMvc.perform(
                        get("/lessons/{id}", 1)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(lessonService, times(1)).getLesson(any());
    }

    @Test
    public void getMappingGetLessonsStatusIsOk() throws Exception {
        mockMvc.perform(
                        get("/lessons/all")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(lessonService, times(1)).getLessons(any(), any());
    }

    @Test
    public void getMappingGetLessonsStatusNotFound() throws Exception {
        doThrow(LessonNotFoundException.class).when(lessonService).getLessons(any(), any());
        mockMvc.perform(
                        get("/lessons/all")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(lessonService, times(1)).getLessons(any(), any());
    }

    @Test
    public void getMappingGetLessonsForStudentStatusIsOk() throws Exception {
        mockMvc.perform(
                        get("/lessons/student/{studentId}", 1)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(lessonService, times(1)).getLessonsForStudent(any());
    }

    @Test
    public void getMappingGetLessonsForStudentStatusNotFound() throws Exception {
        doThrow(StudentNotFoundException.class).when(lessonService).getLessonsForStudent(any());
        mockMvc.perform(
                        get("/lessons/student/{studentId}", 1)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(lessonService, times(1)).getLessonsForStudent(any());
    }

    @Test
    public void patchMappingUpdateAttendanceForStudentStatusIsOk() throws Exception {
        mockMvc.perform(
                        patch("/lessons/{lessonId}/student/{studentId}", 1, 1)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(lessonService, times(1)).updateAttendanceLessonForStudent(any(), any(), any());
    }

    @Test
    public void patchMappingUpdateAttendanceForStudentStatusNotFound() throws Exception {
        doThrow(StudentNotFoundException.class).when(lessonService).updateAttendanceLessonForStudent(any(), any(), any());
        mockMvc.perform(
                        patch("/lessons/{lessonId}/student/{studentId}", 1, 1)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(lessonService, times(1)).updateAttendanceLessonForStudent(any(), any(), any());
    }

    @Test
    public void patchMappingUpdateLessonStatusIsOk() throws Exception {
        mockMvc.perform(
                        patch("/lessons/{id}", 1)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(requestLessonDto)))
                .andExpect(status().isOk());
        verify(lessonService, times(1)).updateLesson(any(), any());
    }

    @Test
    public void patchMappingUpdateLessonStatusNotFound() throws Exception {
        doThrow(LessonNotFoundException.class).when(lessonService).updateLesson(any(), any());
        mockMvc.perform(
                        patch("/lessons/{id}", 1)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(requestLessonDto)))
                .andExpect(status().isNotFound());
        verify(lessonService, times(1)).updateLesson(any(), any());
    }

    @Test
    public void patchMappingUpdateLessonsStatusBadRequest() throws Exception {
        requestLessonDto.setStartDate(new Date());
        mockMvc.perform(
                        patch("/lessons/{id}", 1)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(asJsonString(requestLessonDto)))
                .andExpect(status().isBadRequest());
        verify(lessonService, times(0)).updateLesson(any(), any());
    }

    @Test
    public void deleteMappingDeleteLessonStatusIsOk() throws Exception {
        mockMvc.perform(
                        delete("/lessons/{id}", 1)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(lessonService, times(1)).deleteLesson(any());
    }

    @Test
    public void deleteMappingDeleteLessonStatusNotFound() throws Exception {
        doThrow(LessonNotFoundException.class).when(lessonService).deleteLesson(any());
        mockMvc.perform(
                        delete("/lessons/{id}", 1)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(lessonService, times(1)).deleteLesson(any());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
