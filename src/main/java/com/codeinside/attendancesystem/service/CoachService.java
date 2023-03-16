package com.codeinside.attendancesystem.service;

import com.codeinside.attendancesystem.dto.request.RequestCoachDto;
import com.codeinside.attendancesystem.dto.response.ResponseCoachDto;

import java.util.List;

public interface CoachService {

    void saveCoach(RequestCoachDto requestCoachDto);
    ResponseCoachDto getCoach(Long id);
    List<ResponseCoachDto> getCoaches(Long offset, Long limit);
    void updateCoach(RequestCoachDto requestCoachDto, Long id);
    void deleteCoach(Long id);



}
