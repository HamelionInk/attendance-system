package com.codeinside.attendancesystem.dto.request.post;

import lombok.Data;
import util.annotation.NumberPhoneAlreadyExist;
import util.annotation.PasswordMatches;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@PasswordMatches
@NumberPhoneAlreadyExist
@Data
public class RequestPersonDto {

    @NotBlank(message = "{password.notBlank}")
    private String password;
    @NotBlank(message = "{verifyPassword.notBlank}")
    private String verifyPassword;
    @NotBlank(message = "{firstName.notBlank}")
    private String firstName;
    @NotBlank(message = "{lastName.notBlank}")
    private String lastName;
    private String patronymic;
    @NotBlank(message = "{numberPhone.notBlank}")
    @Pattern(regexp = "^[1-9][0-9]{3,14}$", message = "{numberPhone.pattern}")
    private String numberPhone;
    @Max(value = 110, message = "{age.max}")
    @Min(value = 6, message = "{age.min}")
    @NotNull(message = "{age.notNull}")
    private Integer age;
    @Email(message = "{email}")
    private String email;

}
