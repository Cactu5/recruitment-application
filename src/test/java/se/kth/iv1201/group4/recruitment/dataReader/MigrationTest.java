package se.kth.iv1201.group4.recruitment.dataReader;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import se.kth.iv1201.group4.recruitment.dataReader.util.JsonToMapReader;
import se.kth.iv1201.group4.recruitment.domain.Applicant;
import se.kth.iv1201.group4.recruitment.domain.Availability;
import se.kth.iv1201.group4.recruitment.domain.Competence;
import se.kth.iv1201.group4.recruitment.domain.CompetenceProfile;
import se.kth.iv1201.group4.recruitment.domain.JobApplication;
import se.kth.iv1201.group4.recruitment.domain.Person;
import se.kth.iv1201.group4.recruitment.domain.Recruiter;
import se.kth.iv1201.group4.recruitment.repository.ApplicantRepository;
import se.kth.iv1201.group4.recruitment.repository.AvailabilityRepository;
import se.kth.iv1201.group4.recruitment.repository.CompetenceProfileRepository;
import se.kth.iv1201.group4.recruitment.repository.CompetenceRepository;
import se.kth.iv1201.group4.recruitment.repository.JobApplicationRepository;
import se.kth.iv1201.group4.recruitment.repository.JobStatusRepository;
import se.kth.iv1201.group4.recruitment.repository.LegacyUserRepository;
import se.kth.iv1201.group4.recruitment.repository.PersonRepository;
import se.kth.iv1201.group4.recruitment.repository.RecruiterRepository;

@DataJpaTest
class MigrationTest {
    @Autowired
    private PersonRepository personRepo;

    @Autowired
    private RecruiterRepository recruiterRepo;

    @Autowired
    private ApplicantRepository applicantRepo;

    @Autowired
    private CompetenceRepository competenceRepo;

    @Autowired
    private AvailabilityRepository availabilityRepo;

    @Autowired
    private CompetenceProfileRepository competenceProfileRepo;

    @Autowired
    private JobApplicationRepository jobApplicationRepo;

    @Autowired
    private JobStatusRepository jobStatusRepo;

    @Autowired
    private LegacyUserRepository legacyUserRepo;

    private List<Map<String, Object>> applicants, recruiters, availabilities, 
            competencies, competenceProfiles; 

    private Migration mig;

    @BeforeEach
    void mirgrateData(){
        JsonToMapReader reader = new JsonToMapReader();
        mig = new Migration();
        mig.Migrate();

        competenceRepo.saveAll(mig.getCompetencies());
        competenceRepo.flush();

        jobStatusRepo.saveAll(mig.getJobStatus());
        jobStatusRepo.flush();

        recruiterRepo.saveAll(mig.getRecruiters());
        recruiterRepo.flush();

        applicantRepo.saveAll(mig.getApplicants());
        applicantRepo.flush();

        legacyUserRepo.saveAll(mig.getLegacyUsers());
        legacyUserRepo.flush();

        jobApplicationRepo.saveAll(mig.getJobApplications());
        jobApplicationRepo.flush();

        competenceProfileRepo.saveAll(mig.getCompetenceProfiles());
        competenceProfileRepo.flush();

        availabilityRepo.saveAll(mig.getAvailabilities());
        availabilityRepo.flush();
        
        applicants = reader.convertJsonFileToMap("data/applicants.json");
        recruiters = reader.convertJsonFileToMap("data/recruiters.json");
        availabilities = reader.convertJsonFileToMap("data/availabilities.json");
        competencies = reader.convertJsonFileToMap("data/competencies.json");
        competenceProfiles = reader.convertJsonFileToMap("data/competenceProfiles.json");
    }

    @Test
    void testIfRecruitersMigrated(){
        assertTrue(recruiters.size() > 0);
        for(Map<String, Object> map : recruiters){
            Person p = personRepo.findPersonByUsername((String)map.get("username"));
            assertNotNull(p);

            Recruiter r = recruiterRepo.findRecruiterByPerson(p);
            assertNotNull(r);
            
            validatePerson(p, map);
        }
        assertEquals(recruiters.size(), recruiterRepo.findAll().size());
    }

    @Test
    void testIfApplicantsMigrated(){
        assertTrue(applicants.size() > 0);
        for(Map<String, Object> map : applicants){
            Person p = personRepo.findPersonByUsername((String)map.get("username"));
            assertNotNull(p);
        
            Applicant a = applicantRepo.findApplicantByPerson(p);
            assertNotNull(a);
            
            validatePerson(p, map);
        }
        assertEquals(applicants.size(), applicantRepo.findAll().size());
    }

    @Test
    void testIfCompetenciesMigrated(){
        assertTrue(competencies.size() > 0);
        for(Map<String, Object> map : competencies){
            Competence c = competenceRepo.findCompetenceByName((String)map.get("name"));

            assertNotNull(c);
            assertEquals(map.get("name"), c.getName());
        }
        assertEquals(competencies.size(), competenceRepo.findAll().size());
    }

    @Test
    void testIfUsersMigratedHaveBeenRegisteredAsLegacyUsers(){
        assertEquals(applicants.size() + recruiters.size(), 
                legacyUserRepo.findAll().size());
    }
   
    // More extensive testing is done in testIfJobApplicationsMigrated
    @Test
    void testIfAvailabilitiesMigrated(){
        assertTrue(availabilities.size() > 0);
        assertEquals(availabilities.size(), availabilityRepo.findAll().size());
    }
   
    // More extensive testing is done in testIfJobApplicationsMigrated
    @Test
    void testIfComptenceProfilesMigrated(){
        assertTrue(competenceProfiles.size() > 0);
        assertEquals(competenceProfiles.size(), competenceRepo.findAll().size());
    }
    
    @Test
    void testIfJobApplicationsMigrated(){
        List<JobApplication> JAs = jobApplicationRepo.findAll();
        assertTrue(JAs.size() > 0);
        for(Availability a : mig.getAvailabilities()){
            thereIsAJobApplicationWithMigratedAvailabilities(a, JAs);
        }
        for(CompetenceProfile cp : mig.getCompetenceProfiles()){
            thereIsAJobApplicationWithMigratedProfileCompetence(cp, JAs);
        }
    }

    private void thereIsAJobApplicationWithMigratedAvailabilities(Availability a, 
            List<JobApplication> JAs){
        for(JobApplication ja : JAs){
           if(ja.equals((JobApplication)a.getJobApplication()))
               return;
        }
        fail(); 
    }

    private void thereIsAJobApplicationWithMigratedProfileCompetence(CompetenceProfile cp,
            List<JobApplication> JAs){
        for(JobApplication ja : JAs){
           if(ja.equals((JobApplication)cp.getJobApplication()))
               return;
        }
        fail(); 
    }

    private void validatePerson(Person p, Map<String, Object> map){
        assertEquals(map.get("name"), p.getName());
        assertEquals(map.get("surname"), p.getSurname());
        assertEquals(map.get("ssn"), p.getSSN());
        assertEquals(map.get("username"), p.getUsername());
        assertEquals(map.get("email"), p.getEmail());

    }
}
