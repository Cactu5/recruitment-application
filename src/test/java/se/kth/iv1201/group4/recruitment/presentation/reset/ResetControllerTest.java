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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import se.kth.iv1201.group4.recruitment.application.PersonService;
import se.kth.iv1201.group4.recruitment.domain.Person;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static se.kth.iv1201.group4.recruitment.presentation.PresentationTestHelper.addParam;
import static se.kth.iv1201.group4.recruitment.presentation.PresentationTestHelper.sendGetRequest;
import static se.kth.iv1201.group4.recruitment.presentation.PresentationTestHelper.sendPostRequest;

import java.util.UUID;

@SpringJUnitWebConfig(initializers = ConfigDataApplicationContextInitializer.class)
@EnableAutoConfiguration
@ComponentScan(basePackages = { "se.kth.iv1201.group4.recruitment" })
@TestExecutionListeners(listeners = { DependencyInjectionTestExecutionListener.class, ResetControllerTest.class })
public class ResetControllerTest implements TestExecutionListener {

    @Autowired
    private WebApplicationContext context;
    @Autowired
    private Filter springSecurityFilterChain;
    @Autowired
    PersonService personService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).addFilter(springSecurityFilterChain).build();
    }

    @Test
    public void get404WithEmptyUuid() throws Exception {
        sendGetRequest(mockMvc, "/reset").andExpect(status().isNotFound());
    }

    @Test
    public void get404WithBadUuid() throws Exception {
        sendGetRequest(mockMvc, "/reset/fsdljkhfslfhl").andExpect(status().isNotFound());
    }

    @Test
    public void get404WithNonexistentUuid() throws Exception {
        sendGetRequest(mockMvc, "/reset/00000000-0000-0000-0000-000000000000").andExpect(status().isNotFound());
    }

    @Test
    public void canReset() throws Exception {
        Person p = new Person("Ben", "Johnsson", "ben.bensson@gmail.com", "190507071234", "benjo2", "abc123##");
        registerPerson(p, "abc123##").andExpect(status().is3xxRedirection()).andExpect(header().exists("Location"))
                .andExpect(header().string("Location", "success"));
        sendGetRequest(mockMvc, "/logout").andExpect(status().is3xxRedirection());

        UUID uuid = personService.addPersonToResetAccountList("ben.bensson@gmail.com");
        p = new Person(p.getName(), p.getSurname(), p.getEmail(), p.getSSN(), "benjo3", p.getPassword());

        resetPerson(p, "abc123##", uuid).andExpect(status().is3xxRedirection()).andExpect(header().exists("Location"))
            .andExpect(header().string("Location", "/success"));
    }

    private ResultActions resetPerson(Person p, String pass, UUID uuid) throws Exception {
        return sendPostRequest(mockMvc, "/reset/" + uuid.toString(),
                addParam(
                        addParam(addParam(addParam(addParam(addParam("username", p.getUsername()), "name", p.getName()),
                                "surname", p.getSurname()), "email", p.getEmail()), "SSN", p.getSSN()),
                        "password", pass));
    }

    private ResultActions registerPerson(Person p, String pass) throws Exception {
        return sendPostRequest(mockMvc, "/register",
                addParam(
                        addParam(addParam(addParam(addParam(addParam("username", p.getUsername()), "name", p.getName()),
                                "surname", p.getSurname()), "email", p.getEmail()), "SSN", p.getSSN()),
                        "password", pass));
    }
}
