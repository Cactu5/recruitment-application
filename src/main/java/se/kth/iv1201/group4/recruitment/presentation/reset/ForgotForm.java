package se.kth.iv1201.group4.recruitment.presentation.reset;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * A bean representing an HTML email form
 * used foir sending a link to a reset page
 * 
 * @author William Stackenäs
 */
public class ForgotForm {

    // Use the same error messages as the email from the register form
    @NotBlank(message = "{register.email.missing}")
    @Pattern(regexp = "^[_A-Za-z0-9-+]+" +
                      "(.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(.[A-Za-z0-9]+)*" +
                      "(.[A-Za-z]{2,})$",
             message = "{register.email.invalid}")
    @Size(max = 50, message = "{register.email.length}")
    private String email;

    /**
     * Getter for the email
     * @return The email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter for the email
     * @param email The new email
     */
    public void setEmail(String email) {
        this.email = email;
    }

}