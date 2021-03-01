package se.kth.iv1201.group4.recruitment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import se.kth.iv1201.group4.recruitment.domain.Competence;
import se.kth.iv1201.group4.recruitment.domain.Language;
import se.kth.iv1201.group4.recruitment.domain.LocalCompetence;

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
public interface LocalCompetenceRepository extends JpaRepository<LocalCompetence, Long> {
    /**
     * Returns LocalCompetence specified by the given name. 
     *
     * @param name  name of the given {@link LocalCompetence} to return
     * @return      {@link LocalCompetence} found given the name.
     */
    LocalCompetence findLocalCompetenceByName(String name);

    /**
     * Returns LocalCompetence specified by the given name. 
     *
     * @param l     language of the given {@link LocalCompetence} to return
     * @param c     competence of the given {@link LocalCompetence} to return
     * @return      {@link LocalCompetence} found given the language and competence.
     */
    LocalCompetence findLocalCompetenceByLanguageAndCompetence(Language l, Competence c);


    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    LocalCompetence save(LocalCompetence competence);

    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    void delete(LocalCompetence competence);
}
