package se.kth.iv1201.group4.recruitment.presentation.success;

import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static se.kth.iv1201.group4.recruitment.presentation.PresentationTestHelper.sendGetRequest;
import static se.kth.iv1201.group4.recruitment.presentation.PresentationTestHelper.addParam;
import static se.kth.iv1201.group4.recruitment.presentation.PresentationTestHelper.sendPostRequest;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static se.kth.iv1201.group4.recruitment.presentation.PresentationTestHelper.containsElements;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class SuccessControllerTest {
    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach 
    public void setup() throws Exception {
        mockMvc = MockMvcBuilders
            .webAppContextSetup(context)
            .apply(springSecurity())
            .build();
    }

    @Test
    public void testIfRedirectedToLogin() throws Exception {
        sendGetRequest(mockMvc, "/success").andExpect(status().is3xxRedirection())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", "http://localhost/login"));
    }

    @Test
    @WithMockUser(roles = {"APPLICANT"})
    public void testIfApplicantRedirectedToCorrespondingSuccessPage() throws Exception {
        sendGetRequest(mockMvc, "/success").andExpect(status().is3xxRedirection())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", "success-applicant"));
    }
    @Test
    @WithMockUser(roles = {"RECRUITER"})
    public void testIfRecruiterRedirectedToCorrespondingSuccessPage() throws Exception {
        sendGetRequest(mockMvc, "/success").andExpect(status().is3xxRedirection())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", "success-recruiter"));
    }
    @Test
    @WithMockUser(roles = {"LEGACY_USER"})
    public void testIfLegacyUserRedirectedToCorrespondingSuccessPage() throws Exception {
        sendGetRequest(mockMvc, "/success").andExpect(status().is3xxRedirection())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", "success-legacy-user"));
    }
    @Test
    @WithMockUser(roles = {"LEGACY_USER"})
    public void testIfLegacyUserIsNotAbleToAccessOtherSuccessPages() throws Exception {
        sendGetRequest(mockMvc, "/success-applicant")
            .andExpect(status().is4xxClientError());
        sendGetRequest(mockMvc, "/success-recruiter")
            .andExpect(status().is4xxClientError());
    }
    @Test
    @WithMockUser(roles = {"APPLICANT"})
    public void testIfApplicantIsNotAbleToAccessOtherSuccessPages() throws Exception {
        sendGetRequest(mockMvc, "/success-recruiter")
            .andExpect(status().is4xxClientError());
        sendGetRequest(mockMvc, "/success-legacy-user")
            .andExpect(status().is4xxClientError());
    }
    @Test
    @WithMockUser(roles = {"RECRUITER"})
    public void testIfRecruiterIsNotAbleToAccessOtherSuccessPages() throws Exception {
        sendGetRequest(mockMvc, "/success-applicant")
            .andExpect(status().is4xxClientError());
        sendGetRequest(mockMvc, "/success-legacy-user")
            .andExpect(status().is4xxClientError());
    }

    /**
     * Diffcult to test witout a real legacy user in the db. Was able to test it
     * locally but not sure how to do it in github actions
     */
    @Disabled
    @Test
    @WithMockUser(username = "Borg", roles = {"LEGACY_USER"})
    public void testIfReRegisterFormIsWorkingForLegacyUsers() throws Exception{
        sendPostRequest(mockMvc,"/success-legacy-user",addParam(
            "email","real@email.com",
            "name","Real",
            "surname","Person",
            "username","realUser",
            "password","qooqWan123!",
            "SSN","199210101010"
            )
        ).andExpect(status().is3xxRedirection())
        .andExpect(header().exists("Location"))
        .andExpect(header().string("Location", "logout"));
    }
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
