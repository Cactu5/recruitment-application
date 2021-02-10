package se.kth.iv1201.group4.recruitment.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import se.kth.iv1201.group4.recruitment.domain.Competence;
import se.kth.iv1201.group4.recruitment.domain.CompetenceProfile;

@DataJpaTest
public class CompetenceProfileRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CompetenceProfileRepository competenceProfileRepository;

    @Test
    public void testCreateCompetenceProfile() {
        Competence competence = new Competence("test competence1");
        entityManager.persist(competence);

        CompetenceProfile competenceProfile = new CompetenceProfile(2.5f, competence);
        entityManager.persist(competenceProfile);
        entityManager.flush();

        List<CompetenceProfile> found = competenceProfileRepository.findAll();
        assertThat(found.size(), is(1));
        assertThat(found.get(0), is(competenceProfile));
        assertThat(competenceProfile.getYearsOfExperience(), is(found.get(0).getYearsOfExperience()));
        assertThat(competenceProfile.getCompetence(), is(found.get(0).getCompetence()));
    }
}
