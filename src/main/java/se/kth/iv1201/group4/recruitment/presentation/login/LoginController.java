package se.kth.iv1201.group4.recruitment.presentation.login;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import se.kth.iv1201.group4.recruitment.application.PersonService;
import se.kth.iv1201.group4.recruitment.domain.Applicant;
import se.kth.iv1201.group4.recruitment.domain.Recruiter;
import se.kth.iv1201.group4.recruitment.dto.PersonDTO;

/**
 * A controller for accessing the login page
 * 
 * @author William Stackenäs
 */
@Controller
public class LoginController  {

    @Autowired
    PersonService service;

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    /**
     * A get request for the login page
     * 
     * @return The URL to the login page
     */
    @GetMapping("/login")
    public String showLoginView() {
        LOGGER.trace("Get request for the login page.");
        return "/login";
    }

    /**
     * A post request on the login page, or a login attempt
     * 
     * @param form    The login form that was sent in the post request
     * @param result  The result when validating the form
     * @param model   The model objects used in the login page
     * @return The URL to the login page if the login attempt failed.
     *         Otherwise, the URL to the applicant or recruiter page
     *         depending on who the user was logged in as.
     *          
     *//*
    @PostMapping("/login")
    public String login(@Valid LoginForm form, BindingResult result, Model model) {
        LOGGER.trace("Login attempt.");
        PersonDTO p;
        if (result.hasErrors()) {
            for (FieldError err : result.getFieldErrors()) {
                LOGGER.debug(err.toString());
                model.addAttribute(err.getField(), err.getDefaultMessage());
            }
            return "/login";
        }
        
        try {
            p = service.getPerson(form.getUsername(), form.getPassword());
        } catch(Exception e) {
            model.addAttribute("error", "{error.generic}");
            LOGGER.error("Could not retrieve person from database.", e);
            return "/login";
        }
        if (p == null) {
            model.addAttribute("error", "{login.fail}");
            return "/login";
        }
        if (p instanceof Applicant) {
            LOGGER.debug("Person logged in as an applicant.");
            return "/application";
        } else if (p instanceof Recruiter) {
            LOGGER.info("Person logged in as a recruiter.");
            return "/applications";
        } else {
            // Should never end up here as all Persons are
            // either applicants or recruiters
            LOGGER.error("Person logged in as neither an applicant nor recruiter.");
            model.addAttribute("error", "{error.generic}");
        }
        return "/login";
    }*/
}
