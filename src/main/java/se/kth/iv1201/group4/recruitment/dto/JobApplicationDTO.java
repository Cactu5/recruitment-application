package se.kth.iv1201.group4.recruitment.dto;

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
