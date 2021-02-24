package se.kth.iv1201.group4.recruitment.domain;

import static org.hamcrest.Matchers.is;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.CoreMatchers.hasItem;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestExecutionListener;

import se.kth.iv1201.group4.recruitment.repository.PersonRepository;

@DataJpaTest
public class PersonTest implements TestExecutionListener {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PersonRepository personRepository;

    @Test
    public void testCreatePerson() {
        Person ben = new Person("Ben", "Johnsson", "ben.johnsson@gmail.com", "190607071234", "benjo", "password");
        entityManager.persist(ben);
        entityManager.flush();

        Person person = personRepository.findPersonByUsername(ben.getUsername());
        assertThat(person, is(ben));
        assertThat(person.getEmail(), is(ben.getEmail()));
        assertThat(person.getName(), is(ben.getName()));
        assertThat(person.getSSN(), is(ben.getSSN()));
        assertThat(person.getSurname(), is(ben.getSurname()));
        assertThat(person.getUsername(), is(ben.getUsername()));
    }

    @Test
    public void testCreateInvalidName() {
        Person ben = new Person("!", "Johnsson", "ben.johnsson@gmail.com", "190607071234", "benjo", "password");
        try {
            entityManager.persistAndFlush(ben);
        } catch (ConstraintViolationException e) {
            validateConstraints(e, "{person.name.invalid-char}", "{person.name.length}");
        }
    }

    @Test
    public void testCreateInvalidSurname() {
        Person ben = new Person("Ben", "!", "ben.johnsson@gmail.com", "190607071234", "benjo", "password");
        try {
            entityManager.persistAndFlush(ben);
        } catch (ConstraintViolationException e) {
            validateConstraints(e, "{person.surname.invalid-char}", "{person.surname.length}");
        }
    }

    @Test
    public void testCreateInvalidEmail() {
        Person ben = new Person("Ben", "Johnsson", "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!",
                "190607071234", "benjo", "password");
        try {
            entityManager.persistAndFlush(ben);
        } catch (ConstraintViolationException e) {
            validateConstraints(e, "{person.email.invalid}", "{person.email.length}");
        }
    }

    @Test
    public void testCreateInvalidSSN() {
        Person ben = new Person("Ben", "Johnsson", "ben.johnsson@gmail.com", "!", "benjo", "password");
        try {
            entityManager.persistAndFlush(ben);
        } catch (ConstraintViolationException e) {
            validateConstraints(e, "{person.ssn.invalid-char}", "{person.ssn.length}");
        }
    }

    @Test
    public void testCreateInvalidUsername() {
        Person ben = new Person("Ben", "Johnsson", "ben.johnsson@gmail.com", "190607071234", "!", "password");
        try {
            entityManager.persistAndFlush(ben);
        } catch (ConstraintViolationException e) {
            validateConstraints(e, "{person.username.invalid-char}", "{person.username.length}");
        }
    }

    // Original from Leif Lindbäck
    // https://github.com/KTH-IV1201/bank/blob/15c1e687c76e7ccc203ef998f7e2584f28877e0b/src/test/java/se/kth/iv1201/appserv/bank/domain/AccountTest.java#L143
    private void validateConstraints(ConstraintViolationException e, String... expectedContraints) {
        Set<ConstraintViolation<?>> result = e.getConstraintViolations();
        assertThat(result.size(), is(expectedContraints.length));
        for (String expectedContraint : expectedContraints) {
            assertThat(result, hasItem(hasProperty("messageTemplate", equalTo(expectedContraint))));
        }
    }
}
