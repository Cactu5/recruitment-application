package se.kth.iv1201.group4.recruitment.domain;

import static org.hamcrest.Matchers.is;

import javax.validation.ConstraintViolationException;

import static org.hamcrest.MatcherAssert.assertThat;

import static se.kth.iv1201.group4.recruitment.domain.ConstrainValidationHelper.validateConstraints;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.fail;
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
        entityManager.persistAndFlush(ben);

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
            fail("ConstraintViolationException was not thrown.");
        } catch (ConstraintViolationException e) {
            validateConstraints(e, "{person.name.invalid-char}", "{person.name.length}");
        }
    }

    @Test
    public void testCreateInvalidSurname() {
        Person ben = new Person("Ben", "!", "ben.johnsson@gmail.com", "190607071234", "benjo", "password");
        try {
            entityManager.persistAndFlush(ben);
            fail("ConstraintViolationException was not thrown.");
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
            fail("ConstraintViolationException was not thrown.");
        } catch (ConstraintViolationException e) {
            validateConstraints(e, "{person.email.invalid}", "{person.email.length}");
        }
    }

    @Test
    public void testCreateInvalidSSN() {
        Person ben = new Person("Ben", "Johnsson", "ben.johnsson@gmail.com", "!", "benjo", "password");
        try {
            entityManager.persistAndFlush(ben);
            fail("ConstraintViolationException was not thrown.");
        } catch (ConstraintViolationException e) {
            validateConstraints(e, "{person.ssn.invalid-char}", "{person.ssn.length}");
        }
    }

    @Test
    public void testCreateInvalidUsername() {
        Person ben = new Person("Ben", "Johnsson", "ben.johnsson@gmail.com", "190607071234", "!", "password");
        try {
            entityManager.persistAndFlush(ben);
            fail("ConstraintViolationException was not thrown.");
        } catch (ConstraintViolationException e) {
            validateConstraints(e, "{person.username.invalid-char}", "{person.username.length}");
        }
    }
}
