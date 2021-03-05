package se.kth.iv1201.group4.recruitment.presentation.reset;

import javax.servlet.Filter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestExecutionListener;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import se.kth.iv1201.group4.recruitment.domain.Person;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static se.kth.iv1201.group4.recruitment.presentation.PresentationTestHelper.addParam;
import static se.kth.iv1201.group4.recruitment.presentation.PresentationTestHelper.containsElements;
import static se.kth.iv1201.group4.recruitment.presentation.PresentationTestHelper.sendPostRequest;

@SpringJUnitWebConfig(initializers = ConfigDataApplicationContextInitializer.class)
@EnableAutoConfiguration
@ComponentScan(basePackages = { "se.kth.iv1201.group4.recruitment" })
@TestExecutionListeners(listeners = { DependencyInjectionTestExecutionListener.class, ForgotControllerTest.class })
public class ForgotControllerTest implements TestExecutionListener {
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private Filter springSecurityFilterChain;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).addFilter(springSecurityFilterChain).build();
    }

    @Test
    public void testForgotBadEmail() throws Exception {
        sendPostRequest(mockMvc, "/forgot", addParam("email", "nonexistent@email.com"))
                .andExpect(status().isOk()).andExpect(linkSendFailure());
    }

    @Test
    public void testForgot() throws Exception {
        Person p = new Person("Ben", "Johnsson", "ben.johnsson@gmail.com", "190607071234", "benjo", "abc123##");
        registerPerson(p, "abc123##").andExpect(status().is3xxRedirection()).andExpect(header().exists("Location"))
                .andExpect(header().string("Location", "success"));
        sendPostRequest(mockMvc, "/forgot", addParam("email", "ben.johnsson@gmail.com"))
                .andExpect(status().isOk()).andExpect(linkSendSuccess());
    }

    private ResultActions registerPerson(Person p, String pass) throws Exception {
        return sendPostRequest(mockMvc, "/register",
                addParam(
                        addParam(addParam(addParam(addParam(addParam("username", p.getUsername()), "name", p.getName()),
                                "surname", p.getSurname()), "email", p.getEmail()), "SSN", p.getSSN()),
                        "password", pass));
    }

    private ResultMatcher linkSendFailure() {
        return containsElements("main p:contains(That email address has not been registered)");
    }

    private ResultMatcher linkSendSuccess() {
        return containsElements("main p:contains(An email with a link to a reset page has been sent to the given email address)");
    }
}
