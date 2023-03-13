package com.codeinside.attendancesystem.service;

import com.codeinside.attendancesystem.dto.request.RequestCoachDto;
import com.codeinside.attendancesystem.dto.response.ResponseCoachDto;

import java.util.List;

public interface CoachService {

    ResponseCoachDto getCoach(Long id);
    List<ResponseCoachDto> getCoaches();
    void saveCoach(RequestCoachDto requestCoachDto);
    void deleteCoach(Long id);
    void updateCoach(RequestCoachDto requestCoachDto, Long id);



}
