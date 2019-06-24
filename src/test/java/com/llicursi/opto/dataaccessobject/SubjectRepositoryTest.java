package com.llicursi.opto.dataaccessobject;

import com.llicursi.opto.domainobject.SubjectDO;
import com.llicursi.opto.exception.ResultNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class SubjectRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SubjectRepository subjectRepository;

    @Test
    public void givenFindById_whenHasVotes_thenReturnSubjectWithCount() throws ResultNotFoundException {

        SubjectDO subject = subjectRepository.findById(1L).orElseThrow(() -> new ResultNotFoundException(""));
        assertThat(subject).isNotNull();
        assertThat(subject.getVotes()).isEqualTo(3);

    }



}