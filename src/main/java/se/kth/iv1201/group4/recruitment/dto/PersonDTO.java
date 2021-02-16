package se.kth.iv1201.group4.recruitment.dto;

/**
 * This PersonDTO is a DTO. It contains all of the expected getters for this
 * specific type.
 * 
 * The PersonDTO interface provides methods to get the necessary information of
 * a person. Which consists of: name, surname, email, social security number,
 * username and password.
 * 
 * @author Cactu5
 */
public interface PersonDTO {

    /**
     * Returns the name.
     * 
     * @return the name
     */
    public String getName();

    /**
     * Returns the surname.
     * 
     * @return the surname.
     */
    public String getSurname();

    /**
     * Returns the email.
     * 
     * @return the email
     */
    public String getEmail();

    /**
     * Returns the social security number.
     * 
     * @return the social security number
     */
    public String getSSN();

    /**
     * Returns the username.
     * 
     * @return the username
     */
    public String getUsername();

    /**
     * Returns the password.
     * 
     * @return the password
     */
    public String getPassword();
}
