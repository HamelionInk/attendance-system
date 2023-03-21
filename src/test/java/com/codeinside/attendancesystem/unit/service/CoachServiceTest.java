package com.codeinside.attendancesystem.unit.service;

import com.codeinside.attendancesystem.dto.request.RequestCoachDto;
import com.codeinside.attendancesystem.dto.request.RequestPersonDto;
import com.codeinside.attendancesystem.dto.response.ResponseCoachDto;
import com.codeinside.attendancesystem.dto.response.ResponsePersonDto;
import com.codeinside.attendancesystem.entity.Coach;
import com.codeinside.attendancesystem.entity.Person;
import com.codeinside.attendancesystem.exception.CoachNotFoundException;
import com.codeinside.attendancesystem.exception.NumberPhoneAlreadyExistException;
import com.codeinside.attendancesystem.mapper.CoachMapper;
import com.codeinside.attendancesystem.repository.CoachRepository;
import com.codeinside.attendancesystem.repository.PersonRepository;
import com.codeinside.attendancesystem.service.CoachService;
import com.codeinside.attendancesystem.service.impl.CoachServiceImpl;
import org.junit.jupiter.api.AfterEach;
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
public class CoachServiceTest {

    @Mock
    private CoachRepository coachRepository;
    @Mock
    private PersonRepository personRepository;
    @Mock
    private CoachMapper coachMapper;
    @InjectMocks
    private CoachServiceImpl coachService;
    private Coach coach;

    @BeforeEach
    public void setUp() {
        coach = new Coach();
        Person person = new Person();
        person.setNumberPhone("89085328288");
        coach.setPerson(person);
    }

    @AfterEach
    public void tearDown() {

    }

    @Test
    public void saveCoach() {
        when(coachMapper.requestCoachDtoToCoach(any())).thenReturn(coach);
        when(personRepository.findByNumberPhone(any())).thenReturn(null);

        coachService.saveCoach(any());

        verify(coachRepository, times(1)).save(any());
    }

    @Test
    public void saveCoachExpectedException() {
        when(coachMapper.requestCoachDtoToCoach(any())).thenReturn(coach);
        when(personRepository.findByNumberPhone(any())).thenReturn(new Person());

        Assertions.assertThrows(NumberPhoneAlreadyExistException.class, () -> coachService.saveCoach(any()));

        verify(coachRepository, times(0)).save(any());
    }

    @Test
    public void getCoach() {
        ResponseCoachDto responseCoachDto = new ResponseCoachDto();
        ResponsePersonDto responsePersonDto = new ResponsePersonDto();
        responsePersonDto.setNumberPhone("89085328288");
        responseCoachDto.setPerson(responsePersonDto);
        when(coachRepository.findById(any())).thenReturn(Optional.ofNullable(coach));
        when(coachMapper.coachToResponseCoachDto(any())).thenReturn(responseCoachDto);

        ResponseCoachDto responseCoachDtoActual = coachService.getCoach(any());

        Assertions.assertEquals(coach.getPerson().getNumberPhone(), responseCoachDtoActual.getPerson().getNumberPhone());
    }

    @Test
    public void getCoachExpectedException() {
        when(coachRepository.findById(any())).thenReturn(Optional.empty());

        Assertions.assertThrows(CoachNotFoundException.class, () -> coachService.getCoach(any()));
    }

    @Test
    public void getCoaches() {
        List<Coach> coaches = new ArrayList<>();
        coaches.add(coach);
        List<ResponseCoachDto> responseCoachDtos = new ArrayList<>();
        ResponseCoachDto responseCoachDto = new ResponseCoachDto();
        ResponsePersonDto responsePersonDto = new ResponsePersonDto();
        responsePersonDto.setNumberPhone("89085328288");
        responseCoachDtos.add(responseCoachDto);

        when(coachRepository.selectAllWithOffsetAndLimit(any(), any())).thenReturn(coaches);
        when(coachMapper.coachesToResponseCoachDtos(any())).thenReturn(responseCoachDtos);

        coachService.getCoaches(any(), any());

        Assertions.assertEquals(coaches.size(), responseCoachDtos.size());
    }

    @Test
    public void getCoachesExpectedException() {
        when(coachRepository.selectAllWithOffsetAndLimit(any(), any())).thenReturn(new ArrayList<>());

        Assertions.assertThrows(CoachNotFoundException.class, () -> coachService.getCoaches(any(), any()));
    }

    @Test
    public void updateCoach() {
        Coach coachAfterMapper = new Coach();
        Person person = new Person();
        coachAfterMapper.setPerson(person);
        RequestCoachDto requestCoachDto = new RequestCoachDto();
        RequestPersonDto requestPersonDto = new RequestPersonDto();
        requestCoachDto.setPerson(requestPersonDto);
        when(coachRepository.findById(any())).thenReturn(Optional.ofNullable(coach));
        when(coachMapper.requestCoachDtoToCoachForPatch(any(), any())).thenReturn(coachAfterMapper);

        coachService.updateCoach(requestCoachDto, any());

        verify(coachRepository, times(1)).save(any());
    }

    @Test
    public void updateCoachExpectedException() {
        when(coachRepository.findById(any())).thenReturn(Optional.empty());

        Assertions.assertThrows(CoachNotFoundException.class, () -> coachService.updateCoach(new RequestCoachDto(), any()));
    }

    @Test
    public void deleteCoach() {
        when(coachRepository.findById(any())).thenReturn(Optional.ofNullable(coach));

        coachService.deleteCoach(any());

        verify(coachRepository, times(1)).deleteById(any());

    }

    @Test
    public void  deleteCoachExpectedException() {
        when(coachRepository.findById(any())).thenReturn(Optional.empty());

        Assertions.assertThrows(CoachNotFoundException.class, () -> coachService.deleteCoach(any()));
        verify(coachRepository, times(0)).deleteById(any());
    }



}
