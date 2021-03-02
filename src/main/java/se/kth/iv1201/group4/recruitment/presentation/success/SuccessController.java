package se.kth.iv1201.group4.recruitment.presentation.success;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.support.RequestContextUtils;

import se.kth.iv1201.group4.recruitment.application.CompetenceService;
import se.kth.iv1201.group4.recruitment.application.LanguageService;
import se.kth.iv1201.group4.recruitment.application.PersonService;
import se.kth.iv1201.group4.recruitment.domain.Competence;
import se.kth.iv1201.group4.recruitment.domain.Language;
import se.kth.iv1201.group4.recruitment.domain.LocalCompetence;

/**
 * A controller for the success pages.
 * 
 * @author Cactu5
 * @author William Stackenäs
 * @version %I%
 */
@Controller
public class SuccessController {

    @Autowired
    PersonService service;

    @Autowired
    CompetenceService competenceService;

    @Autowired
    LanguageService languageService;

    private static final Logger LOGGER = LoggerFactory.getLogger(SuccessController.class);

    /**
     * This endpoint returns the success page for the applicant role.
     * 
     * @return the success page for the applicant role
     */
    @GetMapping("/success-applicant")
    public String showSuccessApplicantView(HttpServletRequest request, Model model) {
        LOGGER.trace("Get request for /success-applicant.");
        Language language = languageService.getLanguage(RequestContextUtils.getLocale(request));
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
