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

    @NotNull
    @ManyToOne
    @JoinColumn(name="user_id", nullable=false, updatable=false)
    private UserDO user;

    @NotNull
    @Column
    private Date start;

    @NotNull
    @Column
    private Date due;

    @NotNull
    @Column(length = 70)
    private String title;

    @Column
    private String description;
    
    @Column
    private Boolean active;

    public SubjectDO(@NotNull String title, String description, @NotNull Date start, @NotNull Date due) {
        this.start = start;
        this.due = due;
        this.title = title;
        this.description = description;
        this.active = true;
    }

    public SubjectDO(){
    }
}