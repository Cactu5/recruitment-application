package se.kth.iv1201.group4.recruitment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import se.kth.iv1201.group4.recruitment.domain.Competence;
import se.kth.iv1201.group4.recruitment.domain.Language;
import se.kth.iv1201.group4.recruitment.domain.LocalCompetence;
import se.kth.iv1201.group4.recruitment.domain.LocalCompetenceId;

/**
 * Repository for {@link se.kth.iv1201.group4.recruitment.domain.LocalCompetence}.
 * Contains all methods for accessing data in the database concerning
 * {@link se.kth.iv1201.group4.recruitment.domain.LocalCompetence}.
 * 
 * @author William Stackenäs
 * @version %I%
 */
@Transactional(propagation = Propagation.MANDATORY)
@Repository
public interface LocalCompetenceRepository extends JpaRepository<LocalCompetence, LocalCompetenceId> {
    /**
     * Returns LocalCompetence specified by the given name. 
     *
     * @param name  name of the given {@link LocalCompetence} to return
     * @return      {@link LocalCompetence}s found given the name.
     */
    List<LocalCompetence> findLocalCompetenceByName(String name);

    /**
     * Returns LocalCompetence specified by the given name. 
     *
     * @param l     language of the given {@link LocalCompetence} to return
     * @param c     competence of the given {@link LocalCompetence} to return
     * @return      {@link LocalCompetence} found given the language and competence.
     */
    LocalCompetence findLocalCompetenceByLanguageAndCompetence(Language l, Competence c);

    /**
     * Returns a list of all competences in the given language
     * 
     * @param l     language of the given {@link LocalCompetence}s to return
     * @return      a list of {@link LocalCompetence}s found given the language.
     */
    List<LocalCompetence> getLocalCompetenceByLanguage(Language l);


    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    LocalCompetence save(LocalCompetence competence);

    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    void delete(LocalCompetence competence);
}
