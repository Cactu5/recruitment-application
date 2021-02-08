package se.kth.iv1201.group4.recruitment.Repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import se.kth.iv1201.group4.recruitment.domain.Person;
import se.kth.iv1201.group4.recruitment.domain.Recruiter;
import se.kth.iv1201.group4.recruitment.repository.RecruiterRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RecruiterRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RecruiterRepository recruiterRepository;

    @Test
    public void testCreateRecruiter() {
        Person ben = new Person("Ben", "Johnsson", "ben.johnsson@gmail.com", "190607071234", "benjo", "password");
        entityManager.persist(ben);
        entityManager.flush();

        Recruiter recruiterBen = new Recruiter(ben);
        entityManager.persist(recruiterBen);
        entityManager.flush();

        List<Recruiter> persons = recruiterRepository.findAll();
        assertThat(persons.size(), is(1));
        assertThat(persons.get(0), is(recruiterBen));
    }

    @Test
    public void testFindRecruiterByPerson() {
        Person ben = new Person("Ben", "Johnsson", "ben.johnsson@gmail.com", "190607071234", "benjo", "password");
        entityManager.persist(ben);
        entityManager.flush();

        Recruiter recruiterBen = new Recruiter(ben);
        entityManager.persist(recruiterBen);
        entityManager.flush();

        Recruiter found = recruiterRepository.findRecruiterByPerson(ben);
        assertThat(found, is(recruiterBen));
    }
}
