package se.kth.iv1201.group4.recruitment.presentation.register;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class RegisterForm {

    @NotBlank(message = "{register.email.missing}")
    @Pattern(regexp = "^[_A-Za-z0-9-+]+" +
                      "(.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(.[A-Za-z0-9]+)*" +
                      "(.[A-Za-z]{2,})$",
             message = "{register.email.invalid}")
    @Size(max = 50, message = "{register.email.length}")
    private String email;

    @NotBlank(message = "{register.username.missing}")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "{register.username.invalid-char}")
    @Size(min = 2, max = 30, message = "{register.username.length}")
    private String username;

    @NotBlank(message = "{register.password.missing}")
    @Pattern(regexp = "^.*[A-Za-z].*$", message = "{register.password.missing-char}")
    @Pattern(regexp = "^.*\\d.*$", message = "{register.password.missing-char}")
    @Pattern(regexp = "^.*\\W.*$", message = "{register.password.missing-char}")
    @Size(min = 7, max = 30, message = "{register.password.length}")
    private String password;

    @NotBlank(message = "{register.name.missing}")
    @Pattern(regexp = "^[A-Za-z]+$", message = "{register.name.invalid-char}")
    @Size(min = 2, max = 30, message = "{register.name.length}")
    private String name;

    @NotBlank(message = "{register.surname.missing}")
    @Pattern(regexp = "^[A-Za-z]+$", message = "{register.surname.invalid-char}")
    @Size(min = 2, max = 30, message = "{register.surname.length}")
    private String surname;

    @NotBlank(message = "{register.ssn.missing}")
    @Pattern(regexp = "^[0-9]+$", message = "{register.ssn.invalid-char}")
    @Size(min = 12, max = 12, message = "{register.ssn.length}")
    private String ssn;

    /**
     * Getter for the email
     * @return The email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Getter for the username
     * @return The username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Getter for the password
     * @return The password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Getter for the name
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for the surname
     * @return The surname
     */
    public String getSuname() {
        return surname;
    }

    /**
     * Getter for the SSN
     * @return The SSN
     */
    public String getSsn() {
        return ssn;
    }

    /**
     * Setter for the email
     * @param email The new email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Setter for the username
     * @param username The new username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Setter for the password
     * @param password The new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Setter for the name
     * @param name The new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Setter for the surname
     * @param surname The new surname
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Setter for the SSN
     * @param ssn The new SSN
     */
    public void setSsn(String ssn) {
        this.ssn = ssn;
    }
}
