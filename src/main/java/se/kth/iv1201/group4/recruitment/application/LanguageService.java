package se.kth.iv1201.group4.recruitment.application;

import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import se.kth.iv1201.group4.recruitment.domain.Language;
import se.kth.iv1201.group4.recruitment.dto.LanguageDTO;
import se.kth.iv1201.group4.recruitment.repository.LanguageRepository;

/**
 * A service for accessing or adding competence from and to the competence
 * repositories. Rolls back on all exceptions and supports current transactions,
 * or creates a new if none exist.
 * 
 * @author William Stackenäs
 * @author Filip Garamvölgyi
 * @version %I%
 */
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
@Service
public class LanguageService {
    @Autowired
    private LanguageRepository languageRepo;

    private static final Logger LOGGER = LoggerFactory.getLogger(LanguageService.class);

    private static final String DEFAULT_LANG = "en";

    /**
     * Adds a language to the language repository
     * 
     * @param l The language to add
     * @return returns the added {@link LanguageDTO} if successful otherwise null is returned
     */
    public LanguageDTO addLanguage(Language l) {
        if (l != null) {
            Language lang = languageRepo.saveAndFlush(l);
            LOGGER.info(String.format("Added %s as a language.", l.getName()));
            return lang;
        }
        return null;
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

    /**
     * Returns a language given a locale.
     *
     * @param name  locale of the Language to return.
     * @return      returned language given the locale. If no
     *              such language exists, the default language
     *              will be returned if it exists.      
     */
    public Language getLanguage(Locale locale)
    {
        Language def = getLanguage(DEFAULT_LANG);
        if (def == null) {
            LOGGER.warn("Default language does not exist in the database.");
        }
        String lString = locale.getLanguage();
        String[] splitLocale = lString.split("_");
        if (splitLocale.length < 1) {
            LOGGER.info("Locale with language \"" + lString + "\" was not properly understood. Default language will be returned.");
            return def;
        }
        
        lString = splitLocale[0].toLowerCase().replace("#", "").trim();
        LOGGER.debug("Language parsed from locale is \"" + lString + "\"");
        Language l = getLanguage(lString);
        if (l == null) {
            LOGGER.info("Language \"" + lString + "\" did not exist in the database. Default language will be returned.");
            return def;
        }
        return l;
    }
}
