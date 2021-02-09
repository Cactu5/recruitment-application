package se.kth.iv1201.group4.recruitment.dto;

import java.time.LocalDate;

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
}
