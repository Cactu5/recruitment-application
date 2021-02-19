package se.kth.iv1201.group4.recruitment.presentation.error;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * A controller for handling exceptions and showing an appropriate
 * view for the user
 * 
 * @author William Stackenäs
 */
@Controller
public class ErrorHandler implements ErrorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorHandler.class);

    /**
     * An exception handler for database related errors
     * 
     * @param e The database exception that was thrown
     * @param model The model objects used in the error page
     * @return An appropriate error page
     */
    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleDatabaseException(DataAccessException e, Model model) {
        LOGGER.error(e.getMessage(), e);
        model.addAttribute("error", "{error.db}");
        return getErrorPath();
    }

    /**
     * An exception handler for errors that do not have a more
     * specific handler
     * 
     * @param e The exception that was thrown
     * @param model The model objects used in the error page
     * @return A generic error page
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception e, Model model) {
        LOGGER.error(e.getMessage(), e);
        model.addAttribute("error", "{error.gereric}");
        return getErrorPath();
    }

    /**
     * A get request for the error page. Will respond with the error page
     * and a status code found in the request.
     * 
     * @param request The HTTP request that was sent
     * @param response The HTTP response that will be sent
     * @param model The model objects used in the error page
     * @return The error page
     */
    @GetMapping("/error")
    public String showErrorView(HttpServletRequest request, HttpServletResponse response, Model model) {
        String status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE).toString();
        LOGGER.debug("Showing error view with status " + status + ".");
        model.addAttribute("error", status);
        response.setStatus(Integer.parseInt(status));
        return getErrorPath();
    }

    /**
     * Gets the path for the error page
     * @return The error page path
     */
    @Override
    public String getErrorPath() {
        return "error";
    }
}
