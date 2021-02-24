package se.kth.iv1201.group4.recruitment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import se.kth.iv1201.group4.recruitment.domain.Applicant;
import se.kth.iv1201.group4.recruitment.domain.JobApplication;

/**
 * Repository for
 * {@link se.kth.iv1201.group4.recruitment.domain.JobApplication}. Contains all
 * methods for accessing data in the database concerning
 * {@link se.kth.iv1201.group4.recruitment.domain.JobApplication}.
 * 
 * @author Cactu5
 * @author Filip Garamvölgyi
 * @version %I%
 */
@Transactional(propagation = Propagation.MANDATORY)
@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    /**
     * Finds job applicaitons from the given applicant.
     *
     * @param applicant     applicant whos job applications should be returned.
     * @return              list of job application given the entered applicant.
     */
    List<JobApplication> findJobApplicationsByApplicant(Applicant applicant);

    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    JobApplication save(JobApplication jobApplication);

    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    void delete(JobApplication jobApplication);
}
