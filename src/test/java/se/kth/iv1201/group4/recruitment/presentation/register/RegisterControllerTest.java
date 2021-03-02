package se.kth.iv1201.group4.recruitment.presentation.register;

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
import static se.kth.iv1201.group4.recruitment.presentation.PresentationTestHelper.sendGetRequest;
import static se.kth.iv1201.group4.recruitment.presentation.PresentationTestHelper.sendPostRequest;

@SpringJUnitWebConfig(initializers = ConfigDataApplicationContextInitializer.class)
@EnableAutoConfiguration
@ComponentScan(basePackages = { "se.kth.iv1201.group4.recruitment" })
@TestExecutionListeners(listeners = { DependencyInjectionTestExecutionListener.class, RegisterControllerTest.class })
public class RegisterControllerTest implements TestExecutionListener {

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
        public void canRegisterAndLogin() throws Exception {
                sendPostRequest(mockMvc, "/register", addParam(
                                addParam(addParam(
                                                addParam(addParam(addParam("username", "KarlJohan"), "name", "Ben"),
                                                                "surname", "Johnsson"),
                                                "email", "ben.johnsson@gmail.com"), "SSN", "190607071234"),
                                "password", "abc123##")).andExpect(status().is3xxRedirection())
                                                .andExpect(header().exists("Location"))
                                                .andExpect(header().string("Location", "success"));
                sendGetRequest(mockMvc, "/logout").andExpect(status().is3xxRedirection());
                sendPostRequest(mockMvc, "/login", addParam(addParam("username", "KarlJohan"), "password", "abc123##"))
                                .andExpect(status().is3xxRedirection());
        }
}
