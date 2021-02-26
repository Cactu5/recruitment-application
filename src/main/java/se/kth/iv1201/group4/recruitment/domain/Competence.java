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
import javax.validation.constraints.Size;

import lombok.ToString;
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
public class Competence implements CompetenceDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    @Size(min = 2, max = 30, message = "{competence.name.length}")
    private String name;

    @ManyToOne
    @JoinColumn(name = "language")
    private Language language;

    /**
     * Required by JPA
     */
    protected Competence() {

    }

    /**
     * Creates a new instance with the specified name.
     * 
     * @param name the name.
     */
    public Competence(String name, Language language) {
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
