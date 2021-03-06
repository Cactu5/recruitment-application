package se.kth.iv1201.group4.recruitment.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.ToString;
import se.kth.iv1201.group4.recruitment.dto.PersonDTO;

/**
 * An entity representing a person.
 * 
 * @author Cactu5
 * @version %I%
 */
@ToString(exclude = "password")
@Entity
@Table(name = "person")
public class Person implements PersonDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Pattern(regexp = "^[A-Za-z]+$", message = "{person.name.invalid-char}")
    @Size(min = 2, max = 30, message = "{person.name.length}")
    @Column(name = "name")
    private String name;

    @Pattern(regexp = "^[A-Za-z]+$", message = "{person.surname.invalid-char}")
    @Size(min = 2, max = 30, message = "{person.surname.length}")
    @Column(name = "surname")
    private String surname;

    @Pattern(regexp = "^[_A-Za-z0-9-+]+" + "(.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(.[A-Za-z0-9]+)*"
            + "(.[A-Za-z]{2,})$", message = "{person.email.invalid}")
    @Size(max = 50, message = "{person.email.length}")
    @Column(name = "email", unique = true)
    private String email;

    @Pattern(regexp = "^[0-9]+$", message = "{person.ssn.invalid-char}")
    @Size(min = 12, max = 12, message = "{person.ssn.length}")
    @Column(name = "ssn", unique = true)
    private String ssn;

    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "{person.username.invalid-char}")
    @Size(min = 2, max = 30, message = "{person.username.length}")
    @Column(name = "username", unique = true)
    private String username;

    /**
     * The password encoder used to encode all passwords in the application
     */
    public final static PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    @JsonIgnore
    @Column(name = "password")
    private String password;

    /**
     * Required by JPA
     */
    protected Person() {

    }

    /**
     * Creates a new instance with the specified name, surname, email, ssn, username
     * and password.
     * 
     * @param name     the person's name.
     * @param surname  the person's surname.
     * @param email    the person's email.
     * @param ssn      the person's ssn.
     * @param username the person's username.
     * @param password the person's password.
     */
    public Person(String name, String surname, String email, String ssn, String username, String password) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.ssn = ssn;
        this.username = username;
        setPassword(password);
    }

    private void setPassword(String pass) {
        this.password = PASSWORD_ENCODER.encode(pass);
    }

    /**
     * Overwrites the fields of the person with the fields of the
     * given PersonDTO, if they are not null
     * 
     * @param dto The dto that will be used to overwrite the fields
     */
    public void updateWithContentsOfDTO(PersonDTO dto){
        if(dto.getSSN() != null)
            this.ssn = dto.getSSN();
        if(dto.getName() != null)
            this.name = dto.getName();
        if(dto.getEmail() != null)
            this.email = dto.getEmail();
        if(dto.getSurname() != null)
            this.surname = dto.getSurname();
        if(dto.getUsername() != null)
            this.username = dto.getUsername();
        if(dto.getPassword() != null)
           this.password = dto.getPassword();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getSurname() {
        return surname;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getSSN() {
        return ssn;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public int hashCode() {
        return Long.valueOf(username).hashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Person)) {
            return false;
        }
        Person other = (Person) object;
        return this.username == other.username;
    }

}
