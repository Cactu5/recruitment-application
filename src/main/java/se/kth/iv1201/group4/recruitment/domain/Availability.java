package se.kth.iv1201.group4.recruitment.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.ToString;
import se.kth.iv1201.group4.recruitment.dto.AvailabilityDTO;

@ToString
@Entity
@Table(name = "availabilities")
public class Availability implements AvailabilityDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "from_date")
    private LocalDate fromDate;

    @Column(name = "to_date")
    private LocalDate toDate;

    @ManyToOne
    @JoinColumn(name = "job_application")
    private JobApplication jobApplication;

    /**
     * Required by JPA
     */
    protected Availability() {

    }

    /**
     * Creates a new instance with the specified from date, to date and job
     * application.
     * 
     * @param fromDate       the from date.
     * @param toDate         the to date.
     * @param jobApplication the job application.
     */
    public Availability(LocalDate fromDate, LocalDate toDate, JobApplication jobApplication) {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.jobApplication = jobApplication;
    }

    @Override
    public LocalDate getFrom() {
        return fromDate;
    }

    @Override
    public LocalDate getTo() {
        return toDate;
    }

    @Override
    public int hashCode() {
        return Long.valueOf(id).hashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Availability)) {
            return false;
        }
        Availability other = (Availability) object;
        return this.id == other.id;
    }
}
