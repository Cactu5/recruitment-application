package se.kth.iv1201.group4.recruitment.domain;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import javax.validation.ConstraintViolationException;

import static org.hamcrest.MatcherAssert.assertThat;

import static se.kth.iv1201.group4.recruitment.domain.ConstrainValidationHelper.validateConstraints;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestExecutionListener;

import se.kth.iv1201.group4.recruitment.repository.CompetenceRepository;

@DataJpaTest
public class CompetenceTest implements TestExecutionListener {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CompetenceRepository competenceRepository;

    @Test
    public void testCreateCompetence() {
        Competence competence = new Competence("test competence1");
        entityManager.persistAndFlush(competence);

        List<Competence> found = competenceRepository.findAll();
        assertThat(found.size(), is(1));
        assertThat(found.get(0), is(competence));
        assertThat(competence.getName(), is(found.get(0).getName()));
    }

    @Test
    public void testInvalidName() {
        Competence competence = new Competence("!");

        try {
            entityManager.persistAndFlush(competence);
            fail("ConstraintViolationException was not thrown.");
        } catch (ConstraintViolationException e) {
            validateConstraints(e, "{competence.name.length}");
        }
    }
}
