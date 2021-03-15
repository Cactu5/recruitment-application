package se.kth.iv1201.group4.recruitment.presentation.login;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * A bean representing an HTML login form
 * 
 * @author William Stackenäs
 */
public class LoginForm {
    
    @NotBlank(message = "{login.username.missing}")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "{login.fail}")
    @Size(min = 2, max = 30, message = "{login.fail}")
    private String username;

    @NotBlank(message = "{login.password.missing}")
    @Pattern(regexp = "^.*[A-Za-z].*$", message = "{login.fail}")
    @Pattern(regexp = "^.*\\d.*$", message = "{login.fail}")
    @Pattern(regexp = "^.*\\W.*$", message = "{login.fail}")
    @Size(min = 7, max = 30, message = "{login.fail}")
    private String password;

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
}
