package se.kth.iv1201.group4.recruitment.application;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import se.kth.iv1201.group4.recruitment.domain.Competence;
import se.kth.iv1201.group4.recruitment.domain.CompetenceProfile;
import se.kth.iv1201.group4.recruitment.repository.CompetenceProfileRepository;
import se.kth.iv1201.group4.recruitment.repository.CompetenceRepository;

/**
 * A service for accessing or adding competence from and to the competence
 * repositories. Rolls back on all exceptions and supports current transactions,
 * or creates a new if none exist.
 * 
 * @author Filip Garamvölgyi
 * @version %I%
 */
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
@Service
public class CompetenceService {
    @Autowired
    private CompetenceRepository competenceRepo;

    @Autowired
    private CompetenceProfileRepository competenceProfileRepo;

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonService.class);

    /**
     * Adds a competence to the competence repository
     * 
     * @param c The comptence to add
     */
    public void addCompetence(Competence c) {
        if (c != null) {
            LOGGER.info(String.format("Trying to add %s as a competence.", c.getName()));
            competenceRepo.saveAndFlush(c);
        }
    }

    /**
     * Adds a competence profile to the competence profile repository
     * 
     * @param cp The competence profile to add
     */
    public void addCompetenceProfile(CompetenceProfile cp) {
        if (cp != null) {
            LOGGER.info("Adding competence profile");
            competenceProfileRepo.saveAndFlush(cp);
        }
    }

    /**
     * Returns a competence given the name.
     *
     * @param name  name of the Competence to return.
     * @return      returned competence given the name.      
     */
    public Competence getCompetence(String name) {
        if (name == null ) return null;

        Competence c = competenceRepo.findCompetenceByName(name);

        return c;
    }
    
    public List<CompetenceProfile> getAllCompetenceProfiles(){
        return competenceProfileRepo.findAll();
    }
}
