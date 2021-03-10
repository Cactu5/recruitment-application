package se.kth.iv1201.group4.recruitment.presentation.reset;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Locale;
import java.util.UUID;

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

import se.kth.iv1201.group4.recruitment.application.PersonService;
import se.kth.iv1201.group4.recruitment.domain.Applicant;
import se.kth.iv1201.group4.recruitment.domain.Person;

@SpringJUnitWebConfig(initializers = ConfigDataApplicationContextInitializer.class)
@EnableAutoConfiguration
@ComponentScan(basePackages = { "se.kth.iv1201.group4.recruitment" })
@TestExecutionListeners(listeners = { DependencyInjectionTestExecutionListener.class, ResetViewTest.class })
class ResetViewTest implements TestExecutionListener {
    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MessageSource messageSource;

    private MockMvc mockMvc;
    private final Locale ENGLISH = new Locale("en", "US");
    private final Locale SWEDISH = new Locale("sv", "SE");

    @Autowired
    PersonService personService;

    @BeforeEach
    void setup() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void testIfViewRendersTheRequiredInputs() throws Exception {
        UUID uuid = createResetPage();
        mockMvc.perform(get("/reset/" + uuid.toString())).andExpect(status().isOk())
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
        UUID uuid = createResetPage();
        mockMvc.perform(get("/reset/" + uuid.toString() + "?lang=sv")).andExpect(status().isOk())
                .andExpect(content().string(containsString(messageSource.getMessage("registerForm.label.email", null, SWEDISH))))
                .andExpect(content().string(containsString(messageSource.getMessage("registerForm.label.name", null, SWEDISH))))
                .andExpect(content().string(containsString(messageSource.getMessage("registerForm.label.surname", null, SWEDISH))))
                .andExpect(content().string(containsString(messageSource.getMessage("registerForm.label.username", null, SWEDISH))))
                .andExpect(content().string(containsString(messageSource.getMessage("registerForm.label.password", null, SWEDISH))))
                .andExpect(content().string(containsString(messageSource.getMessage("registerForm.label.ssn", null, SWEDISH))));
    }

    @Test
    void testIfViewRendersInEnglish() throws Exception {
        UUID uuid = createResetPage();
        mockMvc.perform(get("/reset/" + uuid.toString() + "?lang=en")).andExpect(status().isOk())
                .andExpect(content().string(containsString(messageSource.getMessage("registerForm.label.email", null, ENGLISH))))
                .andExpect(content().string(containsString(messageSource.getMessage("registerForm.label.name", null, ENGLISH))))
                .andExpect(content().string(containsString(messageSource.getMessage("registerForm.label.surname", null, ENGLISH))))
                .andExpect(content().string(containsString(messageSource.getMessage("registerForm.label.username", null, ENGLISH))))
                .andExpect(content().string(containsString(messageSource.getMessage("registerForm.label.password", null, ENGLISH))))
                .andExpect(content().string(containsString(messageSource.getMessage("registerForm.label.ssn", null, ENGLISH))));
    }

    @Test
    void testIfViewIsUsingThymeleafDecoration() throws Exception {
        UUID uuid = createResetPage();
        mockMvc.perform(get("/reset/" + uuid.toString() + "?lang=sv")).andExpect(status().isOk())
        .andExpect(content().string(containsString("| " + messageSource.getMessage("pages.reset.name", null, SWEDISH))))
        .andExpect(content().string(containsString("<h1>" + messageSource.getMessage("header.text.title", null, SWEDISH) + "</h1>")))
                .andExpect(content().string(containsString("<footer>")));
    }

    private UUID createResetPage() throws Exception {
        Person p = new Person("Ben", "Johnsson", "ben.bensson@gmail.com", "190507071234", "benjo2", "abc123##");
        try {
            personService.addApplicant(new Applicant(p));
        } catch (Exception e) { /* Do nothing if the applicant has already been added. */}

        return personService.addPersonToResetAccountList("ben.bensson@gmail.com");
    }
}
