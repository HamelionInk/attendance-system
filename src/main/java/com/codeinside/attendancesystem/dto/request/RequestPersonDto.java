package com.codeinside.attendancesystem.dto.request;

import lombok.Data;
import util.annotation.PasswordMatches;
import util.validator.marker.OnCreate;
import util.validator.marker.OnUpdate;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@PasswordMatches(groups = OnCreate.class)
@Data
public class RequestPersonDto {

    @NotBlank(message = "{password.notBlank}", groups = OnCreate.class)
    private String password;
    @NotBlank(message = "{verifyPassword.notBlank}", groups = OnCreate.class)
    private String verifyPassword;
    @NotBlank(message = "{firstName.notBlank}", groups = OnCreate.class)
    private String firstName;
    @NotBlank(message = "{lastName.notBlank}", groups = OnCreate.class)
    private String lastName;
    private String patronymic;
    @NotBlank(message = "{numberPhone.notBlank}", groups = OnCreate.class)
    @Pattern(regexp = "^[1-9][0-9]{3,14}$", message = "{numberPhone.pattern}", groups = { OnCreate.class, OnUpdate.class })
    private String numberPhone;
    @Max(value = 110, message = "{age.max}", groups = { OnCreate.class, OnUpdate.class })
    @Min(value = 6, message = "{age.min}", groups = { OnCreate.class, OnUpdate.class })
    @NotNull(message = "{age.notNull}", groups = OnCreate.class)
    private Integer age;
    @Email(message = "{email}", groups = { OnCreate.class, OnUpdate.class })
    private String email;

}
