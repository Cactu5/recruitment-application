package se.kth.iv1201.group4.recruitment.presentation.success;

import javax.servlet.Filter;
import javax.servlet.http.HttpSession;

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
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static se.kth.iv1201.group4.recruitment.presentation.PresentationTestHelper.sendGetRequest;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static se.kth.iv1201.group4.recruitment.presentation.PresentationTestHelper.addParam;
import static se.kth.iv1201.group4.recruitment.presentation.PresentationTestHelper.sendPostRequest;
import static se.kth.iv1201.group4.recruitment.presentation.PresentationTestHelper.containsElements;

@SpringJUnitWebConfig(initializers = ConfigDataApplicationContextInitializer.class)
@EnableAutoConfiguration
@ComponentScan(basePackages = { "se.kth.iv1201.group4.recruitment" })
@TestExecutionListeners(listeners = { DependencyInjectionTestExecutionListener.class, SuccessControllerTest.class })
public class SuccessControllerTest implements TestExecutionListener {
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
    public void testIfRedirectedToLogin() throws Exception {
        sendGetRequest(mockMvc, "/success").andExpect(status().is3xxRedirection())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", "http://localhost/login"));
    }

    @Test
    public void testIfRedirectToSuccessApplicant() throws Exception {
        HttpSession session = createSessionApplicant();
        sendGetRequest(mockMvc, "/success", session).andExpect(status().is3xxRedirection())
                .andExpect(header().exists("Location")).andExpect(header().string("Location", "success-applicant"));
    }

    @Test
    public void testApplicantCanAccessSuccessApplicant() throws Exception {
        HttpSession session = createSessionApplicant();
        sendGetRequest(mockMvc, "/success-applicant", session).andExpect(status().isOk())
                .andExpect(isSuccessApplicantPage());
    }

    @Test
    public void testApplicantCanNotAccessSuccessRecruiter() throws Exception {
        HttpSession session = createSessionApplicant();
        sendGetRequest(mockMvc, "/success-recruiter", session).andExpect(status().isForbidden());
    }

    @Test
    public void testIfRedirectToSuccessRecruitert() throws Exception {
        HttpSession session = createSessionRecruiter();
        sendGetRequest(mockMvc, "/success", session).andExpect(status().is3xxRedirection())
                .andExpect(header().exists("Location")).andExpect(header().string("Location", "success-recruiter"));
    }

    @Test
    public void testRecruiterCanAccessSuccessRecruiter() throws Exception {
        HttpSession session = createSessionRecruiter();
        sendGetRequest(mockMvc, "/success-recruiter", session).andExpect(status().isOk())
                .andExpect(isSuccessRecruiterPage());
    }

    @Test
    public void testRecruiterCanNotAccessSuccessApplicant() throws Exception {
        HttpSession session = createSessionRecruiter();
        sendGetRequest(mockMvc, "/success-applicant", session).andExpect(status().isForbidden());
    }

    private HttpSession createSessionApplicant() throws Exception {
        return sendPostRequest(mockMvc, "/login", addParam(addParam("password", "user1Pass"), "username", "user1"))
                .andReturn().getRequest().getSession();
    }

    private HttpSession createSessionRecruiter() throws Exception {
        return sendPostRequest(mockMvc, "/login", addParam(addParam("password", "adminPass"), "username", "admin"))
                .andReturn().getRequest().getSession();
    }

    private ResultMatcher isSuccessApplicantPage() {
        return containsElements("main:contains(applicant)");
    }

    private ResultMatcher isSuccessRecruiterPage() {
        return containsElements("main:contains(recruiter)");
    }
}
