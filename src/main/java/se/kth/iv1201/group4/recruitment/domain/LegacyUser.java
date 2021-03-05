package se.kth.iv1201.group4.recruitment.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.ToString;
import se.kth.iv1201.group4.recruitment.dto.LegacyUserDTO;

/**
 * An entity representing a legacy user.
 * 
 * @author Filip Garamvölgyi
 * @author Cactu5
 * @version %I%
 */
@ToString
@Entity
@Table(name = "legacy_user")
public class LegacyUser implements LegacyUserDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NotNull(message = "{legacyUser.person.missing}")
    @OneToOne
    @JoinColumn(name = "person", referencedColumnName = "id")
    private Person person;

    /**
     * Required by JPA
     */
    protected LegacyUser() {
    }

    public LegacyUser(Person person) {
        this.person = person;
    }

    @Override
    public Person getPerson() {
        return person;
    }

    @Override
    public int hashCode() {
        return Long.valueOf(id).hashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof LegacyUser)) {
            return false;
        }
        LegacyUser other = (LegacyUser) object;
        return this.id == other.id;
    }
}
