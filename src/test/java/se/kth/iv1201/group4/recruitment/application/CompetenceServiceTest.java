package se.kth.iv1201.group4.recruitment.application;

import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import se.kth.iv1201.group4.recruitment.domain.Competence;
import se.kth.iv1201.group4.recruitment.domain.Language;
import se.kth.iv1201.group4.recruitment.domain.LocalCompetence;
import se.kth.iv1201.group4.recruitment.repository.CompetenceProfileRepository;
import se.kth.iv1201.group4.recruitment.repository.CompetenceRepository;
import se.kth.iv1201.group4.recruitment.repository.LocalCompetenceRepository;

@SpringBootTest
public class CompetenceServiceTest {
    @Autowired
    private CompetenceService service;

    @MockBean
    private CompetenceRepository competenceRepo;

    @MockBean
    private CompetenceProfileRepository competenceProfileRepo;

    @MockBean
    private LocalCompetenceRepository localCompetenceRepo; 

    @Test
    void testToAddCompetence(){
        Competence c1 = new Competence();
        doReturn(c1).when(competenceRepo).saveAndFlush(any());
        
        Competence c2 = (Competence) service.addCompetence(c1);
        
        assertTrue(c2 != null);
        assertSame(c1, c2); 
    }

    @Test
    void testToAddLocalCompetence(){
        Competence c1 = new Competence();
        Language lang = new Language("sv");
        LocalCompetence lc1 = new LocalCompetence("djur", lang, c1);
        doReturn(lc1).when(localCompetenceRepo).saveAndFlush(any());
        
        LocalCompetence lc2 = (LocalCompetence) service.addLocalCompetence(lc1);
        
        assertTrue(lc2 != null);
        assertSame(lc1, lc2); 
    }
}
