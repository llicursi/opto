package com.llicursi.opto.controller;

import com.llicursi.opto.datatransferobject.SubjectDTO;
import com.llicursi.opto.datatransferobject.mapper.SubjectMapper;
import com.llicursi.opto.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * @return List of subject within the active interval
     */
    @GetMapping("/subject")
    public List<SubjectDTO> getAllActiveSubjects() {
        return SubjectMapper.makeSubjectDTOList(subjectService.findAllActive());
    }


    /**
     * Get all active Subjects
     * @return List of subject within the active interval
     */
    @GetMapping("/user/subject")
    public List<SubjectDTO> getAllSubjectsFromUser(Principal principal) {
        return SubjectMapper.makeSubjectDTOList(subjectService.findAll());
    }

}
