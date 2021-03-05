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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static se.kth.iv1201.group4.recruitment.presentation.PresentationTestHelper.addParam;
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
    public void testLoginSuccess() throws Exception {
        sendPostRequest(mockMvc, "/login", addParam(addParam("password", "user1Pass"), "username", "user1"))
                .andExpect(status().is3xxRedirection()).andExpect(header().exists("Location"))
                .andExpect(header().string("Location", "/success"));
    }

    @Test
    public void testLoginFail() throws Exception {
        sendPostRequest(mockMvc, "/login", addParam(addParam("password", "pass"), "username", "nonExistingUser"))
                .andExpect(status().is3xxRedirection()).andExpect(header().exists("Location"))
                .andExpect(header().string("Location", "/login?error"));
    }
}
