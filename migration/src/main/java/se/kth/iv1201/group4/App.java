package se.kth.iv1201.group4;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import se.kth.iv1201.group4.integration.PersonDB;
import se.kth.iv1201.group4.integration.Person;

import se.kth.iv1201.group4.integration.CompetenceProfileDB;
import se.kth.iv1201.group4.integration.CompetenceProfile;

import se.kth.iv1201.group4.integration.CompetenceDB;
import se.kth.iv1201.group4.integration.Competence;

import se.kth.iv1201.group4.integration.AvailabilityDB;
import se.kth.iv1201.group4.integration.Availability;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ){
        System.out.println( "Hello World!" );
        List<Person>[] persons =  PersonDB.getSingleton().getAllPersons();
        List<CompetenceProfile> competenceProfiles = CompetenceProfileDB.getSingleton().getAllCompetenceProfiles();
        List<Competence> competencies = CompetenceDB.getSingleton().getAllCompetencies();
        List<Availability> availabilities = AvailabilityDB.getSingleton().getAllAvailabilities();
        try {
            ObjectMapper pretty = new ObjectMapper();
            System.out.println("Recruiters:");
            System.out.println( pretty.writerWithDefaultPrettyPrinter().writeValueAsString(persons[0]) );            
            System.out.println("Applicants:");
            System.out.println( pretty.writerWithDefaultPrettyPrinter().writeValueAsString(persons[1]) );            
            System.out.println("Competence profiles:");
            System.out.println( pretty.writerWithDefaultPrettyPrinter().writeValueAsString(competenceProfiles) );     
            System.out.println("Competencies:");
            System.out.println( pretty.writerWithDefaultPrettyPrinter().writeValueAsString(competencies) ); 
            System.out.println("Availabilties:");
            System.out.println( pretty.writerWithDefaultPrettyPrinter().writeValueAsString(availabilities) ); 

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return;
    }
}
