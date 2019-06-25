package com.llicursi.opto.controller;

import com.llicursi.opto.auth.AuthUtils;
import com.llicursi.opto.datatransferobject.SubjectDTO;
import com.llicursi.opto.datatransferobject.VoteDTO;
import com.llicursi.opto.datatransferobject.mapper.SubjectMapper;
import com.llicursi.opto.exception.InvalidDateRangeException;
import com.llicursi.opto.exception.InvalidVoteException;
import com.llicursi.opto.exception.ResultNotFoundException;
import com.llicursi.opto.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class SubjectController {

    private final SubjectService subjectService;

    @Autowired
    public SubjectController(final SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    /**
     * Get all active Subjects
     *
     * @return List of subject within the active interval
     */
    @GetMapping("/subject")
    public List<SubjectDTO> getAllActiveSubjects() {
        return SubjectMapper.makeSubjectDTOList(subjectService.findAllActive());
    }


    /**
     * Get all Subjects from specific User
     *
     * @return List of subject by User
     */
    @GetMapping("/user/subject")
    public List<SubjectDTO> getAllSubjectsFromUser(Principal principal) {
        Long currentUserId = AuthUtils.retrieveUserId(principal);
        return SubjectMapper.makeSubjectDTOList(subjectService.findAll(currentUserId));
    }

    /**
     * Get Subject by Id
     *
     * @return Subject of given Id
     */
    @GetMapping("/subject/{id}")
    public SubjectDTO getSubjectById(@PathVariable Long id) throws ResultNotFoundException {
        return SubjectMapper.convertToSubjectDTO(subjectService.findById(id));
    }

    /**
     * Create subject
     */
    @PostMapping("/subject")
    @ResponseStatus(HttpStatus.CREATED)
    public void createSubject(@RequestBody SubjectDTO subjectDTO, Principal principal) throws InvalidDateRangeException, ResultNotFoundException {
        Long currentUserId = AuthUtils.retrieveUserId(principal);
        subjectService.create(SubjectMapper.convertToSubjectDO(subjectDTO), currentUserId);
    }

    /**
     * Update subject
     */
    @PutMapping("/subject")
    public void updateSubject(@RequestBody SubjectDTO subjectDTO, Principal principal) throws ResultNotFoundException {
        Long currentUserId = AuthUtils.retrieveUserId(principal);
        subjectService.update(SubjectMapper.convertToSubjectDO(subjectDTO), currentUserId);
    }

    /**
     * Delete subject
     */
    @DeleteMapping("/subject/{id}")
    public void deleteSubject(@PathVariable Long id) throws ResultNotFoundException {
        subjectService.delete(id);
    }

    /**
     * Vote subject
     */
    @PutMapping("/subject/vote")
    public void voteSubject(@RequestBody VoteDTO voteDTO, Principal principal) throws ResultNotFoundException, InvalidVoteException {
        Long currentUserId = AuthUtils.retrieveUserId(principal);
        subjectService.vote(currentUserId, voteDTO);
    }


}
