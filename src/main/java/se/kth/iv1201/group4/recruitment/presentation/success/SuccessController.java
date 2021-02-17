package se.kth.iv1201.group4.recruitment.presentation.success;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * A controller for the success page.
 * 
 * @author Cactu5
 * @version %I%
 */
@Controller
public class SuccessController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SuccessController.class);

    @GetMapping("/success")
    public String showRootView() {
        LOGGER.trace("Get request for /success.");
        return "success";
    }
}
