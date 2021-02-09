package se.kth.iv1201.group4.recruitment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import se.kth.iv1201.group4.recruitment.domain.CompetenceProfile;

@Repository
public interface CompetenceProfileRepository extends JpaRepository<CompetenceProfile, Long> {
    @Override
    CompetenceProfile save(CompetenceProfile competenceProfile);

    @Override
    void delete(CompetenceProfile competenceProfile);
}
