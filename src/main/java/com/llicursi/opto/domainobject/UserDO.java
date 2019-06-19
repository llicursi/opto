package com.llicursi.opto.domainobject;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(
        name = "users",
        uniqueConstraints = @UniqueConstraint(name = "uc_id", columnNames = {"userId", "email"})
)
public class UserDO {

    @Id
    @GeneratedValue(generator = "seq_user_id", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name="seq_user_id", sequenceName = "SEQ_USER_ID", allocationSize=1)
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

    public UserDO(String name, String surname, String email, String password, String role) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.role = role;
    }

}
