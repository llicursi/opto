package com.llicursi.opto.datatransferobject;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class UserRegisterDTO {

    @NotNull(message = "User name can not be null!")
    private String name;

    @NotNull(message = "User surname can not be null!")
    private String surname;

    @NotNull(message = "User email can not be null!")
    private String email;

    @NotNull(message = "User password can not be null!")
    private String password;

    public UserRegisterDTO() {
    }

    public UserRegisterDTO(String name, String surname, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }

    public UserRegisterDTO setName(String name) {
        this.name = name;
        return this;
    }

    public UserRegisterDTO setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public UserRegisterDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserRegisterDTO setPassword(String password) {
        this.password = password;
        return this;
    }
}
