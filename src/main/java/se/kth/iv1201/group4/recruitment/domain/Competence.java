package se.kth.iv1201.group4.recruitment.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.ToString;
import se.kth.iv1201.group4.recruitment.dto.CompetenceDTO;

/**
 * An entity representing a competence.
 * 
 * @author Cactu5
 * @author William Stackenäs
 * @version %I%
 */
@ToString
@Entity
@Table(name = "competence")
public class Competence implements CompetenceDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @OneToMany(mappedBy = "localCompetence")
    private List<LocalCompetence> competenceProfiles = new ArrayList<LocalCompetence>();


    /**
     * Creates a new instance.
     */
    public Competence() {
    }

    @Override
    public int hashCode() {
        return Long.valueOf(id).hashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Competence)) {
            return false;
        }
        Competence other = (Competence) object;
        return this.id == other.id;
    }

}
