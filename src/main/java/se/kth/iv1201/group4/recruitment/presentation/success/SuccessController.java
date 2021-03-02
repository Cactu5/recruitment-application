package se.kth.iv1201.group4.recruitment.presentation.success;

import javax.validation.Valid;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import se.kth.iv1201.group4.recruitment.application.PersonService;
import se.kth.iv1201.group4.recruitment.application.error.UpdatedPersonContainsTemporaryDataException;
import se.kth.iv1201.group4.recruitment.domain.Person;
import se.kth.iv1201.group4.recruitment.dto.PersonDTO;
import se.kth.iv1201.group4.recruitment.presentation.register.RegisterForm;
import se.kth.iv1201.group4.recruitment.util.TemporaryDataMatcher;

/**
 * A controller for the success pages.
 * 
 * @author Cactu5
 * @author Filip Garamvölgyi
 * @version %I%
 */
@Controller
public class SuccessController {

    @Autowired
    PersonService service;

    private static final Logger LOGGER = LoggerFactory.getLogger(SuccessController.class);

    /**
     * This endpoint returns the success page for the applicant role.
     * 
     * @return the success page for the applicant role
     */
    @GetMapping("/success-applicant")
    public String showSuccessApplicantView() {
        LOGGER.trace("Get request for /success-applicant.");
        return "success-applicant";
    }

    /**
     * This endpoint returns the success page for the recruiter role.
     * 
     * @return the success page for the recruiter role
     */
    @GetMapping("/success-recruiter")
    public String showSuccessRecruiterView() {
        LOGGER.trace("Get request for /success-recruiter.");
        return "success-recruiter";
    }

    /**
     * This endpoint returns the success page for the legacy user role.
     * 
     * @return the success page for the legacy user role
     */
    @GetMapping("/success-legacy-user")
    public String showSuccessLegacyUserView(RegisterForm registerForm, Authentication auth) {
        LOGGER.info("Get request for /success-legacy-user.");
        setExistingInformationIfValid(registerForm, (UserDetails)auth.getPrincipal());
        return "success-legacy-user";
    }

    private void setExistingInformationIfValid(RegisterForm registerForm, UserDetails userDetails){
        // userDetails.getPassword() if called by the instance used from the pararmeter is null
        userDetails = service.loadUserByUsername(userDetails.getUsername());
        Person p = service.getPerson(userDetails.getUsername(),userDetails.getPassword());

        if(!TemporaryDataMatcher.isTemporaryEmail(p.getEmail()))
            registerForm.setEmail(p.getEmail());

        registerForm.setName(p.getName());
        registerForm.setSurname(p.getSurname());

        if(!TemporaryDataMatcher.isTemporaryUsername(p.getUsername()))
            registerForm.setUsername(userDetails.getUsername());

        if(!TemporaryDataMatcher.isTemporarySSN(p.getSSN()))
            registerForm.setSSN(p.getSSN());
    }

    /**
     * This endpoint updates content of a Person.
     * 
     * @return  if successful redirects to login page otherwise the
     *          same success page is returned.
     */
    @PostMapping("/success-legacy-user")
    public String updatePerson(@Valid RegisterForm form, BindingResult result, Model model, 
            Authentication auth) throws UpdatedPersonContainsTemporaryDataException {
        LOGGER.info("Put request for /success-legacy-user.");
        PersonDTO p;
        if (result.hasErrors()) {
            for (FieldError err : result.getFieldErrors()) {
                LOGGER.debug(err.toString());
                model.addAttribute(err.getField(), err.getDefaultMessage());
            }
            return "success-legacy-user";
        }
        p = new Person(form.getName(), form.getSurname(), form.getEmail(), form.getSSN(), form.getUsername(),
                form.getPassword());
        try {
            service.updatePersonByDTOAndRemoveFromLegacyUsers(p,
                ((UserDetails)auth.getPrincipal()).getUsername());
            LOGGER.info("Legacy user converted to normal user.");
        } catch (ConstraintViolationException e) {
            LOGGER.debug("Registration failure due to primary key conflict.");
            model.addAttribute("error", "{register.fail}");
            return "success-legacy-user";
        }
        return "redirect:logout";
    }

    /**
     * This enpoint routes the user to the appropriate sucess endpoint based on the
     * role.
     * 
     * @return a redirect to the appropriate page
     */
    @GetMapping("/success")
    public String showSuccessView() {

        LOGGER.trace("Get request for /success.");
        UserDetails user = service.getLoggedInUser();

        if (user != null) {
            if (PersonService.authoritiesContains(user.getAuthorities(), PersonService.ROLE_APPLICANT)) {
                return "redirect:success-applicant";
            } else if (PersonService.authoritiesContains(user.getAuthorities(), PersonService.ROLE_RECRUITER)) {
                return "redirect:success-recruiter";
            } else if(PersonService.authoritiesContains(user.getAuthorities(), PersonService.ROLE_LEGACY_USER)){
                return "redirect:success-legacy-user";
            } else {
                LOGGER.error("Authenticated user with no role tried logging in.");
            }
        }

        return "redirect:login";
    }
}
