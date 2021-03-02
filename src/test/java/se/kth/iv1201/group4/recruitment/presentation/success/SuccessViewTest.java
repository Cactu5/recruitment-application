package se.kth.iv1201.group4.recruitment.presentation.success;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.hamcrest.CoreMatchers.containsString;

import org.junit.jupiter.api.AfterEach;
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

import se.kth.iv1201.group4.recruitment.application.CompetenceService;
import se.kth.iv1201.group4.recruitment.application.LanguageService;
import se.kth.iv1201.group4.recruitment.domain.Competence;
import se.kth.iv1201.group4.recruitment.domain.Language;
import se.kth.iv1201.group4.recruitment.domain.LocalCompetence;
import se.kth.iv1201.group4.recruitment.repository.CompetenceRepository;
import se.kth.iv1201.group4.recruitment.repository.LanguageRepository;
import se.kth.iv1201.group4.recruitment.repository.LocalCompetenceRepository;

@SpringJUnitWebConfig(initializers = ConfigDataApplicationContextInitializer.class)
@EnableAutoConfiguration
@ComponentScan(basePackages = { "se.kth.iv1201.group4.recruitment" })
@TestExecutionListeners(listeners = { DependencyInjectionTestExecutionListener.class, SuccessViewTest.class })
public class SuccessViewTest implements TestExecutionListener {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    CompetenceService competenceService;

    @Autowired
    LanguageService languageService;

    @Autowired
    CompetenceRepository competenceRepo;

    @Autowired
    LocalCompetenceRepository localCompetenceRepo;

    @Autowired
    LanguageRepository languageRepo;

    @BeforeEach
    void setup() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @AfterEach
    void tearDown() throws Exception {
        localCompetenceRepo.deleteAll();
        competenceRepo.deleteAll();
        languageRepo.deleteAll();
    }

    @Test
    void testIfViewSuccesRecruiterRenders() throws Exception {
        mockMvc.perform(get("/success-recruiter")).andExpect(status().isOk())
                .andExpect(content().string(containsString("This is the success page for recruiters.")));
    }

    @Test
    void testIfViewSuccesApplicantRenders() throws Exception {
        addLocalCompetences(new String[]{
            "en", "sv"
        }, new String[]{
            "running", "walking", "sleeping", "watching movies"
        }, new String[]{
            "löpning", "gång", "sömn", "titta på filmer"
        });
        mockMvc.perform(get("/success-applicant")).andExpect(status().isOk())
            .andExpect(content().string(containsString("This is the success page for applicants.")))
            .andExpect(content().string(containsString("<option>running</option>")))
            .andExpect(content().string(containsString("<option>walking</option>")))
            .andExpect(content().string(containsString("<option>sleeping</option>")))
            .andExpect(content().string(containsString("<option>watching movies</option>")));
    }

    /**
     * Initializes the database with local competencies
     * 
     * @param languages         An array containing the string representation of all languages that should be added to the database
     * @param compsForEachLang  An array containing all local competence names, for each language.
     * 
     * @details For example, if 3 languages and 9 competences should be added, then the languages array
     *          should have a length of 3 and compsForEachLang should contain 3 arrays, one for each language,
     *          where each array has a length of 9, corresponding to each competence.
     */
    void addLocalCompetences(String[] languages, String[]...compsForEachLang) {
        int langIndex = 0;
        int compIndex = 0;
        Language l;
        Competence c;
        ArrayList<Competence> comps = new ArrayList<Competence>(20);
        for (String[] localCompetencies : compsForEachLang) {
            if (langIndex >= languages.length)
                break;
            l = new Language(languages[langIndex]);
            languageService.addLanguage(l);
            for (String localCompetence : localCompetencies) {
                if (comps.size() <= compIndex) {
                    c = new Competence();
                    comps.add(c);
                    competenceService.addCompetence(c);
                }
                competenceService.addLocalCompetence(new LocalCompetence(localCompetence, l, comps.get(compIndex)));
                compIndex++;
            }
            compIndex = 0;
        }
    }
}
