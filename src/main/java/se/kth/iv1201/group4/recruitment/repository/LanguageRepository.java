package se.kth.iv1201.group4.recruitment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import se.kth.iv1201.group4.recruitment.domain.Language;

/**
 * Repository for {@link se.kth.iv1201.group4.recruitment.domain.Language}.
 * Contains all methods for accessing data in the database concerning
 * {@link se.kth.iv1201.group4.recruitment.domain.Language}.
 * 
 * @author William Stackenäs
 * @version %I%
 */
@Transactional(propagation = Propagation.MANDATORY)
@Repository
public interface LanguageRepository extends JpaRepository<Language, Long> {

    /**
     * find a language by the given name.
     *
     * @param name  name used to find {@link Language}.
     * @return      {@link Language} returned by the given name.
     */
    Language findLanguageByName(String name);

    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    Language save(Language language);

    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    void delete(Language language);
}
