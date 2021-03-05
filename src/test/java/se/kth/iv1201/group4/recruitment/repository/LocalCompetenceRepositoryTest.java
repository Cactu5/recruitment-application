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
import se.kth.iv1201.group4.recruitment.domain.LocalCompetence;

@DataJpaTest
public class LocalCompetenceRepositoryTest {
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

        LocalCompetence lComp = new LocalCompetence("test competence1", lang, competence);
        entityManager.persist(lComp);
        entityManager.flush();

        List<LocalCompetence> found = localCompetenceRepository.findAll();
        assertThat(found.size(), is(1));
        assertThat(found.get(0), is(lComp));

        assertThat(lComp.getName(), is(found.get(0).getName()));
        assertThat(lComp.getLanguage(), is(found.get(0).getLanguage()));
        assertThat(lComp.getCompetence(), is(found.get(0).getCompetence()));
    }
}
