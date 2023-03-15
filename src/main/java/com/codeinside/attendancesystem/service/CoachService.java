package com.codeinside.attendancesystem.service;

import com.codeinside.attendancesystem.dto.request.patch.RequestCoachPatchDto;
import com.codeinside.attendancesystem.dto.request.post.RequestCoachDto;
import com.codeinside.attendancesystem.dto.response.ResponseCoachDto;

import java.util.List;

public interface CoachService {

    ResponseCoachDto getCoach(Long id);
    List<ResponseCoachDto> getCoaches(Long offset, Long limit);
    void saveCoach(RequestCoachDto requestCoachDto);
    void deleteCoach(Long id);
    void updateCoach(RequestCoachPatchDto requestCoachPatchDto, Long id);



}
