package se.kth.iv1201.group4.recruitment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import se.kth.iv1201.group4.recruitment.domain.JobApplication;

/**
 * Repository for
 * {@link se.kth.iv1201.group4.recruitment.domain.JobApplication}. Contains all
 * methods for accessing data in the database concerning
 * {@link se.kth.iv1201.group4.recruitment.domain.JobApplication}.
 * 
 * @author Cactu5
 */
@Transactional(propagation = Propagation.MANDATORY)
@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    JobApplication save(JobApplication jobApplication);

    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    void delete(JobApplication jobApplication);
}
