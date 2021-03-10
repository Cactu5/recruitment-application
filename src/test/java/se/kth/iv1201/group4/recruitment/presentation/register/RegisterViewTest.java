package se.kth.iv1201.group4.recruitment.presentation.register;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Locale;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.hamcrest.CoreMatchers.containsString;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestExecutionListener;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringJUnitWebConfig(initializers = ConfigDataApplicationContextInitializer.class)
@EnableAutoConfiguration
@ComponentScan(basePackages = { "se.kth.iv1201.group4.recruitment" })
@TestExecutionListeners(listeners = { DependencyInjectionTestExecutionListener.class, RegisterViewTest.class })
class RegisterViewTest implements TestExecutionListener {
    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MessageSource messageSource;

    private MockMvc mockMvc;
    private final Locale SWEDISH = new Locale("sv", "SE");

    @BeforeEach
    void setup() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void testIfViewRendersTheRequiredInputs() throws Exception {
        mockMvc.perform(get("/register")).andExpect(status().isOk())
                .andExpect(content().string(containsString("name=\"email\"")))
                .andExpect(content().string(containsString("name=\"name\"")))
                .andExpect(content().string(containsString("name=\"surname\"")))
                .andExpect(content().string(containsString("name=\"username\"")))
                .andExpect(content().string(containsString("name=\"password\"")))
                .andExpect(content().string(containsString("name=\"SSN\"")))
                .andExpect(content().string(containsString("type=\"submit\"")));
    }

    @Test
    void testIfViewRendersInSwedish() throws Exception {
        mockMvc.perform(get("/register?lang=sv")).andExpect(status().isOk())
                .andExpect(content()
                        .string(containsString(messageSource.getMessage("registerForm.label.email", null, SWEDISH))))
                .andExpect(content()
                        .string(containsString(messageSource.getMessage("registerForm.label.name", null, SWEDISH))))
                .andExpect(content()
                        .string(containsString(messageSource.getMessage("registerForm.label.surname", null, SWEDISH))))
                .andExpect(content()
                        .string(containsString(messageSource.getMessage("registerForm.label.username", null, SWEDISH))))
                .andExpect(content()
                        .string(containsString(messageSource.getMessage("registerForm.label.password", null, SWEDISH))))
                .andExpect(content()
                        .string(containsString(messageSource.getMessage("registerForm.label.ssn", null, SWEDISH))));
    }

    @Test
    void testIfViewRendersInEnglish() throws Exception {
        mockMvc.perform(get("/register?lang=en")).andExpect(status().isOk())
                .andExpect(content().string(
                        containsString(messageSource.getMessage("registerForm.label.email", null, Locale.ENGLISH))))
                .andExpect(content().string(
                        containsString(messageSource.getMessage("registerForm.label.name", null, Locale.ENGLISH))))
                .andExpect(content().string(
                        containsString(messageSource.getMessage("registerForm.label.surname", null, Locale.ENGLISH))))
                .andExpect(content().string(
                        containsString(messageSource.getMessage("registerForm.label.username", null, Locale.ENGLISH))))
                .andExpect(content().string(
                        containsString(messageSource.getMessage("registerForm.label.password", null, Locale.ENGLISH))))
                .andExpect(content().string(
                        containsString(messageSource.getMessage("registerForm.label.ssn", null, Locale.ENGLISH))));
    }

    @Test
    void testIfViewIsUsingThymeleafDecoration() throws Exception {
        mockMvc.perform(get("/register?lang=sv")).andExpect(status().isOk())
                .andExpect(content().string(containsString("| Registrering")))
                .andExpect(content().string(containsString("<h1>Rekrytering</h1>")))
                .andExpect(content().string(containsString("<footer>")));
    }

    @Test
    void testIfValidationMessagesEnglish() throws Exception {

        mockMvc.perform(post("/register?lang=en")).andExpect(status().isOk())
                .andExpect(content().string(
                        containsString(messageSource.getMessage("register.email.missing", null, Locale.ENGLISH))))
                .andExpect(content().string(
                        containsString(messageSource.getMessage("register.username.missing", null, Locale.ENGLISH))))
                .andExpect(content().string(
                        containsString(messageSource.getMessage("register.password.missing", null, Locale.ENGLISH))))
                .andExpect(content().string(
                        containsString(messageSource.getMessage("register.name.missing", null, Locale.ENGLISH))))
                .andExpect(content().string(
                        containsString(messageSource.getMessage("register.ssn.missing", null, Locale.ENGLISH))));
    }

    @Test
    void testIfValidationMessagesSwedish() throws Exception {
        mockMvc.perform(post("/register?lang=sv")).andExpect(status().isOk())
                .andExpect(content()
                        .string(containsString(messageSource.getMessage("register.email.missing", null, SWEDISH))))
                .andExpect(content()
                        .string(containsString(messageSource.getMessage("register.username.missing", null, SWEDISH))))
                .andExpect(content()
                        .string(containsString(messageSource.getMessage("register.password.missing", null, SWEDISH))))
                .andExpect(content()
                        .string(containsString(messageSource.getMessage("register.name.missing", null, SWEDISH))))
                .andExpect(content()
                        .string(containsString(messageSource.getMessage("register.ssn.missing", null, SWEDISH))));
    }
}
