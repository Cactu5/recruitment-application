package se.kth.iv1201.group4.recruitment.presentation.register;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.hamcrest.CoreMatchers.containsString;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
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

    private MockMvc mockMvc;

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
                .andExpect(content().string(containsString("email")))
                .andExpect(content().string(containsString("namn")))
                .andExpect(content().string(containsString("efternamn")))
                .andExpect(content().string(containsString("användarnamn")))
                .andExpect(content().string(containsString("lösenord")))
                .andExpect(content().string(containsString("personnummer")));
    }

    @Test
    void testIfViewRendersInEnglish() throws Exception {
        mockMvc.perform(get("/register?lang=en")).andExpect(status().isOk())
                .andExpect(content().string(containsString("Email")))
                .andExpect(content().string(containsString("First Name")))
                .andExpect(content().string(containsString("Surname")))
                .andExpect(content().string(containsString("Username")))
                .andExpect(content().string(containsString("Password")))
                .andExpect(content().string(containsString("Social Security Number")));
    }

    @Test
    void testIfViewIsUsingThymeleafDecoration() throws Exception {
        mockMvc.perform(get("/register?lang=sv")).andExpect(status().isOk())
                .andExpect(content().string(containsString("| Registrering")))
                .andExpect(content().string(containsString("<h1>Rekrytering</h1>")))
                .andExpect(content().string(containsString("<footer>")));
    }
}
