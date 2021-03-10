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

import se.kth.iv1201.group4.recruitment.domain.Language;
import se.kth.iv1201.group4.recruitment.repository.LanguageRepository;

@SpringBootTest
public class LanguageServiceTest {
    @Autowired
    private LanguageService service;
    
    @MockBean
    private LanguageRepository languageRepo; 

    @Test
    void testToGetLanguageByName(){
        Language l1 = new Language("sv");
        doReturn(l1).when(languageRepo).findLanguageByName("sv");

        Language returnedLanguage = service.getLanguage("sv");
        
        assertTrue(returnedLanguage != null, "Language was not found");
        assertSame(returnedLanguage, l1);
    }

    @Test
    void testToGetAllLanguages(){
        Language l1 = new Language("sv");
        Language l2 = new Language("en");
        doReturn(Arrays.asList(l1,l2)).when(languageRepo).findAll();

        List<Language> returnedLanguages = service.getAllLanguages();
        
        assertFalse(returnedLanguages.isEmpty(), "Language was not found");
        assertEquals(2, returnedLanguages.size());
    }

    @Test
    void testToAddLanguage(){
        Language l1 = new Language("sv");
        
        doReturn(l1).when(languageRepo).saveAndFlush(any());

        Language l2 = (Language)service.addLanguage(l1);
 
        assertTrue(l2 != null);
        assertSame(l1, l2);
    }
}
