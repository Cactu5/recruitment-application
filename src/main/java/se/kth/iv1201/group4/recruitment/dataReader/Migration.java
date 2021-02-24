package se.kth.iv1201.group4.recruitment.dataReader;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import se.kth.iv1201.group4.recruitment.dataReader.util.JsonToMapReader;
import se.kth.iv1201.group4.recruitment.domain.Applicant;
import se.kth.iv1201.group4.recruitment.domain.Availability;
import se.kth.iv1201.group4.recruitment.domain.Competence;
import se.kth.iv1201.group4.recruitment.domain.CompetenceProfile;
import se.kth.iv1201.group4.recruitment.domain.JobApplication;
import se.kth.iv1201.group4.recruitment.domain.JobStatus;
import se.kth.iv1201.group4.recruitment.domain.LegacyUser;
import se.kth.iv1201.group4.recruitment.domain.Person;
import se.kth.iv1201.group4.recruitment.domain.Recruiter;

/**
 * This class is used to take data of the old database in the form of JSON files
 * and converts it to the corresponding entities in the new database.
 *
 * @author Filip Garamvölgyi
 */
public class Migration {
    private List<LegacyUser> lus;
    private List<Competence> cs;
    private List<Recruiter> rs;
    private List<Applicant> appls;
    private List<JobStatus> jsss;
    private List<Availability> avs;
    private List<CompetenceProfile> cps;
    private List<JobApplication> jas;
    private final String DEFAULT_STATUS = "unhandled";
    private Map<Integer, Applicant> oldPersonToNewPerson;
    private Map<Integer, Competence> oldCompetenceToNewCompetence;
    private final JsonToMapReader jsonReader;
    private List<Map<String,Object>> applicants, recruiters, availabilities,
        competencies, competenceProfiles;

    /**
     * Creates instance of {@link Migration} class.
     */
    public Migration(){
        oldPersonToNewPerson = new HashMap<Integer, Applicant>();
        oldCompetenceToNewCompetence = new HashMap<Integer, Competence>();
        jsonReader = new JsonToMapReader();
        applicants = new ArrayList<Map<String,Object>>();
        recruiters = new ArrayList<Map<String,Object>>();
        availabilities = new ArrayList<Map<String,Object>>();
        competencies = new ArrayList<Map<String,Object>>();
        competenceProfiles = new ArrayList<Map<String,Object>>();
    }
    /**
     * Performs the convertion of old data to new data.
     */
    public void Migrate(){
        lus = new ArrayList<LegacyUser>();
        readData();
        migrateCompetencies();
        migrateRecruiters();
        migrateApplicants();
        migrateJobStatuses();
        migrateJobApplications();
    }
    public List<LegacyUser> getLegacyUsers(){return lus;}
    public List<Competence> getCompetencies(){return cs;}
    public List<Recruiter> getRecruiters(){return rs;}
    public List<Applicant> getApplicants(){return appls;}
    public List<JobStatus> getJobStatus(){return jsss;}
    public List<Availability> getAvailabilities(){return avs;}
    public List<CompetenceProfile> getCompetenceProfiles(){return cps;}
    public List<JobApplication> getJobApplications(){return jas;}

    private void readData(){
        applicants = jsonReader.convertJsonFileToMap("data/applicants.json");
        recruiters = jsonReader.convertJsonFileToMap("data/recruiters.json");
        availabilities = jsonReader.convertJsonFileToMap("data/availabilities.json");
        competencies = jsonReader.convertJsonFileToMap("data/competencies.json");
        competenceProfiles = jsonReader.convertJsonFileToMap("data/competenceProfiles.json");
    }
    private void migrateApplicants(){
        appls = new ArrayList<Applicant>();
        for(Map<String,Object> map : applicants){
            Person p = new Person(
                        (String)map.get("name"),
                        (String)map.get("surname"),
                        (String)map.get("email"),
                        (String)map.get("ssn"),
                        (String)map.get("username"),
                        (String)map.get("password")
                    );
            Applicant a = new Applicant(p);
            appls.add(a);
            lus.add(new LegacyUser(p));
            oldPersonToNewPerson.put((Integer)map.get("personId"), a);
        }
    }
    private void migrateRecruiters(){
        rs = new ArrayList<Recruiter>();
        for(Map<String,Object> map : recruiters){
            Person p = new Person(
                        (String)map.get("name"),
                        (String)map.get("surname"),
                        (String)map.get("email"),
                        (String)map.get("ssn"),
                        (String)map.get("username"),
                        (String)map.get("password")
                    );

            rs.add(new Recruiter(p));
            lus.add(new LegacyUser(p));
        }
    }
    private void migrateCompetencies(){
        cs = new ArrayList<Competence>();
        for(Map<String,Object> map : competencies){
            Competence c = new Competence((String)map.get("name")); 
            cs.add(c);
            oldCompetenceToNewCompetence.put((Integer)map.get("competenceId"), c);
        }
    }
    private void migrateJobStatuses(){
        jsss = new ArrayList<JobStatus>();
        jsss.add(new JobStatus(DEFAULT_STATUS));
    }
    private void migrateJobApplications(){
        for(Map<String, Object> map : applicants){
            int id = (Integer)map.get("personId");
            createJobApplication(oldPersonToNewPerson.get(id), findCompetenceProfiles(id),
                    findAvailabilities(id));
        }
    }

    private List<Map<String, Object>> findAvailabilities(int id){
        List<Map<String, Object>> as = new ArrayList<Map<String, Object>>();
        for(Map<String, Object> map : availabilities){
            if(((Integer) map.get("personId")).equals(id))
                as.add(map);
        }
        return as;
    }
    private List<Map<String, Object>> findCompetenceProfiles(int id){
        List<Map<String, Object>> cps = new ArrayList<Map<String, Object>>();
        for(Map<String, Object> map : competenceProfiles){
            if(((Integer) map.get("personId")).equals(id))
                cps.add(map);
        }
        return cps;
    }
    private void createJobApplication(Applicant applicant, List<Map<String, Object>> CPs,
            List<Map<String, Object>> as){
        avs = new ArrayList<Availability>();
        cps = new ArrayList<CompetenceProfile>();
        jas = new ArrayList<JobApplication>();
        JobStatus JS = jsss.get(0);
        JobApplication JA = new JobApplication(applicant, JS);
        jas.add(JA);
        
        for(Map<String, Object> map : as){
            Availability a = new Availability(
                    LocalDate.parse((String)map.get("fromDate")), 
                    LocalDate.parse((String)map.get("toDate")),JA);
            avs.add(a);
        }

        for(Map<String, Object> map : CPs){
            double yoeDouble = (double)map.get("yearsOfExperience");
            float yoe = (float)yoeDouble;
            CompetenceProfile CP = new CompetenceProfile(yoe,
                    oldCompetenceToNewCompetence.get(map.get("competenceId")), JA);  
            cps.add(CP);
        }
    }
}
