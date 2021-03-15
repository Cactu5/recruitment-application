package se.kth.iv1201.group4.recruitment.dataReader;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import se.kth.iv1201.group4.recruitment.application.CompetenceService;
import se.kth.iv1201.group4.recruitment.application.JobApplicationService;
import se.kth.iv1201.group4.recruitment.application.LanguageService;
import se.kth.iv1201.group4.recruitment.application.PersonService;
import se.kth.iv1201.group4.recruitment.domain.Applicant;
import se.kth.iv1201.group4.recruitment.domain.Availability;
import se.kth.iv1201.group4.recruitment.domain.Competence;
import se.kth.iv1201.group4.recruitment.domain.CompetenceProfile;
import se.kth.iv1201.group4.recruitment.domain.JobApplication;
import se.kth.iv1201.group4.recruitment.domain.JobStatus;
import se.kth.iv1201.group4.recruitment.domain.Language;
import se.kth.iv1201.group4.recruitment.domain.LegacyUser;
import se.kth.iv1201.group4.recruitment.domain.LocalCompetence;
import se.kth.iv1201.group4.recruitment.domain.Recruiter;

/**
 * A migration component that will insert data in the JSON
 * files into the new database
 * 
 * @author Filip Garamvölgyi
 * @author William Stackenäs
 */
@Component
public class MigrationEvent {

    @Autowired
    private PersonService personService;

    @Autowired
    private JobApplicationService jobApplicationService;

    @Autowired
    private CompetenceService competenceService;

    @Autowired
    private LanguageService languageService;

    private static final Logger LOGGER = LoggerFactory.getLogger(MigrationEvent.class);

    /**
     * Runs when the application starts up and migrates
     * the data in the JSON files if activated using
     * the conditional if check
     * 
     * @param event The event that was triggered
     */
    @EventListener
    public void onContextLoaded(ContextRefreshedEvent event){
        LOGGER.debug("Migration starts");
        if(false){ // Set to true to activate migration on startup
            Migration mig = new Migration();
            mig.migrate();
            migrate(mig);
        } else LOGGER.info("Migration complete");
    }

    private void migrate(Migration mig){
        if(jobApplicationService.getJobStatus(mig.getJobStatus().get(0).getName()) == null){
            LOGGER.debug("Migration continues");
            migrateLanguages(mig.getLanguages());
            migrateJobStatuses(mig.getJobStatus());
            migrateCompetencies(mig.getCompetencies());
            migrateLocalCompetencies(mig.getLocalCompetencies());
            migrateRecruiters(mig.getRecruiters());
            migrateApplicants(mig.getApplicants());
            createLegacyUsers(mig.getLegacyUsers());
            migrateJobApplications(mig.getJobApplications());
            migrateCompetenceProfiles(mig.getCompetenceProfiles());
            migrateAvailabilities(mig.getAvailabilities());
        } else
            LOGGER.info("Migration has already taken place.");

    }

    private void migrateLanguages(List<Language> languages) {
        for(Language l : languages)
            languageService.addLanguage(l);
        LOGGER.info("Migrated languages");
    }
    private void migrateJobStatuses(List<JobStatus> jobStatuses){
        for(JobStatus js : jobStatuses){
            jobApplicationService.addJobStatus(js);
        }
        LOGGER.info("Migrated job statuses");
    }
    private void migrateCompetencies(List<Competence> competencies){
        for(Competence c : competencies)
            competenceService.addCompetence(c);
        LOGGER.info("Migrated competencies");
    }
    private void migrateLocalCompetencies(List<LocalCompetence> lCompetencies){
        for(LocalCompetence c : lCompetencies)
            competenceService.addLocalCompetence(c);
        LOGGER.info("Migrated local competencies");
    }
    private void migrateRecruiters(List<Recruiter> recruiters){
        for(Recruiter r : recruiters)
            personService.addRecruiter(r);
        LOGGER.info("Migrated recruiters");
    }
    private void migrateApplicants(List<Applicant> applicants){
        LOGGER.info("Migrating applicants");
        for(Applicant a : applicants)
            personService.addApplicant(a);
    }
    private void createLegacyUsers(List<LegacyUser> lus){
        for(LegacyUser lu : lus)
            personService.addLegacyUser(lu);
        LOGGER.info("Created legacy users");
    }
    private void migrateJobApplications(List<JobApplication> jas){
        for(JobApplication ja : jas)
            jobApplicationService.addJobApplication(ja);
        LOGGER.info("Migrated job applications");
    }
    private void migrateCompetenceProfiles(List<CompetenceProfile> cps){
        for(CompetenceProfile cp : cps)
            competenceService.addCompetenceProfile(cp);
        LOGGER.info("Migrated competence profiles");
    }
    private void migrateAvailabilities(List<Availability> avs){
        for(Availability a : avs)
            jobApplicationService.addAvailability(a);
        LOGGER.info("Migrated availabilities");
    }
}
