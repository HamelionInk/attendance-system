package com.codeinside.attendancesystem.mapper;

import com.codeinside.attendancesystem.dto.request.patch.RequestPersonPatchDto;
import com.codeinside.attendancesystem.dto.request.post.RequestPersonDto;
import com.codeinside.attendancesystem.dto.response.ResponsePersonDto;
import com.codeinside.attendancesystem.entity.Person;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PersonMapper {

    ResponsePersonDto personToResponsePersonDto(Person person);
    List<ResponsePersonDto> personsToResponsePersonDtos(List<Person> persons);
    Person requestPersonDtoToPerson(RequestPersonDto requestPersonDto);
    Person requestPersonDtoToPersonForPatch(RequestPersonPatchDto requestPersonPatchDto, @MappingTarget Person person);

}
