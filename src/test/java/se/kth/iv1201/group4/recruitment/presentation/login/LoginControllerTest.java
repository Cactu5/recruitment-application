package se.kth.iv1201.group4.recruitment.presentation.login;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.Matchers.empty;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestExecutionListener;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import se.kth.iv1201.group4.recruitment.domain.Applicant;
import se.kth.iv1201.group4.recruitment.domain.Person;
import se.kth.iv1201.group4.recruitment.repository.ApplicantRepository;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static se.kth.iv1201.group4.recruitment.presentation.PresentationTestHelper.addParam;
import static se.kth.iv1201.group4.recruitment.presentation.PresentationTestHelper.containsElements;
import static se.kth.iv1201.group4.recruitment.presentation.PresentationTestHelper.doesNotContainElements;
import static se.kth.iv1201.group4.recruitment.presentation.PresentationTestHelper.sendGetRequest;
import static se.kth.iv1201.group4.recruitment.presentation.PresentationTestHelper.sendPostRequest;

@SpringJUnitWebConfig(initializers = ConfigDataApplicationContextInitializer.class)
@EnableAutoConfiguration
@ComponentScan(basePackages = { "se.kth.iv1201.group4.recruitment" })
@TestExecutionListeners(listeners = { DependencyInjectionTestExecutionListener.class, LoginControllerTest.class })
public class LoginControllerTest implements TestExecutionListener {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private ApplicantRepository applicantRepo;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void canLogin(String page) throws Exception {
        Person ben = new Person("Ben", "Johnsson", "ben.johnsson@gmail.com", "190607071234", "KarlJohan", "abc123##");
        entityManager.persist(ben);
        entityManager.flush();

        Applicant applicantBen = new Applicant(ben);
        entityManager.persist(applicantBen);
        entityManager.flush();

        sendPostRequest(mockMvc, "{login.url}", addParam(addParam("username", "KarlJohan"), "holderName", "abc123##"))
                .andExpect(status().isOk())/*.andExpect(gotApplicantPage()).andExpect(doesNotContainElements("error"))*/; // TODO: 
    }

    private ResultMatcher gotApplicantPage() {
        return containsElements("main h1:contains(Make Application)", "main h1:contains(Make Application)");
    }
}
