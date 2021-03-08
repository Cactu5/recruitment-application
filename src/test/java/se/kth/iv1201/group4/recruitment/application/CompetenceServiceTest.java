package se.kth.iv1201.group4.recruitment.application;

import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import se.kth.iv1201.group4.recruitment.domain.Competence;
import se.kth.iv1201.group4.recruitment.domain.CompetenceProfile;
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

    @Test
    void testToAddCompetenceProfile(){
        Competence c1 = new Competence();
        CompetenceProfile cp1 = new CompetenceProfile(1.5f, c1); 
        
        doReturn(cp1).when(competenceProfileRepo).saveAndFlush(any());
        
        CompetenceProfile cp2 = (CompetenceProfile)service.addCompetenceProfile(cp1);

        assertTrue(cp2 != null);
        assertSame(cp1, cp2);
    }

    @Test
    void testToGetLocalCompetencesByName(){
        Competence c1 = new Competence();
        Language lang1 = new Language("sv");
        Language lang2 = new Language("de");
        LocalCompetence lc1 = new LocalCompetence("djur", lang1, c1);
        LocalCompetence lc2 = new LocalCompetence("tier", lang2, c1);
        
        doReturn(Arrays.asList(lc1,lc2)).when(localCompetenceRepo).findLocalCompetenceByName("djur");
        
        List<LocalCompetence> lcs = service.getLocalCompetence("djur");
        
        assertFalse(lcs.isEmpty());
        assertEquals(2, lcs.size()); 
    }

    @Test
    void testToGetLocalCompetencesByLanguage(){
        Competence c1 = new Competence();
        Competence c2 = new Competence();
        Language lang1 = new Language("sv");
        Language lang2 = new Language("de");
        LocalCompetence lc1 = new LocalCompetence("djur", lang1, c1);
        LocalCompetence lc2 = new LocalCompetence("ägg", lang1, c2);
        LocalCompetence lc3 = new LocalCompetence("tier", lang2, c1);
        
        doReturn(Arrays.asList(lc1,lc2)).when(localCompetenceRepo).getLocalCompetenceByLanguage(lang1); 
        List<LocalCompetence> lcs1 = service.getLocalCompetences(lang1);

        doReturn(Arrays.asList(lc3)).when(localCompetenceRepo).getLocalCompetenceByLanguage(lang2);
        List<LocalCompetence> lcs2 = service.getLocalCompetences(lang2);
        
        assertFalse(lcs1.isEmpty());
        assertFalse(lcs2.isEmpty());
        assertEquals(2, lcs1.size()); 
        assertEquals(1, lcs2.size()); 
    }

    @Test
    void testToGetLocalCompetenceByLanguageAndName(){
        Competence c1 = new Competence();
        Language lang = new Language("sv");
        LocalCompetence lc1 = new LocalCompetence("djur", lang, c1);
        
        doReturn(lc1).when(localCompetenceRepo).findLocalCompetenceByLanguageAndCompetence(lang, c1);
        
        LocalCompetence lc2 = (LocalCompetence) service.getLocalCompetence(lang, c1);
        
        assertTrue(lc2 != null);
        assertSame(lc1, lc2); 
    }
}
