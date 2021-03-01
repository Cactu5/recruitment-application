package se.kth.iv1201.group4.recruitment.domain;

import static org.hamcrest.Matchers.is;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

import static se.kth.iv1201.group4.recruitment.domain.ConstrainValidationHelper.testConstraintViolation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestExecutionListener;

@DataJpaTest
public class AvailabilityTest implements TestExecutionListener {
    @Autowired
    private TestEntityManager entityManager;

    private JobApplication jobApplication;

    @BeforeEach
    public void setup() {
        Person ben = new Person("Ben", "Johnsson", "ben.johnsson@gmail.com", "190607071234", "benjo", "password");
        entityManager.persist(ben);

        Applicant applicantBen = new Applicant(ben);
        entityManager.persist(applicantBen);

        JobStatus jobStatus = new JobStatus("test status");
        entityManager.persist(jobStatus);

        Competence competence = new Competence("test competence1");
        entityManager.persist(competence);

        Availability availability = new Availability(LocalDate.of(2021, 01, 01), LocalDate.of(2021, 01, 15));

        List<Availability> availabilites = new ArrayList<Availability>();
        availabilites.add(availability);

        CompetenceProfile competenceProfile = new CompetenceProfile(2.5f, competence);

        List<CompetenceProfile> competenceProfiles = new ArrayList<CompetenceProfile>();
        competenceProfiles.add(competenceProfile);

        jobApplication = new JobApplication(applicantBen, jobStatus, competenceProfiles, availabilites);
        jobApplication = entityManager.persist(jobApplication);

        availability.setJobApplication(jobApplication);
        entityManager.persist(availability);

        competenceProfile.setJobApplication(jobApplication);
        entityManager.persist(competenceProfile);

        entityManager.flush();
    }

    @Test
    public void testCreateAvailability() {
        Availability availability = new Availability(LocalDate.of(2021, 01, 01), LocalDate.of(2021, 01, 15),
                jobApplication);
        Availability availabilityAfterPersist = entityManager.persistAndFlush(availability);

        assertThat(availabilityAfterPersist, is(availability));
        assertThat(availabilityAfterPersist.getFrom(), is(availability.getFrom()));
        assertThat(availabilityAfterPersist.getTo(), is(availability.getTo()));
        assertThat(availabilityAfterPersist.getJobApplication(), is(availability.getJobApplication()));
    }

    @Test
    public void testMissingJobApplication() {
        Availability availability = new Availability(LocalDate.of(2021, 01, 01), LocalDate.of(2021, 01, 15));
        testConstraintViolation(entityManager, availability, "{availability.jobApplication.missing}");
    }
}
