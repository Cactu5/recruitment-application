package se.kth.iv1201.group4.recruitment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import se.kth.iv1201.group4.recruitment.domain.CompetenceProfile;

/**
 * Repository for
 * {@link se.kth.iv1201.group4.recruitment.domain.CompetenceProfile}. Contains
 * all methods for accessing data in the database concerning
 * {@link se.kth.iv1201.group4.recruitment.domain.CompetenceProfile}.
 * 
 * @author Cactu5
 * @version %I%
 */
@Transactional(propagation = Propagation.MANDATORY)
@Repository
public interface CompetenceProfileRepository extends JpaRepository<CompetenceProfile, Long> {
    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    CompetenceProfile save(CompetenceProfile competenceProfile);

    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    void delete(CompetenceProfile competenceProfile);
}
