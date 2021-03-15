package se.kth.iv1201.group4.recruitment.presentation.error;

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
import static se.kth.iv1201.group4.recruitment.presentation.PresentationTestHelper.sendGetRequestWithStatusCode;
import static se.kth.iv1201.group4.recruitment.presentation.PresentationTestHelper.containsElements;

@SpringJUnitWebConfig(initializers = ConfigDataApplicationContextInitializer.class)
@EnableAutoConfiguration
@ComponentScan(basePackages = { "se.kth.iv1201.group4.recruitment" })
@TestExecutionListeners(listeners = { DependencyInjectionTestExecutionListener.class, ErrorHandlerTest.class })
public class ErrorHandlerTest implements TestExecutionListener {

    @Autowired
    private WebApplicationContext context;
    
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void testFallbackExceptionHandler() throws Exception {
        sendGetRequest(mockMvc, ExceptionThrowingController.URL)
            .andExpect(status().isInternalServerError()).andExpect(isGenericErrorPage());
    }

    @Test
    void testHttp404() throws Exception {
        sendGetRequestWithStatusCode(mockMvc, "error", 404).andExpect(status().isNotFound())
            .andExpect(isNotFoundErrorPage());

    }

    @Test
    void testUnhandledHttpStatus() throws Exception {
        sendGetRequestWithStatusCode(mockMvc, "error", 418).andExpect(status().isIAmATeapot())
            .andExpect(isGenericErrorPage());
    }

    private ResultMatcher isNotFoundErrorPage() {
        return containsElements("main p:contains(page that was requested does not)");
    }

    private ResultMatcher isGenericErrorPage() {
        return containsElements("main p:contains(Something went wrong)");
    }
}
