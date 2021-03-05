package se.kth.iv1201.group4.recruitment.presentation.success;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import se.kth.iv1201.group4.recruitment.application.PersonService;

/**
 * A controller for the success pages.
 * 
 * @author Cactu5
 * @version %I%
 */
@Controller
public class SuccessController {

    @Autowired
    private PersonService service;

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
            } else {
                LOGGER.warn("Authenticated user with no role tried logging in.");
                throw new UsernameNotFoundException("user has no role");
            }
        }

        return "redirect:login";
    }
}
