package se.kth.iv1201.group4.recruitment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import se.kth.iv1201.group4.recruitment.domain.JobStatus;

/**
 * Repository for JobStatus. Contains all methods for accessing data in the
 * database concerning JobStatus.
 * 
 * @author Cactu5
 */
@Transactional(propagation = Propagation.MANDATORY)
@Repository
public interface JobStatusRepository extends JpaRepository<JobStatus, Long> {
    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    JobStatus save(JobStatus jobStatus);

    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    void delete(JobStatus jobStatus);
}
