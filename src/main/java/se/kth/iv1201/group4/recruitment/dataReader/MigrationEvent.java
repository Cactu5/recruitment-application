package se.kth.iv1201.group4.recruitment.dataReader;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import se.kth.iv1201.group4.recruitment.application.CompetenceService;
import se.kth.iv1201.group4.recruitment.application.JobApplicationService;
import se.kth.iv1201.group4.recruitment.application.PersonService;
import se.kth.iv1201.group4.recruitment.domain.Applicant;
import se.kth.iv1201.group4.recruitment.domain.Availability;
import se.kth.iv1201.group4.recruitment.domain.Competence;
import se.kth.iv1201.group4.recruitment.domain.CompetenceProfile;
import se.kth.iv1201.group4.recruitment.domain.JobApplication;
import se.kth.iv1201.group4.recruitment.domain.JobStatus;
import se.kth.iv1201.group4.recruitment.domain.LegacyUser;
import se.kth.iv1201.group4.recruitment.domain.Recruiter;

@Component
public class MigrationEvent {

    @Autowired
    private PersonService personService;

    @Autowired
    private JobApplicationService jobApplicationService;

    @Autowired
    private CompetenceService competenceService;

    private static final Logger LOGGER = LoggerFactory.getLogger(MigrationEvent.class);

    @EventListener
    public void onContextLoaded(ContextRefreshedEvent event){
        LOGGER.debug("Migration starts");
        if(false){
            Migration mig = new Migration();
            mig.migrate();
            migrate(mig);
        } else LOGGER.info("Migration complete");
    }

    private void migrate(Migration mig){
        if(jobApplicationService.getJobStatus(mig.getJobStatus().get(0).getName()) == null){
            LOGGER.debug("Migration continues");
            migrateJobStatuses(mig.getJobStatus());
            migrateCompetencies(mig.getCompetencies());
            migrateRecruiters(mig.getRecruiters());
            migrateApplicants(mig.getApplicants());
            createLegacyUsers(mig.getLegacyUsers());
            migrateJobApplications(mig.getJobApplications());
            migrateCompetenceProfiles(mig.getCompetenceProfiles());
            migrateAvailabilities(mig.getAvailabilities());
        } else LOGGER.info("Migration has laready taken place.");

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