package com.llicursi.opto.service;

import com.llicursi.opto.dataaccessobject.SubjectRepository;
import com.llicursi.opto.domainobject.SubjectDO;
import com.llicursi.opto.exception.ResultNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;

    @Autowired
    public SubjectServiceImpl(final SubjectRepository subjectRepository){
        this.subjectRepository = subjectRepository;
    }

    @Override
    public List<SubjectDO> findAll(){
        return (List<SubjectDO>) subjectRepository.findAll();
    }

    @Override
    public List<SubjectDO> findAllActive() {
        return subjectRepository.findAllActive();
    }

    @Override
    public SubjectDO findById(Long id) throws ResultNotFoundException {
        return subjectRepository.findById(id).orElseThrow(() -> new ResultNotFoundException("Could not find current subject with id: " + id));
    }

    @Override
    @Transactional
    public SubjectDO create(SubjectDO subject) {
        SubjectDO subjectDO = subjectRepository.save(subject);
        return subjectDO;
    }

    @Override
    @Transactional
    public void update(SubjectDO subject, Long subjectId) throws ResultNotFoundException {
        SubjectDO foundSubject = findById(subjectId);
        foundSubject.setDescription(subject.getDescription());
        subjectRepository.save(foundSubject);
    }

    @Override
    @Transactional
    public void delete(Long id) throws ResultNotFoundException {
        SubjectDO subjectDO = findById(id);
        subjectRepository.delete(subjectDO);
    }


}
