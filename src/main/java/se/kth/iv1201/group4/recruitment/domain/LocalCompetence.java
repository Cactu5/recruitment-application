package se.kth.iv1201.group4.recruitment.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.ToString;
import se.kth.iv1201.group4.recruitment.dto.LocalCompetenceDTO;
import se.kth.iv1201.group4.recruitment.dto.CompetenceDTO;
import se.kth.iv1201.group4.recruitment.dto.LanguageDTO;

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
public class LocalCompetence implements LocalCompetenceDTO {

    @Column(name = "name")
    @Size(min = 2, max = 30, message = "{competence.name.length}")
    private String name;

    @Id
    @ManyToOne
    @JoinColumn(name = "language")
    private Language language;

    @Id
    @ManyToOne
    @JoinColumn(name = "competence")
    private Competence competence;

    /**
     * Required by JPA
     */
    protected LocalCompetence() {

    }

    /**
     * Creates a new instance with the specified name.
     * 
     * @param name the name.
     */
    public LocalCompetence(String name, Language language, Competence competence) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public LanguageDTO getLanguage() {
        return language;
    }

    @Override
    public CompetenceDTO getCompetence() {
        return competence;
    }

    @Override
    public int hashCode() {
        return competence.hashCode() + language.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof LocalCompetence)) {
            return false;
        }
        LocalCompetence other = (LocalCompetence) object;
        return this.language.equals(other.language) && this.competence.equals(other.competence);
    }

}
