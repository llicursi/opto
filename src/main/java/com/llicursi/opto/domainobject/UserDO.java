package com.llicursi.opto.domainobject;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(
        name = "users",
        uniqueConstraints = @UniqueConstraint(name = "uc_id", columnNames = {"userId", "email"})
)
public class UserDO {

    @Id
    @GeneratedValue
    private Long userId;

    @Column(nullable = false, length = 40)
    @NotNull(message = "User name can not be null!")
    private String name;

    @Column(nullable = false, length = 40)
    @NotNull(message = "User surname can not be null!")
    private String surname;

    @Column(unique=true)
    @NotNull(message = "User email can not be null!")
    private String email;

    @Column(nullable = false, length = 64)
    @NotNull(message = "User password can not be null!")
    private String password;

    @Column(nullable = false)
    private String role;

    public UserDO(){
    }

    public UserDO(Long userId, String name, String surname, String email, String password, String role) {
        this.userId = userId;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
