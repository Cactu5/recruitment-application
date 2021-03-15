package se.kth.iv1201.group4.recruitment.presentation.login;

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
 * Test class that attempts to validate both valid and invalid login forms
 * 
 * @author William Stackenäs
 */
@SpringJUnitWebConfig(initializers = ConfigDataApplicationContextInitializer.class)
@EnableAutoConfiguration
@ComponentScan(basePackages = {"se.kth.iv1201.group4.recruitment"})
@TestExecutionListeners(listeners = { DependencyInjectionTestExecutionListener.class, LoginFormTest.class  })
public class LoginFormTest implements TestExecutionListener {
    @Autowired
    private Validator validator;

    @Test
    public void testBlankPass() {
        testInvalidPassword("", "{login.fail}", 5);
    }

    @Test
    public void testShortPass() {
        testInvalidPassword("a1#", "{login.fail}");
    }

    @Test
    public void testLongPass() {
        testInvalidPassword("ohasfhaoilfjaslokckj dshk)())((/)(/)736247326gyuhdsjhfgsfwueg2387gxybuweq2", "{login.fail}");
    }

    @Test
    public void testNoLetterPass() {
        testInvalidPassword("189345(?+", "{login.fail}");
    }

    @Test
    public void testNoNumberPass() {
        testInvalidPassword("asjdkj%!=fjkd", "{login.fail}");
    }

    @Test
    public void testNoSymbolPass() {
        testInvalidPassword("dljlfaljdHYU27hd32", "{login.fail}");
    }

    @Test
    public void testBlankUsername() {
        testInvalidUsername("", "{login.fail}", 3);
    }

    @Test
    public void testShortUsername() {
        testInvalidUsername("a", "{login.fail}");
    }

    @Test
    public void testLongUsername() {
        testInvalidUsername("lasdjfdalfkjdpfafpjdpasjdpiqjf03fj4239rty29r834d9823j47jd23984u3294dju32942d983y2948327x89", "{login.fail}");
    }

    @Test
    public void testInvalidUsername() {
        testInvalidUsername("jlksdfh ojfp9", "{login.fail}");
    }

    @Test
    public void testValidLogin() {
        LoginForm form = new LoginForm();
        form.setUsername("aaaaaa");
        form.setPassword("abc123##");
        Set<ConstraintViolation<LoginForm>> result = validator.validate(form);
        assertThat(result, is(empty()));
    }

    private void testInvalidPassword(String invalidPass, String expectedMsg) {
        testInvalidPassword(invalidPass, expectedMsg, 1);
    }

    private void testInvalidUsername(String invalidUser, String expectedMsg) {
        testInvalidUsername(invalidUser, expectedMsg, 1);
    }
    
    private void testInvalidPassword(String invalidPass, String expectedMsg, int results) {
        LoginForm form = new LoginForm();
        form.setUsername("aaaaaa");
        form.setPassword(invalidPass);
        Set<ConstraintViolation<LoginForm>> result = validator.validate(form);
        assertThat(result.size(), is(results));
        assertThat(result, hasItem(hasProperty("messageTemplate", equalTo(expectedMsg))));
    }

    private void testInvalidUsername(String invalidUser, String expectedMsg, int results) {
        LoginForm form = new LoginForm();
        form.setUsername(invalidUser);
        form.setPassword("abc123##");
        Set<ConstraintViolation<LoginForm>> result = validator.validate(form);
        assertThat(result.size(), is(results));
        assertThat(result, hasItem(hasProperty("messageTemplate", equalTo(expectedMsg))));
    }   
}
