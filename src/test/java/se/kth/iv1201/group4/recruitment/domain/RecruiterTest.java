package se.kth.iv1201.group4.recruitment.domain;

import static org.hamcrest.Matchers.is;

import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestExecutionListener;

@DataJpaTest
public class RecruiterTest implements TestExecutionListener {
    @Autowired
    private TestEntityManager entityManager;

    private Person person;

    @BeforeEach
    public void setup() {
        person = new Person("Ben", "Johnsson", "ben.johnsson@gmail.com", "190607071234", "benjo", "password");
        entityManager.persistAndFlush(person);
    }

    @Test
    public void testCreateRecruiter() {
        Recruiter recruiter = new Recruiter(person);
        Recruiter recruiterAfterPersist = entityManager.persistAndFlush(recruiter);

        assertThat(recruiterAfterPersist, is(recruiter));
    }
}
