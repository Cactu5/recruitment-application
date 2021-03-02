package se.kth.iv1201.group4.recruitment.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.ToString;
import se.kth.iv1201.group4.recruitment.dto.AvailabilityDTO;
import se.kth.iv1201.group4.recruitment.dto.CompetenceProfileDTO;
import se.kth.iv1201.group4.recruitment.dto.JobApplicationDTO;
import se.kth.iv1201.group4.recruitment.dto.JobStatusDTO;

/**
 * An entity representing a job application.
 * 
 * @author Cactu5
 * @author Filip Garamvölgyi
 * @version %I%
 */
@ToString
@Entity
@Table(name = "job_application")
public class JobApplication implements JobApplicationDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "applicant")
    private Applicant applicant;

    @ManyToOne
    @JoinColumn(name = "job_status")
    private JobStatus jobStatus;

    @OneToMany(mappedBy = "jobApplication")
    private List<CompetenceProfile> competenceProfiles = new ArrayList<CompetenceProfile>();

    @OneToMany(mappedBy = "jobApplication")
    private List<Availability> availabilites = new ArrayList<Availability>();

    /**
     * Required by JPA
     */
    protected JobApplication() {

    }
    /**
     * Creates a new instance with the specified applicant, job status
     * 
     * @param applicant     owner of the job application
     * @param jobStatus     status of the job application
     */
    public JobApplication(Applicant applicant, JobStatus jobStatus) {
        this.applicant = applicant;
        this.jobStatus = jobStatus;
    }

    /**
     * Creates a new instance with the specified applicant, job status, competence
     * profiles and availabilites.
     * 
     * @param applicant
     * @param jobStatus
     * @param competenceProfiles
     * @param availabilites
     */
    public JobApplication(Applicant applicant, JobStatus jobStatus, List<CompetenceProfile> competenceProfiles,
            List<Availability> availabilites) {
        this.applicant = applicant;
        this.jobStatus = jobStatus;
        this.competenceProfiles = competenceProfiles;
        this.availabilites = availabilites;
    }

    @Override
    public CompetenceProfileDTO[] getCompetences() {
        return competenceProfiles.toArray(CompetenceProfileDTO[]::new);
    }

    @Override
    public AvailabilityDTO[] getAvailabilities() {
        return availabilites.toArray(AvailabilityDTO[]::new);
    }

    @Override
    public JobStatusDTO getStatus() {
        return jobStatus;
    }

    @Override
    public int hashCode() {
        return Long.valueOf(id).hashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof JobApplication)) {
            return false;
        }
        JobApplication other = (JobApplication) object;
        return this.id == other.id;
    }

}
