package se.kth.iv1201.group4.recruitment.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import se.kth.iv1201.group4.recruitment.domain.Language;

@DataJpaTest
public class LanguageRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private LanguageRepository languageRepository;

    @Test
    public void testCreateJobStatus() {
        Language lang = new Language("swedish");
        entityManager.persist(lang);
        entityManager.flush();

        List<Language> found = languageRepository.findAll();
        assertThat(found.size(), is(1));
        assertThat(found.get(0), is(lang));
        assertThat(lang.getName(), is(found.get(0).getName()));
    }
}