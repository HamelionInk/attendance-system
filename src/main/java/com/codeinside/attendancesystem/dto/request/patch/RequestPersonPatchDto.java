package com.codeinside.attendancesystem.dto.request.patch;

import lombok.Data;
import util.annotation.NumberPhoneAlreadyExist;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

@NumberPhoneAlreadyExist
@Data
public class RequestPersonPatchDto {

    private String password;
    private String verifyPassword;
    private String firstName;
    private String lastName;
    private String patronymic;
    @Pattern(regexp = "^[1-9]{1}[0-9]{3,14}$", message = "{numberPhone.pattern}")
    private String numberPhone;
    @Max(value = 110, message = "{age.max}")
    @Min(value = 6, message = "{age.min}")
    private Integer age;
    @Email(message = "{email}")
    private String email;

}
