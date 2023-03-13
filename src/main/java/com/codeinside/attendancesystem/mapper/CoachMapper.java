package com.codeinside.attendancesystem.mapper;

import com.codeinside.attendancesystem.dto.request.RequestCoachDto;
import com.codeinside.attendancesystem.dto.response.ResponseCoachDto;
import com.codeinside.attendancesystem.entity.Coach;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CoachMapper {

    ResponseCoachDto coachToResponseCoachDto(Coach coach);
    List<ResponseCoachDto> coachListToResponseCoachListDto(List<Coach> coaches);
    Coach requestCoachDtoToCoach(RequestCoachDto requestCoachDto);
    Coach requestCoachDtoToCoachForPatch(RequestCoachDto requestCoachDto, @MappingTarget Coach coach);

}
