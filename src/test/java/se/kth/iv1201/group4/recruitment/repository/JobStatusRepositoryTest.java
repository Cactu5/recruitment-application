package se.kth.iv1201.group4.recruitment.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import se.kth.iv1201.group4.recruitment.domain.JobStatus;

@DataJpaTest
public class JobStatusRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private JobStatusRepository jobStatusRepository;

    @Test
    public void testCreateJobStatus() {
        JobStatus jobStatus = new JobStatus("test status");
        entityManager.persist(jobStatus);
        entityManager.flush();

        List<JobStatus> found = jobStatusRepository.findAll();
        assertThat(found.size(), is(1));
        assertThat(found.get(0), is(jobStatus));
        assertThat(jobStatus.getName(), is(found.get(0).getName()));
    }
}