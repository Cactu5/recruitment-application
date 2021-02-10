package se.kth.iv1201.group4.recruitment.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.ToString;
import se.kth.iv1201.group4.recruitment.dto.ApplicantDTO;
import se.kth.iv1201.group4.recruitment.dto.JobApplicationDTO;

@ToString
@Entity
@Table(name = "applicant")
public class Applicant implements ApplicantDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person", referencedColumnName = "id")
    private Person person;

    @OneToMany(mappedBy = "applicant", cascade = CascadeType.ALL)
    private List<JobApplication> jobApplications = new ArrayList<JobApplication>();

    /**
     * Required by JPA
     */
    protected Applicant() {

    }

    /**
     * Creates a new instance with the specified person.
     * 
     * @param person the person.
     */
    public Applicant(Person person) {
        this.person = person;
    }

    @Override
    public int hashCode() {
        return Long.valueOf(id).hashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Applicant)) {
            return false;
        }
        Applicant other = (Applicant) object;
        return this.id == other.id;
    }

    @Override
    public JobApplicationDTO getJobApplication() {
        return jobApplications.get(0);
    }

    @Override
    public String getName() {
        return person.getName();
    }

    @Override
    public String getSurname() {
        return person.getSurname();
    }

    @Override
    public String getEmail() {
        return person.getEmail();
    }

    @Override
    public String getSSN() {
        return person.getSSN();
    }

    @Override
    public String getUsername() {
        return person.getUsername();
    }

    @Override
    public String getPassword() {
        return person.getPassword();
    }

}
