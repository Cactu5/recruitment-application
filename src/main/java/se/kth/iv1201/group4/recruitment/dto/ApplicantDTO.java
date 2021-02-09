package se.kth.iv1201.group4.recruitment.dto;

public interface ApplicantDTO extends PersonDTO {

    /**
     * Returns a JobApplicationDTO object.
     * 
     * @return a JobApplicationDTO object
     */
    public JobApplicationDTO getJobApplication();
}
