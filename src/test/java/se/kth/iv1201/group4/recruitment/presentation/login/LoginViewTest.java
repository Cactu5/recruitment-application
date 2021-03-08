package se.kth.iv1201.group4.recruitment.presentation.login;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
@TestExecutionListeners(listeners = { DependencyInjectionTestExecutionListener.class, LoginViewTest.class })
class LoginViewTest implements TestExecutionListener {
    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MessageSource messageSource;

    private final Locale SWEDISH = new Locale("sv", "SE");
    private MockMvc mockMvc;

    @BeforeEach
    void setup() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void testIfViewRendersTheRequiredInputs() throws Exception {
        mockMvc.perform(get("/login")).andExpect(status().isOk())
                .andExpect(
                        content().string(containsString("<input id=\"username\" name=\"username\" type=\"text\" />")))
                .andExpect(content()
                        .string(containsString("<input id=\"password\" name=\"password\" type=\"password\" />")));
    }

    @Test
    void testIfViewRendersInSwedish() throws Exception {
        mockMvc.perform(get("/login?lang=sv")).andExpect(status().isOk())
                .andExpect(content()
                        .string(containsString(messageSource.getMessage("loginForm.label.username", null, SWEDISH))))
                .andExpect(content()
                        .string(containsString(messageSource.getMessage("loginForm.label.password", null, SWEDISH))));
    }

    @Test
    void testIfViewRendersInEnglish() throws Exception {
        mockMvc.perform(get("/login?lang=en")).andExpect(status().isOk())
                .andExpect(content().string(
                        containsString(messageSource.getMessage("loginForm.label.username", null, Locale.ENGLISH))))
                .andExpect(content().string(
                        containsString(messageSource.getMessage("loginForm.label.password", null, Locale.ENGLISH))));
    }

    @Test
    void testIfViewIsUsingThymeleafDecoration() throws Exception {
        mockMvc.perform(get("/login?lang=sv")).andExpect(status().isOk())
                .andExpect(content().string(containsString("| Login")))
                .andExpect(content().string(containsString("<h1>Rekrytering</h1>")))
                .andExpect(content().string(containsString("<footer>")));
    }

    @Test
    void testIfValidationMessagesEnglish() throws Exception {
        mockMvc.perform(get("/login?lang=en&error")).andExpect(status().isOk()).andExpect(content().string(
                containsString(messageSource.getMessage("loginForm.error.invalidLogin", null, Locale.ENGLISH))));
    }

    @Test
    void testIfValidationMessagesSwedish() throws Exception {
        mockMvc.perform(get("/login?lang=sv&error")).andExpect(content()
                .string(containsString(messageSource.getMessage("loginForm.error.invalidLogin", null, SWEDISH))));
    }
}
