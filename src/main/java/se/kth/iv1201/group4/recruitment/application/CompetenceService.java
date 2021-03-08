package se.kth.iv1201.group4.recruitment.application;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import se.kth.iv1201.group4.recruitment.domain.Competence;
import se.kth.iv1201.group4.recruitment.domain.CompetenceProfile;
import se.kth.iv1201.group4.recruitment.domain.Language;
import se.kth.iv1201.group4.recruitment.domain.LocalCompetence;
import se.kth.iv1201.group4.recruitment.dto.CompetenceDTO;
import se.kth.iv1201.group4.recruitment.dto.LocalCompetenceDTO;
import se.kth.iv1201.group4.recruitment.repository.CompetenceProfileRepository;
import se.kth.iv1201.group4.recruitment.repository.CompetenceRepository;
import se.kth.iv1201.group4.recruitment.repository.LocalCompetenceRepository;

/**
 * A service for accessing or adding competence from and to the competence
 * repositories. Rolls back on all exceptions and supports current transactions,
 * or creates a new if none exist.
 * 
 * @author Filip Garamvölgyi
 * @author William Stackenäs
 * @version %I%
 */
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
@Service
public class CompetenceService {
    @Autowired
    private CompetenceRepository competenceRepo;

    @Autowired
    private LocalCompetenceRepository localCompetenceRepo;

    @Autowired
    private CompetenceProfileRepository competenceProfileRepo;

    private static final Logger LOGGER = LoggerFactory.getLogger(CompetenceService.class);

    /**
     * Adds a competence to the competence repository
     * 
     * @param c The comptence to add
     * @return returns the {@link CompetenceDTO} if successful otherwise null is returned.
     */
    public CompetenceDTO addCompetence(Competence c) {
        if (c != null) {
            CompetenceDTO dto = competenceRepo.saveAndFlush(c);
            LOGGER.info(String.format("Added competence."));
            return dto;
        }
        return null;
    }

    /**
     * Adds a local competence to the competence repository
     * 
     * @param c The local comptence to add
     * @return returns the {@link LocalCompetenceDTO} if successful otherwise null is returned.
     */
    public LocalCompetenceDTO addLocalCompetence(LocalCompetence c) {
        if (c != null) {
            LocalCompetenceDTO dto = localCompetenceRepo.saveAndFlush(c);
            LOGGER.info(String.format("Added local competence."));
            return dto;
        }
        return null;
    }

    /**
     * Adds a competence profile to the competence profile repository
     * 
     * @param cp The competence profile to add
     */
    public void addCompetenceProfile(CompetenceProfile cp) {
        if (cp != null) {
            competenceProfileRepo.saveAndFlush(cp);
            LOGGER.info("Added competence profile");
        }
    }

    /**
     * Returns local competences given the name.
     *
     * @param name  name of the LocalCompetences to return.
     * @return      returned local competences given the name.      
     */
    public List<LocalCompetence> getLocalCompetence(String name) {
        if (name == null ) return null;

        return localCompetenceRepo.findLocalCompetenceByName(name);
    }
    
    public List<CompetenceProfile> getAllCompetenceProfiles(){
        return competenceProfileRepo.findAll();
    }

    /**
     * Returns all local competences given the language.
     *
     * @param l     language of the {@link LocalCompetence}s to return
     * @return      returned list of local competences given the language.      
     */
    public List<LocalCompetence> getLocalCompetences(Language l){
        if (l == null ) return new ArrayList<LocalCompetence>();

        return localCompetenceRepo.getLocalCompetenceByLanguage(l);
    }

    /**
     * Returns a local competence given the language and competence.
     *
     * @param l     language of the given {@link LocalCompetence} to return
     * @param c     competence of the given {@link LocalCompetence} to return
     * @return      returned local competence given the language and competence.      
     */
    public LocalCompetence getLocalCompetence(Language language, Competence competence) {
        if (language == null || competence == null) return null;

        LocalCompetence c = localCompetenceRepo.findLocalCompetenceByLanguageAndCompetence(language, competence);

        return c;
    }
}
