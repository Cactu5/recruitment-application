package se.kth.iv1201.group4.recruitment.presentation.reset;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.Matchers.empty;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestExecutionListener;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

/**
 * Test class that attempts to validate both valid and invalid forgot forms
 * 
 * @author William Stackenäs
 */
@SpringJUnitWebConfig(initializers = ConfigDataApplicationContextInitializer.class)
@EnableAutoConfiguration
@ComponentScan(basePackages = {"se.kth.iv1201.group4.recruitment"})
@TestExecutionListeners(listeners = { DependencyInjectionTestExecutionListener.class, ForgotFormTest.class  })
public class ForgotFormTest implements TestExecutionListener {
    @Autowired
    private Validator validator;

    @Test
    public void testBlankEmail() {
        testInvalidEmail("", "{register.email.missing}", 2);
    }

    @Test
    public void testInvalidEmail() {
        testInvalidEmail("@a.b.c", "{register.email.invalid}");
    }

    @Test
    public void testLongEmail() {
        testInvalidEmail("oifhjasdoifjasgdhrtmjrtnrdsdoifnmoimawoifmoepm@oisadfjgsdpofsdfsdfsdfsfsdfqefqfqefnfmwdoicmwepocsdofjs.se", "{register.email.length}");
    }


    @Test
    public void testValidForget() {
        ForgotForm form = new ForgotForm();
        form.setEmail("a@a.com");
        Set<ConstraintViolation<ForgotForm>> result = validator.validate(form);
        assertThat(result, is(empty()));
    }

    private void testInvalidEmail(String invalidEmail, String expectedMsg, int results) {
        ForgotForm form = new ForgotForm();
        form.setEmail(invalidEmail);
        testInvalidForm(form, expectedMsg, results);
    }

    private void testInvalidEmail(String invalidEmail, String expectedMsg) {
        testInvalidEmail(invalidEmail, expectedMsg, 1);
    }
    
    private void testInvalidForm(ForgotForm form, String expectedMsg, int results) {
        Set<ConstraintViolation<ForgotForm>> result = validator.validate(form);
        assertThat(result.size(), is(results));
        assertThat(result, hasItem(hasProperty("messageTemplate", equalTo(expectedMsg))));
    }
}
