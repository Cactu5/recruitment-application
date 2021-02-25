package se.kth.iv1201.group4.recruitment.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import se.kth.iv1201.group4.recruitment.domain.LegacyUser;
import se.kth.iv1201.group4.recruitment.domain.Person;

@DataJpaTest
public class LegacyUserRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private LegacyUserRepository legacyUserRepository;

    @Test
    public void testCreateLegacyUser() {
        Person ben = new Person("Ben", "Johnsson", "ben.johnsson@gmail.com", "190607071234", "benjo", "password");
        entityManager.persist(ben);
        entityManager.flush();

        LegacyUser legacyBen = new LegacyUser(ben);
        entityManager.persist(legacyBen);
        entityManager.flush();

        List<LegacyUser> legacyUsers = legacyUserRepository.findAll();
        assertThat(legacyUsers.size(), is(1));
        assertThat(legacyUsers.get(0), is(legacyBen));
    }

    @Test
    public void testFindLegacyUserByPerson() {
        Person ben = new Person("Ben", "Johnsson", "ben.johnsson@gmail.com", "190607071234", "benjo", "password");
        entityManager.persist(ben);
        entityManager.flush();

        LegacyUser legacyBen = new LegacyUser(ben);
        entityManager.persist(legacyBen);
        entityManager.flush();


        LegacyUser found = legacyUserRepository.findLegacyUserByPerson(ben);
        assertThat(found, is(legacyBen));
    }
}
