package se.kth.iv1201.group4.recruitment.presentation.reset;

import java.util.UUID;

import javax.validation.Valid;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import se.kth.iv1201.group4.recruitment.application.PersonService;
import se.kth.iv1201.group4.recruitment.domain.Applicant;
import se.kth.iv1201.group4.recruitment.domain.Person;
import se.kth.iv1201.group4.recruitment.presentation.register.RegisterForm;

/**
 * A controller for accessing the reset page
 * 
 * @author William Stackenäs
 * @version %I%
 */
@Controller
public class ResetController {

    @Autowired
    private PersonService service;

    private static final Logger LOGGER = LoggerFactory.getLogger(ResetController.class);

    /**
     * A get request for the reset page
     * 
     * @param uuid   The UUID of the person that requested a reset of their account
     * @return The URL to the reset page
     */
    @GetMapping("/reset/{id}")
    public String showResetView(@PathVariable String uuid) {
        String email;
        LOGGER.trace("Get request for the reset page.");

        if (service.getLoggedInUser() != null) {
            LOGGER.trace("An authenticated user tried to use the reset page.");
            return "redirect:success";
        }
        try {
            email = service.getEmailFromAccountList(UUID.fromString(uuid));
        } catch (IllegalArgumentException e) {
            // If the uuid was malformed
            return "error";
        }
        if (email == null)
            return "error";

        return "reset";
    }

    /**
     * A post request on the reset page, or a reset attempt.
     * 
     * @param uuid   The UUID of the person that requested a reset of their account
     * @param form   The registration form that was sent in the post request
     *               and should be used to update the account
     * @param result The result when validating the form
     * @param model  The model objects used in the reset page
     * @return The URL to the reset page if the registration attempt failed.
     *         Otherwise, the URL to the applicant or recruiter pages.
     * 
     */
    @PostMapping("/reset/{id}")
    public String resetPerson(@PathVariable String uuidStr, @Valid RegisterForm form, BindingResult result, Model model) throws Exception {
        Person p;
        String email;
        UUID uuid;

        LOGGER.trace("Reset attempt.");

        try {
            uuid = UUID.fromString(uuidStr);
            email = service.getEmailFromAccountList(uuid);
        } catch (IllegalArgumentException e) {
            // If the uuid was malformed
            return "error";
        }
        if (email == null)
            return "error";
        
        if (result.hasErrors()) {
            for (FieldError err : result.getFieldErrors()) {
                LOGGER.info(err.toString());
                model.addAttribute(err.getField(), err.getDefaultMessage());
            }
            return "reset";
        }
        p = new Person(form.getName(), form.getSurname(), form.getEmail(), form.getSSN(), form.getUsername(),
                form.getPassword());
        try {
            service.resetPersonFromResetAccountList(uuid, p);
            service.autoLogin(form.getUsername(), form.getPassword());
        } catch (ConstraintViolationException | DataIntegrityViolationException e) {
            LOGGER.info("Registration failure due to primary key conflict.");
            model.addAttribute("error", "register.fail");
            return "reset";
        }
        LOGGER.info("New user " + form.getUsername() + " reset their account successfully.");
        return "redirect:success";
    }
}
