package se.kth.iv1201.group4.recruitment.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import se.kth.iv1201.group4.recruitment.domain.Applicant;
import se.kth.iv1201.group4.recruitment.domain.Person;


@DataJpaTest
public class ApplicantRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ApplicantRepository applicantRepository;

    @Test
    public void testCreateApplicant() {
        Person ben = new Person("Ben", "Johnsson", "ben.johnsson@gmail.com", "190607071234", "benjo", "password");
        entityManager.persist(ben);
        entityManager.flush();

        Applicant applicantBen = new Applicant(ben);
        entityManager.persist(applicantBen);
        entityManager.flush();

        List<Applicant> persons = applicantRepository.findAll();
        assertThat(persons.size(), is(1));
        assertThat(persons.get(0), is(applicantBen));
    }

    @Test
    public void testFindRecruiterByPerson() {
        Person ben = new Person("Ben", "Johnsson", "ben.johnsson@gmail.com", "190607071234", "benjo", "password");
        entityManager.persist(ben);
        entityManager.flush();

        Applicant applicantBen = new Applicant(ben);
        entityManager.persist(applicantBen);
        entityManager.flush();

        Applicant found = applicantRepository.findApplicantByPerson(ben);
        assertThat(found, is(applicantBen));
    }
}
