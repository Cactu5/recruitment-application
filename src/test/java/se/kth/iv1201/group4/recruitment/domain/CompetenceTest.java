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

import se.kth.iv1201.group4.recruitment.repository.LocalCompetenceRepository;

@DataJpaTest
public class CompetenceTest implements TestExecutionListener {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private LocalCompetenceRepository localCompetenceRepository;

    @Test
    public void testCreateLocalCompetence() {
        Language lang = new Language("english");
        entityManager.persist(lang);

        Competence competence = new Competence();
        entityManager.persist(competence);

        LocalCompetence lCompetence = new LocalCompetence("competence", lang, competence);
        entityManager.persistAndFlush(lCompetence);

        List<LocalCompetence> found = localCompetenceRepository.findAll();
        assertThat(found.size(), is(1));
        assertThat(found.get(0), is(lCompetence));
        assertThat(lCompetence.getName(), is(found.get(0).getName()));
    }

    @Test
    public void testInvalidName() {
        Language lang = new Language("english");
        entityManager.persist(lang);

        Competence competence = new Competence();
        entityManager.persist(competence);

        LocalCompetence lCompetence = new LocalCompetence("!", lang, competence);
        entityManager.persistAndFlush(competence);

        try {
            entityManager.persistAndFlush(lCompetence);
            fail("ConstraintViolationException was not thrown.");
        } catch (ConstraintViolationException e) {
            validateConstraints(e, "{competence.name.length}");
        }
    }
}
