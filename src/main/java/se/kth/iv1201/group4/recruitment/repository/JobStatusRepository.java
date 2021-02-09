package se.kth.iv1201.group4.recruitment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import se.kth.iv1201.group4.recruitment.domain.JobStatus;

@Repository
public interface JobStatusRepository extends JpaRepository<JobStatus, Long> {
    @Override
    JobStatus save(JobStatus jobStatus);

    @Override
    void delete(JobStatus jobStatus);
}
