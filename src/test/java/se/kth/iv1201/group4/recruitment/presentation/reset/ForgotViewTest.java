package se.kth.iv1201.group4.recruitment.presentation.reset;

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
@TestExecutionListeners(listeners = { DependencyInjectionTestExecutionListener.class, ForgotViewTest.class })
class ForgotViewTest implements TestExecutionListener {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void testIfViewRendersTheRequiredInputs() throws Exception {
        mockMvc.perform(get("/forgot")).andExpect(status().isOk())
                .andExpect(
                        content().string(containsString("<input id=\"email\" name=\"email\" type=\"text\" />")));
    }

    @Test
    void testIfViewRendersInSwedish() throws Exception {
        mockMvc.perform(get("/login?lang=sv")).andExpect(status().isOk())
                .andExpect(content().string(containsString("användarnamn")))
                .andExpect(content().string(containsString("lösenord")));
    }

    @Test
    void testIfViewRendersInEnglish() throws Exception {
        mockMvc.perform(get("/login?lang=en")).andExpect(status().isOk())
                .andExpect(content().string(containsString("Username")))
                .andExpect(content().string(containsString("Password")));
    }

    @Test
    void testIfViewIsUsingThymeleafDecoration() throws Exception {
        mockMvc.perform(get("/login?lang=sv")).andExpect(status().isOk())
                .andExpect(content().string(containsString("| Login")))
                .andExpect(content().string(containsString("<h1>Rekrytering</h1>")))
                .andExpect(content().string(containsString("<footer>")));
    }
}
