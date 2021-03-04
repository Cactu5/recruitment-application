package se.kth.iv1201.group4.recruitment.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestExecutionListener;

@DataJpaTest
public class JobApplicationTest implements TestExecutionListener {
    @Autowired
    private TestEntityManager entityManager;

    private Person person;
    private Applicant applicant;
    private JobStatus jobStatus;

    @BeforeEach
    public void setup() {
        person = new Person("Ben", "Johnsson", "ben.johnsson@gmail.com", "190607071234", "benjo", "password");
        entityManager.persist(person);

        applicant = new Applicant(person);
        entityManager.persist(applicant);

        jobStatus = new JobStatus("test status");
        entityManager.persist(jobStatus);

        entityManager.flush();
    }

    @Test
    public void testCreateJobApplication() {
        try {
            Competence competence = new Competence("test competence1");
            Competence competence2 = new Competence("test competence2");
            entityManager.persist(competence);
            entityManager.persist(competence2);

            Availability availability = new Availability(LocalDate.of(2021, 01, 01), LocalDate.of(2021, 01, 15));
            Availability availability2 = new Availability(LocalDate.of(2021, 02, 07), LocalDate.of(2021, 03, 20));

            List<Availability> availabilites = new ArrayList<Availability>();
            availabilites.add(availability);
            availabilites.add(availability2);

            CompetenceProfile competenceProfile = new CompetenceProfile(2.5f, competence);
            CompetenceProfile competenceProfile2 = new CompetenceProfile(2.5f, competence2);

            List<CompetenceProfile> competenceProfiles = new ArrayList<CompetenceProfile>();
            competenceProfiles.add(competenceProfile);
            competenceProfiles.add(competenceProfile2);

            JobApplication jobApplication = new JobApplication(applicant, jobStatus, competenceProfiles, availabilites);
            entityManager.persist(jobApplication);

            availability.setJobApplication(jobApplication);
            availability2.setJobApplication(jobApplication);
            entityManager.persist(availability);
            entityManager.persist(availability2);

            competenceProfile.setJobApplication(jobApplication);
            competenceProfile2.setJobApplication(jobApplication);
            entityManager.persist(competenceProfile);
            entityManager.persist(competenceProfile2);

            entityManager.flush();
        } catch (Exception e) {
            fail("An exception was thrown: " + e.getMessage());
        }
    }
}
