package com.llicursi.opto.datatransferobject;

import com.llicursi.opto.domainobject.SubjectDO;
import com.llicursi.opto.domainobject.UserDO;
import com.llicursi.opto.domainobject.VoteIdentity;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
public class VoteDTO {

    private Long subject;

    private Boolean agree;

    public VoteDTO(Long subject, Boolean agree) {
        this.subject = subject;
        this.agree = agree;
    }
}