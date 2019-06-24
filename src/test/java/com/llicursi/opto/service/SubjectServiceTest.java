package com.llicursi.opto.service;

import com.llicursi.opto.dataaccessobject.SubjectRepository;
import com.llicursi.opto.datatransferobject.SubjectDTO;
import com.llicursi.opto.datatransferobject.mapper.SubjectMapper;
import com.llicursi.opto.domainobject.SubjectDO;
import com.llicursi.opto.domainobject.UserDO;
import com.llicursi.opto.exception.InvalidDateRangeException;
import com.llicursi.opto.exception.ResultNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@RunWith(SpringRunner.class)
public class SubjectServiceTest {

    @Mock
    private SubjectRepository mockSubjectRepository;


    @Mock
    private UserService mockUserService;

    private SubjectServiceImpl subjectService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        subjectService = new SubjectServiceImpl(mockSubjectRepository, mockUserService);
    }

    @Test
    public void givenCreateSubject_whenValidSubject_thenCreateSuccessfully() throws ResultNotFoundException, InvalidDateRangeException {

        SubjectDTO sourceObject = SubjectDTO.newBuilder()
                .setTitle("Topic 7")
                .setDescription(" 7 days of difference")
                .setStart(Date.from(LocalDateTime.now().atZone(ZoneOffset.systemDefault()).toInstant()))
                .setDue(Date.from(LocalDateTime.now().plus(7, ChronoUnit.DAYS).atZone(ZoneOffset.systemDefault()).toInstant()))
                .createSubjectDTO();

        SubjectDO sourceSubjectDO = SubjectMapper.convertToSubjectDO(sourceObject);
        SubjectDO resultSubjectDO = SubjectMapper.convertToSubjectDO(sourceObject);
        resultSubjectDO.setId(8L);
        UserDO mockedUser = new UserDO("Test", "Teste", "teste@opto.com", "****", "USER");
        mockedUser.setId(4L);

        when(mockSubjectRepository.save(any(SubjectDO.class))).thenReturn(resultSubjectDO);
        when(mockUserService.findById(any(Long.class))).thenReturn(mockedUser);

        SubjectDO resultingSubjectDO = subjectService.create(sourceSubjectDO, 4L);

        assertThat(resultingSubjectDO).isNotNull();
        assertThat(resultingSubjectDO.getId()).isNotNull();
        assertThat(resultingSubjectDO.getTitle()).isNotEmpty();

        verify(mockUserService, times(1)).findById(any(Long.class));
        verifyNoMoreInteractions(mockUserService);

        verify(mockSubjectRepository, times(1)).save(any(SubjectDO.class));
        verifyNoMoreInteractions(mockSubjectRepository);
    }

    @Test
    public void givenCreateSubject_whenInvalidLowerDateRange_thenThrowException() throws ResultNotFoundException {

        SubjectDTO sourceObject = SubjectDTO.newBuilder()
                .setTitle("Topic 8")
                .setDescription("Testing minimum difference constraint")
                .setStart(Date.from(LocalDateTime.now().atZone(ZoneOffset.systemDefault()).toInstant()))
                .setDue(Date.from(LocalDateTime.now().plus(6, ChronoUnit.DAYS).atZone(ZoneOffset.systemDefault()).toInstant()))
                .createSubjectDTO();

        SubjectDO sourceSubjectDO = SubjectMapper.convertToSubjectDO(sourceObject);
        SubjectDO resultSubjectDO = SubjectMapper.convertToSubjectDO(sourceObject);
        resultSubjectDO.setId(9L);
        UserDO mockedUser = new UserDO("Test", "Teste", "teste@opto.com", "****", "USER");
        mockedUser.setId(4L);

        when(mockSubjectRepository.save(any(SubjectDO.class))).thenReturn(resultSubjectDO);
        when(mockUserService.findById(any(Long.class))).thenReturn(mockedUser);

        try {
            SubjectDO resultingSubjectDO = subjectService.create(sourceSubjectDO, 4L);
            fail("Test failed: must throw InvalidDateRangeException");
        } catch (InvalidDateRangeException e) {
        }
    }

    @Test
    public void givenCreateSubject_whenInvalidGreaterDateRange_thenThrowException() throws ResultNotFoundException {

        SubjectDTO sourceObject = SubjectDTO.newBuilder()
                .setTitle("Topic 9")
                .setDescription("Testing maximum difference constraint")
                .setStart(Date.from(LocalDateTime.now().atZone(ZoneOffset.systemDefault()).toInstant()))
                .setDue(Date.from(LocalDateTime.now().plus(32, ChronoUnit.DAYS).atZone(ZoneOffset.systemDefault()).toInstant()))
                .createSubjectDTO();

        SubjectDO sourceSubjectDO = SubjectMapper.convertToSubjectDO(sourceObject);
        SubjectDO resultSubjectDO = SubjectMapper.convertToSubjectDO(sourceObject);
        resultSubjectDO.setId(10L);
        UserDO mockedUser = new UserDO("Test", "Teste", "teste@opto.com", "****", "USER");
        mockedUser.setId(4L);

        when(mockSubjectRepository.save(any(SubjectDO.class))).thenReturn(resultSubjectDO);
        when(mockUserService.findById(any(Long.class))).thenReturn(mockedUser);

        try {
            SubjectDO resultingSubjectDO = subjectService.create(sourceSubjectDO, 4L);
            fail("Test failed: must throw InvalidDateRangeException");
        } catch (InvalidDateRangeException e) {
        }
    }

    @Test
    public void givenCreateSubject_whenInvalidRange_thenThrowException() throws ResultNotFoundException {

        SubjectDTO sourceObject = SubjectDTO.newBuilder()
                .setTitle("Topic 10")
                .setDescription("Testing fail when due date is lower than start date")
                .setDue(Date.from(LocalDateTime.now().atZone(ZoneOffset.systemDefault()).toInstant()))
                .setStart(Date.from(LocalDateTime.now().plus(8, ChronoUnit.DAYS).atZone(ZoneOffset.systemDefault()).toInstant()))
                .createSubjectDTO();

        SubjectDO sourceSubjectDO = SubjectMapper.convertToSubjectDO(sourceObject);
        SubjectDO resultSubjectDO = SubjectMapper.convertToSubjectDO(sourceObject);
        resultSubjectDO.setId(11L);
        UserDO mockedUser = new UserDO("Test", "Teste", "teste@opto.com", "****", "USER");
        mockedUser.setId(4L);

        when(mockSubjectRepository.save(any(SubjectDO.class))).thenReturn(resultSubjectDO);
        when(mockUserService.findById(any(Long.class))).thenReturn(mockedUser);

        try {
            SubjectDO resultingSubjectDO = subjectService.create(sourceSubjectDO, 4L);
            fail("Test failed: must throw InvalidDateRangeException");
        } catch (InvalidDateRangeException e) {
        }
    }
}