package com.codeinside.attendancesystem.dto.request.patch;

import util.annotation.NumberPhoneAlreadyExist;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

@NumberPhoneAlreadyExist
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerifyPassword() {
        return verifyPassword;
    }

    public void setVerifyPassword(String verifyPassword) {
        this.verifyPassword = verifyPassword;
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
}
