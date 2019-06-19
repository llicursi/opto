package com.llicursi.opto.domainobject;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Entity
@Table(name = "subject")
public class SubjectDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false, updatable=false)
    private UserDO user;

    @NotNull
    @Column
    private Date creation;

    @NotNull
    @Column
    private Date due;

    @NotNull
    @Column(length = 70)
    private String title;

    @Column
    private String description;

    public SubjectDO(@NotNull UserDO user, @NotNull Date creation, @NotNull Date due, @NotNull String title, String description) {
        this.user = user;
        this.creation = creation;
        this.due = due;
        this.title = title;
        this.description = description;
    }
}