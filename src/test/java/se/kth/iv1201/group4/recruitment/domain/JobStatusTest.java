package se.kth.iv1201.group4.recruitment.domain;

import static org.hamcrest.Matchers.is;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

import static se.kth.iv1201.group4.recruitment.domain.ConstrainValidationHelper.testConstraintViolation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestExecutionListener;

import se.kth.iv1201.group4.recruitment.repository.JobStatusRepository;

@DataJpaTest
public class JobStatusTest implements TestExecutionListener {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private JobStatusRepository jobStatusRepository;

    @Test
    public void testCreateJobStatus() {
        JobStatus jobStatus = new JobStatus("test status");
        entityManager.persistAndFlush(jobStatus);

        List<JobStatus> found = jobStatusRepository.findAll();
        assertThat(found.size(), is(1));
        assertThat(found.get(0), is(jobStatus));
        assertThat(jobStatus.getName(), is(found.get(0).getName()));
    }

    @Test
    public void testInvalidNameLenght() {
        JobStatus jobStatus = new JobStatus("!");

        testConstraintViolation(entityManager, jobStatus, "{jobStatus.name.length}");
    }
}
