package se.kth.iv1201.group4.recruitment.presentation.register;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import se.kth.iv1201.group4.recruitment.application.PersonService;
import se.kth.iv1201.group4.recruitment.domain.Applicant;
import se.kth.iv1201.group4.recruitment.domain.Person;

/**
 * A controller for accessing the register page
 * 
 * @author William Stackenäs
 */
@Controller
public class RegisterController  {

    @Autowired
    PersonService service;

    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterController.class);

    private static final String REGISTER_URL = "register";
    private static final String APPLICANT_URL = "application";

    /**
     * A get request for the register page
     * 
     * @return The URL to the register page
     */
    @GetMapping("/" + REGISTER_URL)
    public String showRegisterView(RegisterForm registerForm) {
        LOGGER.trace("Get request for the register page.");
        return REGISTER_URL;
    }

    /**
     * A post request on the register page, or a registration attempt.
     * Only applicants can register using this method.
     * 
     * @param form    The registration form that was sent in the post request
     * @param result  The result when validating the form
     * @param model   The model objects used in the register page
     * @return The URL to the register page if the registration attempt failed.
     *         Otherwise, the URL to the applicant page.
     *          
     */
    @PostMapping("/" + REGISTER_URL)
    public String register(@Valid RegisterForm form, 
        BindingResult result, Model model) {
        LOGGER.trace("Registration attempt.");
        Person p;
        if (result.hasErrors()) {
            for (FieldError err : result.getFieldErrors()) {
                LOGGER.debug(err.toString());
                model.addAttribute(err.getField(), err.getDefaultMessage());
            }
            return REGISTER_URL;
        }
        p = new Person(form.getName(),
                       form.getSurname(),
                       form.getEmail(),
                       form.getSsn(),
                       form.getUsername(),
                       form.getPassword());
        try {
            service.addApplicant(new Applicant(p));
        } catch(DataAccessException ex) {
            LOGGER.debug("Registration failure due to primary key conflict.");
            model.addAttribute("error", "{register.fail}");
            return REGISTER_URL;
        } catch(Exception e) {
            LOGGER.error("Could not add applicant to database.", e);
            model.addAttribute("error", "{error.gereric}");
            return REGISTER_URL;
        }
        return APPLICANT_URL;
    }
}
