package se.kth.iv1201.group4.recruitment.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import se.kth.iv1201.group4.recruitment.domain.Applicant;
import se.kth.iv1201.group4.recruitment.domain.Availability;
import se.kth.iv1201.group4.recruitment.domain.Competence;
import se.kth.iv1201.group4.recruitment.domain.CompetenceProfile;
import se.kth.iv1201.group4.recruitment.domain.JobApplication;
import se.kth.iv1201.group4.recruitment.domain.JobStatus;
import se.kth.iv1201.group4.recruitment.domain.Person;

@DataJpaTest
public class CompetenceProfileRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CompetenceProfileRepository competenceProfileRepository;

    @Test
    public void testCreateCompetenceProfile() {
        Person ben = new Person("Ben", "Johnsson", "ben.johnsson@gmail.com", "190607071234", "benjo", "password");
        entityManager.persist(ben);

        Applicant applicantBen = new Applicant(ben);
        entityManager.persist(applicantBen);

        JobStatus jobStatus = new JobStatus("test status");
        entityManager.persist(jobStatus);

        Competence competence = new Competence();
        Competence competence2 = new Competence();
        entityManager.persist(competence);
        entityManager.persist(competence2);

        Availability availability = new Availability(LocalDate.of(2021, 01, 01), LocalDate.of(2021, 01, 15));

        List<Availability> availabilites = new ArrayList<Availability>();
        availabilites.add(availability);

        CompetenceProfile competenceProfile = new CompetenceProfile(2.5f, competence);

        List<CompetenceProfile> competenceProfiles = new ArrayList<CompetenceProfile>();
        competenceProfiles.add(competenceProfile);

        JobApplication jobApplication = new JobApplication(applicantBen, jobStatus, competenceProfiles, availabilites);
        entityManager.persist(jobApplication);

        availability.setJobApplication(jobApplication);
        entityManager.persist(availability);

        competenceProfile.setJobApplication(jobApplication);
        entityManager.persist(competenceProfile);

        entityManager.flush();

        List<CompetenceProfile> found = competenceProfileRepository.findAll();
        assertThat(found.size(), is(1));
        assertThat(found.get(0), is(competenceProfile));
        assertThat(competenceProfile.getYearsOfExperience(), is(found.get(0).getYearsOfExperience()));
        assertThat(competenceProfile.getCompetence(), is(found.get(0).getCompetence()));
        assertThat(jobApplication, is(found.get(0).getJobApplication()));
    }
}
