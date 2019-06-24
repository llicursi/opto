package com.llicursi.opto.service;

import com.llicursi.opto.datatransferobject.VoteDTO;
import com.llicursi.opto.domainobject.SubjectDO;
import com.llicursi.opto.exception.InvalidDateRangeException;
import com.llicursi.opto.exception.InvalidVoteException;
import com.llicursi.opto.exception.ResultNotFoundException;

import java.util.List;

public interface SubjectService {

    List<SubjectDO> findAll(Long currentUserId);

    SubjectDO findById(Long id) throws ResultNotFoundException;

    SubjectDO create(SubjectDO subject, Long userId) throws ResultNotFoundException, InvalidDateRangeException;

    void update(SubjectDO subject, Long userId) throws ResultNotFoundException;

    void delete(Long id) throws ResultNotFoundException;

    List<SubjectDO> findAllActive();

    void vote(Long userId, VoteDTO voteDTO) throws ResultNotFoundException, InvalidVoteException;
}
