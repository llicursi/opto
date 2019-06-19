package com.llicursi.opto.datatransferobject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class UserDTO {

    @JsonIgnore
    private Long id;

    @NotNull(message = "User name can not be null!")
    private String name;

    private String email;

    @JsonIgnore
    @NotNull(message = "User password can not be null!")
    private String password;

    private String role;

    public UserDTO(){
    }

    public UserDTO(Long id, String name, String email, String password, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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
            return new UserDTO(id, name, email, password, role);
        }
    }

}


