package se.kth.iv1201.group4.recruitment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import se.kth.iv1201.group4.recruitment.domain.Competence;

@Repository
public interface CompetenceRepository extends JpaRepository<Competence, Long> {
    @Override
    Competence save(Competence competence);

    @Override
    void delete(Competence competence);
}
