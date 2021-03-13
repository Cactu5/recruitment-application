package se.kth.iv1201.group4.recruitment.presentation.error;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * A controller used for testing the error handling controller
 * by throwing an exception
 * 
 * @author William Stackenäs
 */
@Controller
public class ExceptionThrowingController {
    
    public static final String URL = "urlthatwillthrowexception";

    /**
     * A get request that results in an exception.
     * 
     * @return N/A
     * @throws Exception the exception that is thrown.
     */
    @GetMapping("/" + URL)
    public String throwException() throws Exception {
        throw new Exception("Called controller that throws exception.");
    }
}
