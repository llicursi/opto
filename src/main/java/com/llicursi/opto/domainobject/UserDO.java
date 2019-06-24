package com.llicursi.opto.domainobject;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDO userDO = (UserDO) o;
        return id.equals(userDO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
