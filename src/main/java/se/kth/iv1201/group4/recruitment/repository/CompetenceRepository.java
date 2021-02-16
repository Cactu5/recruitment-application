package se.kth.iv1201.group4.recruitment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import se.kth.iv1201.group4.recruitment.domain.Competence;

/**
 * Repository for {@link se.kth.iv1201.group4.recruitment.domain.Competence}.
 * Contains all methods for accessing data in the database concerning
 * {@link se.kth.iv1201.group4.recruitment.domain.Competence}.
 * 
 * @author Cactu5
 * @version %I%
 */
@Transactional(propagation = Propagation.MANDATORY)
@Repository
public interface CompetenceRepository extends JpaRepository<Competence, Long> {
    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    Competence save(Competence competence);

    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    void delete(Competence competence);
}
