package se.kth.iv1201.group4.recruitment.dto;

/**
 * This CompetenceProfileDTO is a DTO. It contains all of the expected getters
 * for this specific type.
 * 
 * The CompetenceProfileDTO interface provides a way to get the competence and
 * the years of experience in that specific competence.
 * 
 * @author Cactu5
 * @author Filip Garamvölgyi
 * @version %I%
 */
public interface CompetenceProfileDTO {

    /**
     * Returns the competence.
     * 
     * @return the competence
     */
    public CompetenceDTO getCompetence();

    /**
     * Returns the years of experience.
     * 
     * @return the years of experience
     */
    public float getYearsOfExperience();

    /**
     * @return  returns the associated {@link JobApplicationDTO}.
     */
    public JobApplicationDTO getJobApplication();
}
