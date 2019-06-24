package com.llicursi.opto.domainobject;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Data
@Embeddable
public class VoteIdentity implements Serializable {

    @NotNull
    @ManyToOne
    @JoinColumn(name="user_id", nullable=false, updatable=false)
    private UserDO user;

    @NotNull
    @ManyToOne
    @JoinColumn(name="subject_id", nullable=false, updatable=false)
    private SubjectDO subject;

    public VoteIdentity(){
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VoteIdentity that = (VoteIdentity) o;
        return Objects.equals(user, that.user) &&
                Objects.equals(subject, that.subject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, subject);
    }

    public VoteIdentity(@NotNull UserDO user, @NotNull SubjectDO subject) {
        this.user = user;
        this.subject = subject;
    }
}