package se.kth.iv1201.group4.recruitment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import se.kth.iv1201.group4.recruitment.domain.Applicant;

@Repository
public interface ApplicantRepository extends JpaRepository<Applicant, Long> {
    @Override
    Applicant save(Applicant applicant);

    @Override
    void delete(Applicant applicant);
}
