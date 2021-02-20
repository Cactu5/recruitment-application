package se.kth.iv1201.group4.recruitment.presentation.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import se.kth.iv1201.group4.recruitment.application.PersonService;

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
     * A get request for the root page
     * 
     * @return The URL to the root page
     */
    @GetMapping("/")
    public String showRootView() {
        LOGGER.trace("Get request for the root page.");
        return "success";
    }

    /**
     * A get request for the login page
     * 
     * @return The URL to the login page
     */
    @GetMapping("/login")
    public String showLoginView() {
        LOGGER.trace("Get request for the login page.");
        return "login";
    }
}
