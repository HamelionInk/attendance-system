package com.codeinside.attendancesystem.dto.response;

import com.codeinside.attendancesystem.enums.TypeUser;
import lombok.Data;

@Data
public class ResponsePersonDto {

    private String firstName;
    private String lastName;
    private String patronymic;
    private String numberPhone;
    private Integer age;
    private String email;
    private TypeUser typeUser;

}
