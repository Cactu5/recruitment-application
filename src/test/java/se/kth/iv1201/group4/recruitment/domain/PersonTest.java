package se.kth.iv1201.group4.recruitment.domain;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
public class PersonTest {
    @Autowired
    private TestEntityManager entityManager;

    private Person ben;

    @BeforeEach
    public void setUp() {
        ben = new Person("Ben", "Johnsson", "ben.johnsson@gmail.com", "190607071234", "benjo", "password");
    }

    @Test
    public void testSavePerson() {
        Person savedPerson = entityManager.persistAndFlush(ben);
        assertThat(savedPerson, is(ben));
    }
}
