package com.codeinside.attendancesystem.service;

import com.codeinside.attendancesystem.dto.request.patch.RequestCoachPatchDto;
import com.codeinside.attendancesystem.dto.request.post.RequestCoachDto;
import com.codeinside.attendancesystem.dto.response.ResponseCoachDto;

import java.util.List;

public interface CoachService {

    void saveCoach(RequestCoachDto requestCoachDto);
    ResponseCoachDto getCoach(Long id);
    List<ResponseCoachDto> getCoaches(Long offset, Long limit);
    void updateCoach(RequestCoachPatchDto requestCoachPatchDto, Long id);
    void deleteCoach(Long id);



}
