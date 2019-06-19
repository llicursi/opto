package com.llicursi.opto.dataaccessobject;

import com.llicursi.opto.domainobject.SubjectDO;
import org.springframework.data.repository.CrudRepository;

public interface SubjectRepository extends CrudRepository<SubjectDO, Long> {
}
