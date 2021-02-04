package se.kth.iv1201.group4.recruitment.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.ToString;
import se.kth.iv1201.group4.recruitment.dto.JobStatusDTO;

@ToString
@Entity
@Table(name = "job_status")
public class JobStatus implements JobStatusDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    @Size(min = 2, max = 30, message = "{jobStatus.name.length}")
    private String name;

    @OneToMany(mappedBy = "jobStatus")
    private List<JobApplication> jobApplications = new ArrayList<JobApplication>();

    /**
     * Required by JPA
     */
    protected JobStatus() {

    }

    /**
     * Creates a new instance with the specified name.
     * 
     * @param name the name.
     */
    public JobStatus(String name) {
        this.name = name;
    }

    /**
     * Creates a new instance with the specified name and job applications.
     * 
     * @param name the name.
     */
    public JobStatus(String name, List<JobApplication> jobApplications) {
        this.name = name;
        this.jobApplications = jobApplications;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        return Long.valueOf(id).hashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof JobStatus)) {
            return false;
        }
        JobStatus other = (JobStatus) object;
        return this.id == other.id;
    }

}
