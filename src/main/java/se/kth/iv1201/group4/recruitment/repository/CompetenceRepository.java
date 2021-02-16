package se.kth.iv1201.group4.recruitment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import se.kth.iv1201.group4.recruitment.domain.Competence;

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
