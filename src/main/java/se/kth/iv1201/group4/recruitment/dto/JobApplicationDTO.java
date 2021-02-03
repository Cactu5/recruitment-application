package se.kth.iv1201.group4.recruitment.dto;

public interface JobApplicationDTO {

    /**
     * Returns an array with competences.
     * 
     * @return an array with competences
     */
    public CompetenceDTO[] getCompetences();

    /**
     * Returns an array with availabilites.
     * 
     * @return an array with availabilites
     */
    public AvailabilityDTO[] getAvailabilities();
}
