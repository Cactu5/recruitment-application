package se.kth.iv1201.group4.integration;

/**
 * Representation of a row from the availability in the database
 *
 * @author Filip Garamvölgyi
 */
public class Availability {
    private final int availabilityId;
    private final int personId;
    private final String fromDate;
    private final String toDate;

    Availability(final int availabilityId, final int personId,
            final String fromDate, final String toDate){
        this.availabilityId = availabilityId;
        this.personId = personId;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public int getAvailabilityId() { return availabilityId; }
    public int getPersonId() { return personId; }

    public String getFromDate(){return fromDate;}
    public String getToDate(){return toDate;}
}
