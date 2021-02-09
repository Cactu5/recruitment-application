package se.kth.iv1201.group4.recruitment.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.ToString;
import se.kth.iv1201.group4.recruitment.dto.CompetenceDTO;
import se.kth.iv1201.group4.recruitment.dto.CompetenceProfileDTO;

@ToString
@Entity
@Table(name = "competence_profile")
public class CompetenceProfile implements CompetenceProfileDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "years_of_experience")
    private float yearsOfExperience;

    @ManyToOne
    @JoinColumn(name = "competence")
    private Competence competence;

    @ManyToOne
    @JoinColumn(name = "job_application")
    private JobApplication jobApplication;

    /**
     * Required by JPA
     */
    protected CompetenceProfile() {

    }

    /**
     * Creates a new instance with the specified years of experience and competence.
     * 
     * @param yearsOfExperience the years of experience.
     * @param competence        the competence.
     */
    public CompetenceProfile(float yearsOfExperience, Competence competence) {
        this.yearsOfExperience = yearsOfExperience;
        this.competence = competence;
    }

    /**
     * Creates a new instance with the specified years of experience, competence and
     * job application.
     * 
     * @param yearsOfExperience the years of experience.
     * @param competence        the competence.
     * @param jobApplication    the job application.
     */
    public CompetenceProfile(float yearsOfExperience, Competence competence, JobApplication jobApplication) {
        this.yearsOfExperience = yearsOfExperience;
        this.competence = competence;
        this.jobApplication = jobApplication;
    }

    @Override
    public CompetenceDTO getCompetence() {
        return competence;
    }

    @Override
    public float getYearsOfExperience() {
        return yearsOfExperience;
    }

    @Override
    public int hashCode() {
        return Long.valueOf(id).hashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof CompetenceProfile)) {
            return false;
        }
        CompetenceProfile other = (CompetenceProfile) object;
        return this.id == other.id;
    }

}
