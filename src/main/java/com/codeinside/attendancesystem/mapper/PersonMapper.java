package com.codeinside.attendancesystem.mapper;

import com.codeinside.attendancesystem.dto.request.RequestPersonDto;
import com.codeinside.attendancesystem.dto.response.ResponsePersonDto;
import com.codeinside.attendancesystem.entity.Person;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PersonMapper {

    ResponsePersonDto personToResponsePersonDto(Person person);
    List<ResponsePersonDto> personListToResponsePersonListDto(List<Person> persons);
    Person requestPersonDtoToPerson(RequestPersonDto requestPersonDto);
    Person requestPersonDtoToPersonForPatch(RequestPersonDto requestPersonDto, @MappingTarget Person person);

}
