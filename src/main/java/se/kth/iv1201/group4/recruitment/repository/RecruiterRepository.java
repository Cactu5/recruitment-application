package se.kth.iv1201.group4.recruitment.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import se.kth.iv1201.group4.recruitment.domain.Recruiter;

@Repository
public interface RecruiterRepository extends JpaRepository<Recruiter, Long> {
    @Override
    Recruiter save(Recruiter recruiter);

    @Override
    void delete(Recruiter recruiter);
}
