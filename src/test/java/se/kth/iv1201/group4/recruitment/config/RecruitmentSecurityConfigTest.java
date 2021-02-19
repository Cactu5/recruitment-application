package se.kth.iv1201.group4.recruitment.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.TestExecutionListener;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringJUnitWebConfig(initializers = ConfigDataApplicationContextInitializer.class)
@EnableAutoConfiguration
@ComponentScan(basePackages = { "se.kth.iv1201.group4.recruitment" })
@TestExecutionListeners(listeners = { DependencyInjectionTestExecutionListener.class,
        RecruitmentSecurityConfigTest.class })
class RecruitmentSecurityConfigTest implements TestExecutionListener {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    void testContentProtectedBehindAuthentication() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/success-applicant"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

    @Test
    void testPageNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/dfk9d84jgdijgofdkg"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testLoginDoesNotRequireAuthentication() throws Exception {
        pageIsOk("/login");
    }

    @Test
    void testRegisterDoesNotRequireAuthentication() throws Exception {
        pageIsOk("/register");
    }

    private void pageIsOk(String page) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(page)).andExpect(MockMvcResultMatchers.status().isOk());
    }
}
