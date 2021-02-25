package se.kth.iv1201.group4.recruitment.dto;

import java.time.LocalDate;

/**
 * The AvailabilityDTO interface is a DTO. It contains all of the expected
 * getters for this specific type.
 * 
 * The AvailabilityDTO interface provides a way to get the availabity's start
 * and end dates.
 * 
 * @author Cactu5
 * @author Filip Garamvölgyi
 * @version %I%
 */
public interface AvailabilityDTO {
    /**
     * Returns the starting time of the availability.
     * 
     * @return the starting time of the availability
     */
    public LocalDate getFrom();

    /**
     * Returns the ending time of the availability.
     * 
     * @return the ending time of the availability
     */
    public LocalDate getTo();

    /**
     * @return returns the associated {@link JobApplicationDTO}.
     */
    public JobApplicationDTO getJobApplication();
}
