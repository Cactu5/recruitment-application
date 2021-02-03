package se.kth.iv1201.group4.recruitment.presentation.login;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController  {

    private static final String LOGIN_URL = "login";
    private static final String APPLICANT_URL = "application";
    private static final String RECRUITER_URL = "applications";

    /**
     * A get request for the login page
     * 
     * @return The URL to the login page
     */
    @GetMapping("/" + LOGIN_URL)
    public String showLoginView() {
        return LOGIN_URL;
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
     */
    @PostMapping("/" + LOGIN_URL)
    public String login(@Valid LoginForm form, 
        BindingResult result, Model model) {
        //PersonDTO p;
        if (result.hasErrors()) {
            for (FieldError err : result.getFieldErrors())
                System.out.println(err + " ");
            return LOGIN_URL;
        }
        /*
        try {
            p = DAO.getPerson(form.getUsername(), form.getPassword());
        } catch(Exception e) {
            model.addAttribute("error", "{error.generic}");
            return LOGIN_URL;
        }
        if (p == NULL) {
            model.addAttribute("error", "{login.fail}");
            return LOGIN_URL;
        }
        if (p instanceof ApplicantDTO) {
            return APPLICANT_URL;
        } else if (p instanceof RecruiterDTO) {
            return RECRUITER_URL;
        } else {
            // Should never end up here as all Persons are
            // either applicants or recruiters
            model.addAttribute("error", "{error.generic}");
        }
        */
        return LOGIN_URL;
    }
}
