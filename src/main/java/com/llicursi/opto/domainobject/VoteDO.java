package com.llicursi.opto.domainobject;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
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

    public VoteDO(VoteIdentity voteIdentity, Boolean agree, Integer changes, Date creation) {
        this.voteIdentity = voteIdentity;
        this.agree = agree;
        this.changes = changes;
        this.creation = creation;
    }
}