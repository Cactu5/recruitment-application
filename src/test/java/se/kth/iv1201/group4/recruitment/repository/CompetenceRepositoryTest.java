package se.kth.iv1201.group4.recruitment.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import se.kth.iv1201.group4.recruitment.domain.Competence;
import se.kth.iv1201.group4.recruitment.domain.Language;

@DataJpaTest
public class CompetenceRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CompetenceRepository competenceRepository;

    @Test
    public void testCreateCompetence() {
        Language lang = new Language("english");
        entityManager.persist(lang);

        Competence competence = new Competence("test competence1", lang);
        entityManager.persist(competence);
        entityManager.flush();

        List<Competence> found = competenceRepository.findAll();
        assertThat(found.size(), is(1));
        assertThat(found.get(0), is(competence));
        assertThat(competence.getName(), is(found.get(0).getName()));
        assertThat(lang, is(found.get(0).getLanguage()));
    }
}
