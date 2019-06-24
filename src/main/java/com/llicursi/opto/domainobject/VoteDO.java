package com.llicursi.opto.domainobject;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Entity
@Table(name = "vote")
public class VoteDO {

    @EmbeddedId
    private VoteIdentity voteIdentity;

    @Column
    private Boolean agree;

    @Column
    private Integer changes;

    @Column
    private Date creation;

}