package com.llicursi.opto.domainobject;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(
        name = "user",
        uniqueConstraints = @UniqueConstraint(name = "uc_id", columnNames = {"id", "email"})
)
public class UserDO {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 40)
    @NotNull(message = "User name can not be null!")
    private String name;

    @Column(nullable = false, length = 120)
    @NotNull(message = "User surname can not be null!")
    private String surname;

    @Column(unique=true, length = 120)
    @NotNull(message = "User email can not be null!")
    private String email;

    @JsonIgnore
    @Column(nullable = false, length = 64)
    @NotNull(message = "User password can not be null!")
    private String password;

    @JsonIgnore
    @Column(nullable = false)
    private String role;

    public UserDO(){
    }

    public UserDO(String name, String surname, String email, String password, String role) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.role = role;
    }

}
