package se.kth.iv1201.group4.recruitment.presentation.success;

import javax.validation.Valid;

import org.hibernate.exception.ConstraintViolationException;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.RequestContextUtils;
import se.kth.iv1201.group4.recruitment.application.CompetenceService;
import se.kth.iv1201.group4.recruitment.application.LanguageService;
import se.kth.iv1201.group4.recruitment.application.PersonService;
import se.kth.iv1201.group4.recruitment.application.error.UpdatedPersonContainsTemporaryDataException;
import se.kth.iv1201.group4.recruitment.domain.Person;
import se.kth.iv1201.group4.recruitment.dto.PersonDTO;
import se.kth.iv1201.group4.recruitment.presentation.register.RegisterForm;
import se.kth.iv1201.group4.recruitment.util.TemporaryDataMatcher;
import se.kth.iv1201.group4.recruitment.util.error.EmailAlreadyExistsException;
import se.kth.iv1201.group4.recruitment.util.error.UsernameAlreadyExistsException;
import se.kth.iv1201.group4.recruitment.domain.Language;
import se.kth.iv1201.group4.recruitment.domain.LocalCompetence;

/**
 * A controller for the success pages.
 * 
 * @author Cactu5
 * @author Filip Garamvölgyi
 * @author William Stackenäs
 * @version %I%
 */
@Controller
public class SuccessController {

    @Autowired
    private PersonService service;

    @Autowired
    CompetenceService competenceService;

    @Autowired
    LanguageService languageService;

    private static final Logger LOGGER = LoggerFactory.getLogger(SuccessController.class);

    /**
     * This endpoint returns the success page for the applicant role,
     * containing a dropdown meny of all competences in the database in
     * the language requested by the client, if possible
     * 
     * @param request The HTTP request from the client that may contain
     *                information about its prefered language
     * @param model  The model objects used in the register page
     * @param lang   The lang query parameter from the URL contining the
     *               language that should be used. If empty, the default
     *               language will be used.
     * 
     * @return the success page for the applicant role
     */
    @GetMapping("/success-applicant")
    public String showSuccessApplicantView(HttpServletRequest request,
            Model model, @RequestParam(name = "lang", required = false) String lang) {
        LOGGER.trace("Get request for /success-applicant.");
        Locale locale;
        if (lang != null && !lang.isEmpty()) {
            locale = new Locale(lang);
        } else {
            locale = RequestContextUtils.getLocale(request);
        }
        Language language = languageService.getLanguage(locale);
        List<LocalCompetence> comps = competenceService.getLocalCompetences(language);

        model.addAttribute("competencies", comps);
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
        LOGGER.info("Post request for /success-legacy-user.");
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
            LOGGER.info(String.format("Attempting to update legacy user: %s",
                        ((UserDetails)auth.getPrincipal()).getUsername()));
            service.updatePersonWithUsernameAndRemoveFromLegacyUsers(p,
                ((UserDetails)auth.getPrincipal()).getUsername());
            LOGGER.info("Legacy user converted to normal user.");
        } catch (ConstraintViolationException e) {
            LOGGER.debug("Registration failure due to primary key conflict.");
            model.addAttribute("error", "{register.fail}");
            return "success-legacy-user";
        } catch(EmailAlreadyExistsException e){
            LOGGER.debug("Tried to use already existing email");
            model.addAttribute("dbUniqueEmailError", "");
            return "success-legacy-user";
        } catch(UsernameAlreadyExistsException e){
            LOGGER.debug("Tried to use already existing username");
            model.addAttribute("dbUniqueUsernameError","");
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
                LOGGER.warn("Authenticated user with no role tried logging in.");
                throw new UsernameNotFoundException("user has no role");
            }
        }

        return "redirect:login";
    }
}
