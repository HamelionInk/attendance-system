package com.codeinside.attendancesystem.service.impl;

import com.codeinside.attendancesystem.dto.request.RequestCoachDto;
import com.codeinside.attendancesystem.dto.response.ResponseCoachDto;
import com.codeinside.attendancesystem.entity.Coach;
import com.codeinside.attendancesystem.enums.TypeUser;
import com.codeinside.attendancesystem.exception.CoachNotFoundException;
import com.codeinside.attendancesystem.exception.NumberPhoneAlreadyExistException;
import com.codeinside.attendancesystem.mapper.CoachMapper;
import com.codeinside.attendancesystem.repository.CoachRepository;
import com.codeinside.attendancesystem.repository.PersonRepository;
import com.codeinside.attendancesystem.service.CoachService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CoachServiceImpl implements CoachService {

    private final CoachMapper coachMapper;
    private final CoachRepository coachRepository;
    private final PersonRepository personRepository;

    @Override
    public void saveCoach(RequestCoachDto requestCoachDto) {
        Coach coach = coachMapper.requestCoachDtoToCoach(requestCoachDto);
        coach.getPerson().setTypeUser(TypeUser.TRAINER);
        numberPhoneAlreadyExist(coach.getPerson().getNumberPhone());
        coachRepository.save(coach);
    }

    public void numberPhoneAlreadyExist(String numberPhone) {
        if(personRepository.findByNumberPhone(numberPhone).isPresent()) {
            throw new NumberPhoneAlreadyExistException();
        }
    }

    @Override
    public ResponseCoachDto getCoach(Long id) {
        Coach coach = coachRepository
                .findById(id)
                .orElseThrow(CoachNotFoundException::new);
        return coachMapper.coachToResponseCoachDto(coach);
    }

    @Override
    public List<ResponseCoachDto> getCoaches(Long offset, Long limit) {
        List<Coach> coaches = coachRepository
                .selectAllWithOffsetAndLimit(offset, limit)
                .orElseThrow(CoachNotFoundException::new);
        return coachMapper.coachesToResponseCoachDtos(coaches);
    }

    @Override
    public void updateCoach(RequestCoachDto requestCoachDto, Long id) {
        Coach coach = coachRepository
                .findById(id)
                .orElseThrow(CoachNotFoundException::new);
        Coach coachUpdated = coachMapper.requestCoachDtoToCoachForPatch(requestCoachDto, coach);
        if(requestCoachDto.getPerson().getNumberPhone() != null) {
            numberPhoneAlreadyExist(coachUpdated.getPerson().getNumberPhone());
        }
        coachRepository.save(coachUpdated);
    }

    @Override
    public void deleteCoach(Long id) {
        coachRepository
                .findById(id)
                .orElseThrow(CoachNotFoundException::new);
        coachRepository.deleteById(id);
    }
}
