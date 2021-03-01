package se.kth.iv1201.group4.recruitment.application;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import se.kth.iv1201.group4.recruitment.domain.Language;
import se.kth.iv1201.group4.recruitment.repository.LanguageRepository;

/**
 * A service for accessing or adding competence from and to the competence
 * repositories. Rolls back on all exceptions and supports current transactions,
 * or creates a new if none exist.
 * 
 * @author William Stackenäs
 * @version %I%
 */
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
@Service
public class LanguageService {
    @Autowired
    private LanguageRepository languageRepo;

    private static final Logger LOGGER = LoggerFactory.getLogger(LanguageService.class);

    /**
     * Adds a language to the language repository
     * 
     * @param l The language to add
     */
    public void addLanguage(Language l) {
        if (l != null) {
            languageRepo.saveAndFlush(l);
            LOGGER.info(String.format("Added %s as a language.", l.getName()));
        }
    }

    /**
     * Returns a language given the name.
     *
     * @param name  name of the Language to return.
     * @return      returned language given the name.      
     */
    public Language getLanguage(String name) {
        if (name == null ) return null;

        Language l = languageRepo.findLanguageByName(name);

        return l;
    }
    
    public List<Language> getAllLanguages(){
        return languageRepo.findAll();
    }
}
