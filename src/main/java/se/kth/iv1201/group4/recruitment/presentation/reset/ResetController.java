package se.kth.iv1201.group4.recruitment.presentation.reset;

import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
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
import se.kth.iv1201.group4.recruitment.domain.Person;
import se.kth.iv1201.group4.recruitment.dto.PersonDTO;
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
     * @param response  The response of the sent request
     * @param uuidStr   The UUID of the person that requested a reset of their account
     * @param form      The registration form that should be sent in a post request
     * @param model     The model objects used in the reset page
     * @return The URL to the reset page
     */
    @GetMapping("/reset/{uuidStr}")
    public String showResetView(HttpServletResponse response,
                                @PathVariable String uuidStr,
                                RegisterForm form,
                                Model model) {
        LOGGER.trace("Get request for the reset page.");

        if (service.getLoggedInUser() != null) {
            LOGGER.info("An authenticated user tried to use the reset page.");
            return "redirect:success";
        }
        try {
            validateUUIDString(uuidStr);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", "error.404");
            response.setStatus(404);
            return "error";
        }

        return "reset";
    }

    /**
     * A post request on the reset page, or a reset attempt.
     * 
     * @param response The response of the sent request
     * @param uuidStr  The UUID of the person that requested a reset of their account
     * @param form     The registration form that was sent in the post request
     *                 and should be used to update the account
     * @param result   The result when validating the form
     * @param model    The model objects used in the reset page
     * @return The URL to the reset page if the registration attempt failed.
     *         Otherwise, the URL to the applicant or recruiter pages.
     * 
     */
    @PostMapping("/reset/{uuidStr}")
    public String resetPerson(HttpServletResponse response,
                              @PathVariable String uuidStr,
                              @Valid RegisterForm form,
                              BindingResult result,
                              Model model) throws Exception {
        Person p;
        UUID uuid;

        LOGGER.info("Reset attempt.");

        try {
            uuid = validateUUIDString(uuidStr);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", "error.404");
            response.setStatus(404);
            return "error";
        }
        
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
            service.resetPersonFromResetAccountList(uuid, (PersonDTO)p);
            service.autoLogin(form.getUsername(), form.getPassword());
        } catch (ConstraintViolationException | DataIntegrityViolationException e) {
            LOGGER.info("Reset failure due to primary key conflict.");
            model.addAttribute("error", "register.fail"); // Same error message as register
            return "reset";
        }
        LOGGER.info("New user " + form.getUsername() + " reset their account successfully.");
        return "redirect:success";
    }

    private UUID validateUUIDString(String uuidStr) throws IllegalArgumentException {
        UUID uuid;
        String email;
        try {
            uuid = UUID.fromString(uuidStr);
            email = service.getEmailFromAccountList(uuid);
        } catch (IllegalArgumentException e) {
            // If the uuid was malformed
            throw new IllegalArgumentException("Bad UUID");
        }
        if (email == null) {
            throw new IllegalArgumentException("Nonexistent UUID");
        }
        return uuid;
    }
}
