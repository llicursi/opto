package com.llicursi.opto.domainobject;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Entity
public class SubjectDO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "subject_id")
    private Long subjectId;

    @NotNull
    private Date startDate;

    @NotNull
    private Date endDate;

    @NotNull
    private String title;

    private String description;

    public SubjectDO(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

}