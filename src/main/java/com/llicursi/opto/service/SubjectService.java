package com.llicursi.opto.service;

import com.llicursi.opto.domainobject.SubjectDO;
import com.llicursi.opto.exception.ResultNotFoundException;

import java.util.List;

public interface SubjectService {

    List<SubjectDO> findAll(Long currentUserId);

    SubjectDO findById(Long id) throws ResultNotFoundException;

    SubjectDO create(SubjectDO subject, Long userId) throws ResultNotFoundException;

    void update(SubjectDO subject, Long userId) throws ResultNotFoundException;

    void delete(Long id) throws ResultNotFoundException;

    List<SubjectDO> findAllActive();
}
