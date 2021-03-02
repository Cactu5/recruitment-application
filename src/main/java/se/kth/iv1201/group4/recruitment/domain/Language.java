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
import javax.validation.constraints.Size;

import lombok.ToString;
import se.kth.iv1201.group4.recruitment.dto.LanguageDTO;

/**
 * An entity representing a language.
 * 
 * @author William Stackenäs
 */
@ToString
@Entity
@Table(name = "language")
public class Language implements LanguageDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    @Size(min = 2, max = 30, message = "{langauge.name.length}")
    private String name;

    @OneToMany(mappedBy = "localCompetence")
    private List<LocalCompetence> competenceProfiles = new ArrayList<LocalCompetence>();

    /**
     * Required by JPA
     */
    protected Language() {

    }

    /**
     * Creates a new instance with the specified name.
     * 
     * @param name the name.
     */
    public Language(String name) {
        this.name = name;
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
        if (!(object instanceof Language)) {
            return false;
        }
        Language other = (Language) object;
        return this.id == other.id;
    }

}
