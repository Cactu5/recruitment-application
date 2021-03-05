package se.kth.iv1201.group4.recruitment.presentation.reset;

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

/**
 * A controller for accessing the forgot page
 * 
 * @author William Stackenäs
 */
@Controller
public class ForgotController {
    
    @Autowired
    private PersonService service;

    private static final Logger LOGGER = LoggerFactory.getLogger(ForgotController.class);

    /**
     * A get request for the forgot page
     * 
     * @return The URL to the forgot page
     */
    @GetMapping("/forgot")
    public String showForgotView(ForgotForm registerForm) {
        LOGGER.info("Get request for the forgot page.");

        if (service.getLoggedInUser() != null) {
            LOGGER.trace("An authenticated user tried to use the forgot page.");
            return "redirect:success";
        }

        return "forgot";
    }

    /**
     * A post request on the forgot page, or a request to send a link to a page where
     * the account with the given email can be reset.
     * 
     * @param form   The forgot form that was sent in the post request
     * @param result The result when validating the form
     * @param model  The model objects used in the register page
     * @return The URL to the register page if the registration attempt failed.
     *         Otherwise, the URL to the applicant page.
     * 
     */
    @PostMapping("/forgot")
    public String sendResetLink(@Valid ForgotForm form, BindingResult result, Model model) throws Exception {
        LOGGER.trace("Registration attempt.");

        if (result.hasErrors()) {
            for (FieldError err : result.getFieldErrors()) {
                LOGGER.info(err.toString());
                model.addAttribute(err.getField(), err.getDefaultMessage());
            }
            return "forgot";
        }
        try {
            service.addPersonToResetAccountList(form.getEmail());
        } catch (IllegalArgumentException e) {
            LOGGER.info("Invalid email was given in the forgot page.");
            model.addAttribute("error", "forgot.fail");
            return "forgot";
        }
        model.addAttribute("message", "forgot.success");
        return "forgot";
    }
}
