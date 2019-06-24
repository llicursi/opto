package com.llicursi.opto.service;

import com.llicursi.opto.dataaccessobject.SubjectRepository;
import com.llicursi.opto.dataaccessobject.VoteRepository;
import com.llicursi.opto.datatransferobject.SubjectDTO;
import com.llicursi.opto.datatransferobject.VoteDTO;
import com.llicursi.opto.datatransferobject.mapper.SubjectMapper;
import com.llicursi.opto.domainobject.SubjectDO;
import com.llicursi.opto.domainobject.UserDO;
import com.llicursi.opto.domainobject.VoteDO;
import com.llicursi.opto.domainobject.VoteIdentity;
import com.llicursi.opto.exception.InvalidDateRangeException;
import com.llicursi.opto.exception.InvalidVoteException;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    @Mock
    private VoteRepository mockVoteRepository;

    private SubjectServiceImpl subjectService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        subjectService = new SubjectServiceImpl(mockSubjectRepository, mockUserService, mockVoteRepository);
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

    @Test
    public void givenVote_whenValidNewVote_thenVoteSuccessfully() throws ResultNotFoundException, InvalidVoteException {

        SubjectDTO sourceObject = SubjectDTO.newBuilder()
                .setTitle("Topic 11")
                .setDescription(" Testing ValidVote")
                .setStart(Date.from(LocalDateTime.now().atZone(ZoneOffset.systemDefault()).toInstant()))
                .setDue(Date.from(LocalDateTime.now().plus(7, ChronoUnit.DAYS).atZone(ZoneOffset.systemDefault()).toInstant()))
                .createSubjectDTO();

        SubjectDO returnedSubjectDO = SubjectMapper.convertToSubjectDO(sourceObject);
        returnedSubjectDO.setId(12L);
        UserDO mockedUser = new UserDO("Test", "Teste", "teste@opto.com", "****", "USER");
        mockedUser.setId(4L);

        VoteIdentity voteIdentity = new VoteIdentity(mockedUser, returnedSubjectDO);

        VoteDO resultingVoteDO = new VoteDO(voteIdentity, true, 1, Date.from(LocalDateTime.now().atZone(ZoneOffset.systemDefault()).toInstant()));

        when(mockSubjectRepository.findById(any(Long.class))).thenReturn(Optional.of(returnedSubjectDO));
        when(mockSubjectRepository.findAllActive()).thenReturn(mockedSubjectList());
        when(mockUserService.findById(any(Long.class))).thenReturn(mockedUser);
        when(mockVoteRepository.save(any(VoteDO.class))).thenReturn(resultingVoteDO);

        subjectService.vote(4L, new VoteDTO(12L, true));

        assertThat(resultingVoteDO).isNotNull();
        assertThat(resultingVoteDO.getChanges()).isEqualTo(1);

        verify(mockSubjectRepository, times(1)).findById(any(Long.class));
        verify(mockSubjectRepository, times(2)).findAllActive();

        verify(mockUserService, times(1)).findById(any(Long.class));
        verifyNoMoreInteractions(mockUserService);

        verify(mockVoteRepository, times(1)).save(any(VoteDO.class));

    }

    @Test
    public void givenVote_whenValidUpdateVote_thenVoteSuccessfully() throws ResultNotFoundException, InvalidVoteException {

        SubjectDTO sourceObject = SubjectDTO.newBuilder()
                .setTitle("Topic 11")
                .setDescription(" Testing ValidVote")
                .setStart(Date.from(LocalDateTime.now().atZone(ZoneOffset.systemDefault()).toInstant()))
                .setDue(Date.from(LocalDateTime.now().plus(7, ChronoUnit.DAYS).atZone(ZoneOffset.systemDefault()).toInstant()))
                .createSubjectDTO();

        SubjectDO returnedSubjectDO = SubjectMapper.convertToSubjectDO(sourceObject);
        returnedSubjectDO.setId(12L);
        UserDO mockedUser = new UserDO("Test", "Teste", "teste@opto.com", "****", "USER");
        mockedUser.setId(4L);

        VoteIdentity voteIdentity = new VoteIdentity(mockedUser, returnedSubjectDO);

        VoteDO existentVode = new VoteDO(voteIdentity, true, 1, Date.from(LocalDateTime.now().atZone(ZoneOffset.systemDefault()).toInstant()));
        VoteDO resultingVoteDO = new VoteDO(voteIdentity, false, 2, Date.from(LocalDateTime.now().atZone(ZoneOffset.systemDefault()).toInstant()));

        when(mockSubjectRepository.findById(any(Long.class))).thenReturn(Optional.of(returnedSubjectDO));
        when(mockSubjectRepository.findAllActive()).thenReturn(mockedSubjectList());
        when(mockUserService.findById(any(Long.class))).thenReturn(mockedUser);

        when(mockVoteRepository.findById(any(VoteIdentity.class))).thenReturn(Optional.of(existentVode));
        when(mockVoteRepository.save(any(VoteDO.class))).thenReturn(resultingVoteDO);

        subjectService.vote(4L, new VoteDTO(12L, false));

        assertThat(resultingVoteDO).isNotNull();
        assertThat(resultingVoteDO.getChanges()).isEqualTo(2);

        verify(mockSubjectRepository, times(1)).findById(any(Long.class));
        verify(mockSubjectRepository, times(2)).findAllActive();

        verify(mockUserService, times(1)).findById(any(Long.class));
        verifyNoMoreInteractions(mockUserService);

        verify(mockVoteRepository, times(1)).save(any(VoteDO.class));

    }

    @Test
    public void givenVote_whenInvalidDuplicateOptionVote_thenThrowException() throws ResultNotFoundException {

        SubjectDTO sourceObject = SubjectDTO.newBuilder()
                .setTitle("Topic 11")
                .setDescription(" Testing ValidVote")
                .setStart(Date.from(LocalDateTime.now().atZone(ZoneOffset.systemDefault()).toInstant()))
                .setDue(Date.from(LocalDateTime.now().plus(7, ChronoUnit.DAYS).atZone(ZoneOffset.systemDefault()).toInstant()))
                .createSubjectDTO();

        SubjectDO returnedSubjectDO = SubjectMapper.convertToSubjectDO(sourceObject);
        returnedSubjectDO.setId(12L);
        UserDO mockedUser = new UserDO("Test", "Teste", "teste@opto.com", "****", "USER");
        mockedUser.setId(4L);

        VoteIdentity voteIdentity = new VoteIdentity(mockedUser, returnedSubjectDO);

        VoteDO existentVote = new VoteDO(voteIdentity, true, 1, Date.from(LocalDateTime.now().atZone(ZoneOffset.systemDefault()).toInstant()));

        when(mockSubjectRepository.findById(any(Long.class))).thenReturn(Optional.of(returnedSubjectDO));
        when(mockSubjectRepository.findAllActive()).thenReturn(mockedSubjectList());
        when(mockUserService.findById(any(Long.class))).thenReturn(mockedUser);

        when(mockVoteRepository.findById(any(VoteIdentity.class))).thenReturn(Optional.of(existentVote));

        try {
            subjectService.vote(4L, new VoteDTO(12L, true));
            fail("Test failed: must throw InvalidVoteException");
        } catch (InvalidVoteException e) {
        }

    }

    @Test
    public void givenVote_whenExceedNumberOfVote_thenThrowException() throws ResultNotFoundException {

        SubjectDTO sourceObject = SubjectDTO.newBuilder()
                .setTitle("Topic 11")
                .setDescription(" Testing ValidVote")
                .setStart(Date.from(LocalDateTime.now().atZone(ZoneOffset.systemDefault()).toInstant()))
                .setDue(Date.from(LocalDateTime.now().plus(7, ChronoUnit.DAYS).atZone(ZoneOffset.systemDefault()).toInstant()))
                .createSubjectDTO();

        SubjectDO returnedSubjectDO = SubjectMapper.convertToSubjectDO(sourceObject);
        returnedSubjectDO.setId(12L);
        UserDO mockedUser = new UserDO("Test", "Teste", "teste@opto.com", "****", "USER");
        mockedUser.setId(4L);

        VoteIdentity voteIdentity = new VoteIdentity(mockedUser, returnedSubjectDO);

        VoteDO existentVote = new VoteDO(voteIdentity, true, 2, Date.from(LocalDateTime.now().atZone(ZoneOffset.systemDefault()).toInstant()));

        when(mockSubjectRepository.findById(any(Long.class))).thenReturn(Optional.of(returnedSubjectDO));
        when(mockSubjectRepository.findAllActive()).thenReturn(mockedSubjectList());
        when(mockUserService.findById(any(Long.class))).thenReturn(mockedUser);

        when(mockVoteRepository.findById(any(VoteIdentity.class))).thenReturn(Optional.of(existentVote));

        try {
            subjectService.vote(4L, new VoteDTO(12L, false));
            fail("Test failed: must throw InvalidVoteException");
        } catch (InvalidVoteException e) {
        }

    }

    @Test
    public void givenVote_whenInactiveSubject_thenThrowException() throws ResultNotFoundException {

        SubjectDTO sourceObject = SubjectDTO.newBuilder()
                .setTitle("Topic 11")
                .setDescription(" Testing ValidVote")
                .setStart(Date.from(LocalDateTime.now().minus(20, ChronoUnit.DAYS).atZone(ZoneOffset.systemDefault()).toInstant()))
                .setDue(Date.from(LocalDateTime.now().minus(10, ChronoUnit.DAYS).atZone(ZoneOffset.systemDefault()).toInstant()))
                .createSubjectDTO();

        SubjectDO returnedSubjectDO = SubjectMapper.convertToSubjectDO(sourceObject);
        returnedSubjectDO.setId(12L);
        UserDO mockedUser = new UserDO("Test", "Teste", "teste@opto.com", "****", "USER");
        mockedUser.setId(4L);

        when(mockSubjectRepository.findById(any(Long.class))).thenReturn(Optional.of(returnedSubjectDO));
        when(mockSubjectRepository.findAllActive()).thenReturn(null);
        when(mockUserService.findById(any(Long.class))).thenReturn(mockedUser);

        try {
            subjectService.vote(4L, new VoteDTO(12L, true));
            fail("Test failed: must throw InvalidVoteException");
        } catch (InvalidVoteException e) {
        }

    }

    public List<SubjectDO> mockedSubjectList() {

        SubjectDTO subjectDTO = SubjectDTO.newBuilder()
                .setTitle("Topic 11")
                .setDescription(" Testing ValidVote")
                .setStart(Date.from(LocalDateTime.now().atZone(ZoneOffset.systemDefault()).toInstant()))
                .setDue(Date.from(LocalDateTime.now().plus(7, ChronoUnit.DAYS).atZone(ZoneOffset.systemDefault()).toInstant()))
                .createSubjectDTO();

        SubjectDO subjectDO = SubjectMapper.convertToSubjectDO(subjectDTO);
        subjectDO.setId(12L);

        List<SubjectDO> mockList = new ArrayList<>();
        mockList.add(subjectDO);

        return mockList;

    }
}