package com.llicursi.opto.datatransferobject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserDTO {

    @JsonIgnore
    private Long id;

    @NotNull(message = "User name can not be null!")
    private String name;

    @NotNull(message = "User surname can not be null!")
    private String surname;

    @NotNull(message = "User email can not be null!")
    private String email;

    @JsonIgnore
    @NotNull(message = "User password can not be null!")
    private String password;

    private String role;

    public UserDTO(){
    }

    public UserDTO(Long id, String name, String surname, String email, String password, String role) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    public static UserDTOBuilder newBuilder()
    {
        return new UserDTOBuilder();
    }

    public static class UserDTOBuilder
    {
        private Long id;
        private String name;
        private String email;
        private String surname;
        private String password;
        private String role;

        public UserDTOBuilder setId(Long id)
        {
            this.id = id;
            return this;
        }

        public UserDTOBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public UserDTOBuilder setSurname(String name) {
            this.surname = surname;
            return this;
        }

        public UserDTOBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        @JsonProperty
        public UserDTOBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public UserDTOBuilder setRole(String role) {
            this.role = role;
            return this;
        }

        public UserDTO createUserDTO()
        {
            return new UserDTO(id, name, surname, email, password, role);
        }
    }

}


