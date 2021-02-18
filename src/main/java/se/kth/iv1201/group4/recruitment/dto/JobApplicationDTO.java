package se.kth.iv1201.group4.recruitment.dto;

/**
 * This JobApplicationDTO is a DTO. It contains all of the expected getters for
 * this specific type.
 * 
 * The JobApplicationDTO interface provides a way to get all of the competences,
 * availabilities and also the status of the job application.
 * 
 * @author Cactu5
 * @version %I%
 */
public interface JobApplicationDTO {

    /**
     * Returns an array with competences.
     * 
     * @return an array with competences
     */
    public CompetenceProfileDTO[] getCompetences();

    /**
     * Returns an array with availabilites.
     * 
     * @return an array with availabilites
     */
    public AvailabilityDTO[] getAvailabilities();

    /**
     * Returns the status of the job application.
     * 
     * @return the status
     */
    public JobStatusDTO getStatus();
}
