package com.llicursi.opto.dataaccessobject;

import com.llicursi.opto.domainobject.VoteDO;
import com.llicursi.opto.domainobject.VoteIdentity;
import org.springframework.data.repository.CrudRepository;

public interface VoteRepository extends CrudRepository<VoteDO, VoteIdentity> {


}
