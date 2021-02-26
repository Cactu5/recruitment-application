package se.kth.iv1201.group4.recruitment.domain;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolationException;

import static org.hamcrest.MatcherAssert.assertThat;

import static se.kth.iv1201.group4.recruitment.domain.ConstrainValidationHelper.validateConstraints;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestExecutionListener;

import se.kth.iv1201.group4.recruitment.repository.CompetenceProfileRepository;

@DataJpaTest
public class CompetenceProfileTest implements TestExecutionListener {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CompetenceProfileRepository competenceProfileRepository;

    private JobApplication jobApplication;

    @BeforeEach
    public void setup() throws Exception {
        Person ben = new Person("Ben", "Johnsson", "ben.johnsson@gmail.com", "190607071234", "benjo", "password");
        entityManager.persist(ben);

        Applicant applicantBen = new Applicant(ben);
        entityManager.persist(applicantBen);

        JobStatus jobStatus = new JobStatus("test status");
        entityManager.persist(jobStatus);

        Competence competence = new Competence("test competence1");
        Competence competence2 = new Competence("test competence2");
        entityManager.persist(competence);
        entityManager.persist(competence2);

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
    public void testCreateCompetence() {
        Competence competence = new Competence("test competence0");
        entityManager.persistAndFlush(competence);
        CompetenceProfile competenceProfile = new CompetenceProfile(2.5f, competence, jobApplication);
        CompetenceProfile competenceProfileAfterPersist = entityManager.persistAndFlush(competenceProfile);

        assertThat(competenceProfileAfterPersist, is(competenceProfile));
        assertThat(competenceProfileAfterPersist.getYearsOfExperience(), is(competenceProfile.getYearsOfExperience()));
        assertThat(competenceProfileAfterPersist.getCompetence(), is(competenceProfile.getCompetence()));
        assertThat(competenceProfileAfterPersist.getJobApplication(), is(competenceProfile.getJobApplication()));
    }

    @Test
    public void testInvalidYearsOfExperience() {
        Competence competence = new Competence("test competence0");
        entityManager.persistAndFlush(competence);
        CompetenceProfile competenceProfile = new CompetenceProfile(-2.5f, competence, jobApplication);

        try {
            entityManager.persistAndFlush(competence);
            entityManager.persistAndFlush(competenceProfile);
            fail("ConstraintViolationException was not thrown.");
        } catch (ConstraintViolationException e) {
            validateConstraints(e, "{competenceProfile.yearsOfExperience.lessThanZero}");
        }
    }

    @Test
    public void testMissingJobApplication() {
        Competence competence = new Competence("test competence0");
        entityManager.persistAndFlush(competence);
        CompetenceProfile competenceProfile = new CompetenceProfile(2.5f, competence);

        try {
            entityManager.persistAndFlush(competence);
            entityManager.persistAndFlush(competenceProfile);
            fail("ConstraintViolationException was not thrown.");
        } catch (ConstraintViolationException e) {
            validateConstraints(e, "{competenceProfile.jobApplication.missing}");
        }
    }

    @Test
    public void testMissingCompetence() {
        Competence competence = new Competence("test competence0");
        entityManager.persistAndFlush(competence);
        CompetenceProfile competenceProfile = new CompetenceProfile(2.5f, null, jobApplication);

        try {
            entityManager.persistAndFlush(competence);
            entityManager.persistAndFlush(competenceProfile);
            fail("ConstraintViolationException was not thrown.");
        } catch (ConstraintViolationException e) {
            validateConstraints(e, "{competenceProfile.competence.missing}");
        }
    }
}
