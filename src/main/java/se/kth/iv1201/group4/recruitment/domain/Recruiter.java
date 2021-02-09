package se.kth.iv1201.group4.recruitment.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.ToString;
import se.kth.iv1201.group4.recruitment.dto.RecruiterDTO;

@ToString
@Entity
@Table(name = "recruiter")
public class Recruiter implements RecruiterDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person", referencedColumnName = "id")
    private Person person;

    /**
     * Required by JPA
     */
    protected Recruiter() {

    }

    /**
     * Creates a new instance with the specified person.
     * 
     * @param person the person.
     */
    public Recruiter(Person person) {
        this.person = person;
    }

    @Override
    public int hashCode() {
        return Long.valueOf(id).hashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Recruiter)) {
            return false;
        }
        Recruiter other = (Recruiter) object;
        return this.id == other.id;
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
}
