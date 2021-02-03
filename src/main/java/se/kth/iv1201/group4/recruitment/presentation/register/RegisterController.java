package se.kth.iv1201.group4.recruitment.presentation.register;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController  {

    private static final String REGISTER_URL = "register";
    private static final String APPLICANT_URL = "application";

    private static final String ALREADY_EXISTS_MSG = "An applicant with that username, email or SSN already exists.";
    private static final String ERROR_MSG = "Something went wrong. Please try again later.";

    /**
     * A get request for the register page
     * 
     * @return The URL to the register page
     */
    @GetMapping("/" + REGISTER_URL)
    public String showRegisterView() {
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
        //Applicant a;
        if (result.hasErrors()) {
            for (FieldError err : result.getFieldErrors())
                System.out.println(err + " ");
            return REGISTER_URL;
        }
        /*
        a = new Applicant(form.getEmail()
                          form.getUsername()
                          form.getPassword()
                          form.getName()
                          form.getSurname());
        try {
            DAO.addApplicant(a);
        } catch(Finns redan exception e) {
            model.addAttribute("error", ALREADY_EXISTS_MSG);
            return REGISTER_URL;
        } catch(Exception e) {
            model.addAttribute("error", ERROR_MSG);
            return REGISTER_URL;
        }
        */
        return APPLICANT_URL;
    }
}