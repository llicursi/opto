package com.llicursi.opto.service;

import com.llicursi.opto.dataaccessobject.SubjectRepository;
import com.llicursi.opto.domainobject.SubjectDO;
import com.llicursi.opto.domainobject.UserDO;
import com.llicursi.opto.exception.InvalidDateRangeException;
import com.llicursi.opto.exception.ResultNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class SubjectServiceImpl implements SubjectService {

    private static final int MIN_DAYS = 7;
    private static final int MAX_DAYS = 31;

    private final SubjectRepository subjectRepository;
    private final UserService userService;

    @Autowired
    public SubjectServiceImpl(final SubjectRepository subjectRepository, final UserService userService) {
        this.subjectRepository = subjectRepository;
        this.userService = userService;
    }

    @Override
    public List<SubjectDO> findAll(Long currentUserId) {
        return subjectRepository.findAllByUser_id(currentUserId);
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
    public SubjectDO create(SubjectDO subject, Long userId) throws ResultNotFoundException, InvalidDateRangeException {
        validateRange(subject);

        UserDO currentUser = userService.findById(userId);
        subject.setUser(currentUser);
        SubjectDO subjectDO = subjectRepository.save(subject);
        return subjectDO;
    }

    /**
     * Validates whether the start date is lower then due date and that the date range
     * has a minimun of 7 days and a maximum of 31 days
     *
     * @param subject
     */
    private void validateRange(SubjectDO subject) throws InvalidDateRangeException {

        if (subject.getStart().after(subject.getDue())) {
            throw new InvalidDateRangeException("Invalid Date Range: Due date must be greater than start date");
        }

        long diffInMillies = Math.abs(subject.getDue().getTime() - subject.getStart().getTime());
        long diffDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

        if (diffDays < MIN_DAYS || diffDays > MAX_DAYS) {
            throw new InvalidDateRangeException("Invalid Date Range: Voting period must not last less than 7 days and more than 31 days");
        }

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
