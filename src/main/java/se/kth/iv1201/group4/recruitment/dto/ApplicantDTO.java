package se.kth.iv1201.group4.recruitment.dto;

/**
 * The ApplicantDTO interface is a DTO. It contains all of the expected getters
 * for this specific type.
 * 
 * The ApplicantDTO interface provides a method to get the job application.
 * 
 * The ApplicantDTO interface is a way to distinguish an applicant from a
 * recruiter.
 * 
 * @author Cactu5
 * @version %I%
 */
public interface ApplicantDTO extends PersonDTO {

    /**
     * Returns a JobApplicationDTO object.
     * 
     * @return a JobApplicationDTO object
     */
    public JobApplicationDTO getJobApplication();
}
