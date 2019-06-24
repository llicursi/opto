package com.llicursi.opto.dataaccessobject;

import com.llicursi.opto.domainobject.SubjectDO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SubjectRepository extends CrudRepository<SubjectDO, Long> {

    @Query("SELECT s FROM SubjectDO s " +
            "WHERE s.active = true AND s.start <= CURRENT_TIMESTAMP AND s.due >= CURRENT_TIMESTAMP ")
    List<SubjectDO> findAllActive();

    List<SubjectDO> findAllByUser_id(Long userId);
}
