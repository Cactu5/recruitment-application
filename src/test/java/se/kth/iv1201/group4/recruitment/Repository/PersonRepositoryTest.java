package se.kth.iv1201.group4.recruitment.Repository;

import static org.hamcrest.Matchers.is;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import se.kth.iv1201.group4.recruitment.domain.Person;
import se.kth.iv1201.group4.recruitment.repository.PersonRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PersonRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PersonRepository personRepository;

    @Test
    public void testCreatePerson() {
        Person ben = new Person("Ben", "Johnsson", "ben.johnsson@gmail.com", "190607071234", "benjo", "password");
        entityManager.persist(ben);
        entityManager.flush();

        List<Person> persons = personRepository.findAll();
        assertThat(persons.size(), is(1));
        assertThat(persons.get(0), is(ben));
    }

    @Test
    public void testFindByUsername() {
        Person ben = new Person("Ben", "Johnsson", "ben.johnsson@gmail.com", "190607071234", "benjo", "password");
        entityManager.persist(ben);
        entityManager.flush();

        Person found = personRepository.findPersonByUsername(ben.getUsername());
        assertThat(found.getUsername(), is(ben.getUsername()));
    }
}
