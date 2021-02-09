package se.kth.iv1201.group4.recruitment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import se.kth.iv1201.group4.recruitment.domain.JobApplication;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    @Override
    JobApplication save(JobApplication jobApplication);

    @Override
    void delete(JobApplication jobApplication);
}
