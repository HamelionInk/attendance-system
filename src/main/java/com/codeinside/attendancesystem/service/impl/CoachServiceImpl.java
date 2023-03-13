package com.codeinside.attendancesystem.service.impl;

import com.codeinside.attendancesystem.dto.request.RequestCoachDto;
import com.codeinside.attendancesystem.dto.response.ResponseCoachDto;
import com.codeinside.attendancesystem.entity.Coach;
import com.codeinside.attendancesystem.enums.TypeUser;
import com.codeinside.attendancesystem.exception.CoachNotFoundException;
import com.codeinside.attendancesystem.mapper.CoachMapper;
import com.codeinside.attendancesystem.repository.CoachRepository;
import com.codeinside.attendancesystem.service.CoachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CoachServiceImpl implements CoachService {

    private final CoachMapper coachMapper;
    private final CoachRepository coachRepository;

    @Autowired
    public CoachServiceImpl(CoachMapper coachMapper, CoachRepository coachRepository) {
        this.coachMapper = coachMapper;
        this.coachRepository = coachRepository;
    }

    @Override
    public ResponseCoachDto getCoach(Long id) {
        Optional<Coach> coachOptional = coachRepository.findById(id);
        Coach coach = coachOptional.orElseThrow(CoachNotFoundException::new);
        return coachMapper.coachToResponseCoachDto(coach);
    }

    @Override
    public List<ResponseCoachDto> getCoaches(Long offset, Long limit) {
        List<Coach> coaches = coachRepository.selectAllWithOffsetAndLimit(offset, limit);
        if(coaches.isEmpty()) {
            throw new CoachNotFoundException();
        }
        return coachMapper.coachListToResponseCoachListDto(coaches);
    }

    @Override
    public void saveCoach(RequestCoachDto requestCoachDto) {
        Coach coach = coachMapper.requestCoachDtoToCoach(requestCoachDto);
        coach.getPerson().setTypeUser(TypeUser.TRAINER);
        coachRepository.save(coach);
    }

    @Override
    public void deleteCoach(Long id) {
        Optional<Coach> coachOptional = coachRepository.findById(id);
        if(coachOptional.isPresent()) {
            coachRepository.deleteById(id);
        } else {
            throw new CoachNotFoundException();
        }
    }

    @Override
    public void updateCoach(RequestCoachDto requestCoachDto, Long id) {
        Optional<Coach> coachOptional = coachRepository.findById(id);
        if(coachOptional.isEmpty()) {
            throw new CoachNotFoundException();
        }
        Coach coach = coachMapper.requestCoachDtoToCoachForPatch(requestCoachDto, coachOptional.get());
        coachRepository.save(coach);
    }
}