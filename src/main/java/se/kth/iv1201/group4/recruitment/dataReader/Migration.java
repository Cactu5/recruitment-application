package se.kth.iv1201.group4.recruitment.dataReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import se.kth.iv1201.group4.recruitment.application.PersonService;
import se.kth.iv1201.group4.recruitment.dataReader.util.JsonToMapReader;
import se.kth.iv1201.group4.recruitment.domain.Applicant;
import se.kth.iv1201.group4.recruitment.domain.Competence;
import se.kth.iv1201.group4.recruitment.domain.Person;
import se.kth.iv1201.group4.recruitment.domain.Recruiter;

public class Migration {
    private final PersonService personService;
    private Map<Integer, Person> oldPersonToNewPerson;
    private Map<Integer, Competence> oldCompetenceToNewCompetence;
    private final JsonToMapReader jsonReader;
    List<Map<String,Object>> applicants, recruiters, availabilities,
        competencies, competenceProfiles;

    public Migration(PersonService personService){
        this.personService = personService;

        oldPersonToNewPerson = new HashMap<Integer, Person>();
        oldCompetenceToNewCompetence = new HashMap<Integer, Competence>();
        jsonReader = new JsonToMapReader();
        applicants = new ArrayList<Map<String,Object>>();
        recruiters = new ArrayList<Map<String,Object>>();
        availabilities = new ArrayList<Map<String,Object>>();
        competencies = new ArrayList<Map<String,Object>>();
        competenceProfiles = new ArrayList<Map<String,Object>>();
    }
    public void Migrate(){
        readData();
        migrateCompetencies();
        migrateRecruiters();
        migrateApplicants();
    }
    private void readData(){
        applicants = jsonReader.convertJsonFileToMap("data/applicants.json");
        recruiters = jsonReader.convertJsonFileToMap("data/recruiters.json");
        availabilities = jsonReader.convertJsonFileToMap("data/availabilities.json");
        competencies = jsonReader.convertJsonFileToMap("data/competencies.json");
        competenceProfiles = jsonReader.convertJsonFileToMap("data/competenceProfiles.json");
    }
    private void migrateApplicants(){
        for(Map<String,Object> map : applicants){
            Person p = new Person(
                        (String)map.get("name"),
                        (String)map.get("surname"),
                        (String)map.get("email"),
                        (String)map.get("ssn"),
                        (String)map.get("username"),
                        (String)map.get("password")
                    );
            personService.addApplicant(new Applicant(p));
            oldPersonToNewPerson.put((Integer)map.get("personId"), 
                    personService.getPerson(p.getUsername(),p.getPassword()));
        }
    }
    private void migrateRecruiters(){
        for(Map<String,Object> map : recruiters){
            Person p = new Person(
                        (String)map.get("name"),
                        (String)map.get("surname"),
                        (String)map.get("email"),
                        (String)map.get("ssn"),
                        (String)map.get("username"),
                        (String)map.get("password")
                    );
            personService.addRecruiter(new Recruiter(p));
        }
    }
    private void migrateCompetencies(){
        for(Map<String,Object> map : competencies){
            Competence c = new Competence((String)map.get("name")); 
            //TODO: add via competence repository
        }
    }
}
