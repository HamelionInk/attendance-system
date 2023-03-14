package com.codeinside.attendancesystem.dto.request;

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
    @Pattern(regexp = "^\\+[1-9]{1}[0-9]{3,14}$", message = "{numberPhone.pattern}")
    private String numberPhone;
    @Max(value = 110, message = "{age.max}")
    @Min(value = 6, message = "{age.min}")
    @NotNull(message = "{age.notNull}")
    private Integer age;
    @Email(message = "{email}")
    private String email;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVerifyPassword() {
        return verifyPassword;
    }

    public void setVerifyPassword(String verifyPassword) {
        this.verifyPassword = verifyPassword;
    }
}
