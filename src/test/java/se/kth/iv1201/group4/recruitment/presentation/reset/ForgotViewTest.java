package se.kth.iv1201.group4.recruitment.presentation.reset;

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
@TestExecutionListeners(listeners = { DependencyInjectionTestExecutionListener.class, ForgotViewTest.class })
class ForgotViewTest implements TestExecutionListener {
    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MessageSource messageSource;

    private MockMvc mockMvc;
    private final Locale ENGLISH = new Locale("en", "US");
    private final Locale SWEDISH = new Locale("sv", "SE");

    @BeforeEach
    void setup() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void testIfViewRendersTheRequiredInputs() throws Exception {
        mockMvc.perform(get("/forgot")).andExpect(status().isOk())
                .andExpect(
                        content().string(containsString("<input id=\"email\" type=\"text\" name=\"email\"")));
    }

    @Test
    void testIfViewRendersInSwedish() throws Exception {
        mockMvc.perform(get("/forgot?lang=sv")).andExpect(status().isOk())
                .andExpect(content().string(containsString(messageSource.getMessage("forgotForm.text.submit", null, SWEDISH))))
                .andExpect(content().string(containsString(messageSource.getMessage("forgotForm.text.alreadyHaveAnAccount", null, SWEDISH))));
    }

    @Test
    void testIfViewRendersInEnglish() throws Exception {
        mockMvc.perform(get("/forgot?lang=en")).andExpect(status().isOk())
                .andExpect(content().string(containsString(messageSource.getMessage("forgotForm.text.submit", null, ENGLISH))))
                .andExpect(content().string(containsString(messageSource.getMessage("forgotForm.text.alreadyHaveAnAccount", null, ENGLISH))));
    }

    @Test
    void testIfViewIsUsingThymeleafDecoration() throws Exception {
        mockMvc.perform(get("/forgot?lang=sv")).andExpect(status().isOk())
                .andExpect(content().string(containsString("| " + messageSource.getMessage("pages.forgot.name", null, SWEDISH))))
                .andExpect(content().string(containsString("<h1>" + messageSource.getMessage("header.text.title", null, SWEDISH) + "</h1>")))
                .andExpect(content().string(containsString("<footer>")));
    }
}
