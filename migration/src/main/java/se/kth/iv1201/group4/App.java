package se.kth.iv1201.group4;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import se.kth.iv1201.group4.integration.*;

/**
 * Hello world!
 *
 */
public class App {
    private static final String FOLDER = "data/";
    public static void main( String[] args ){
        System.out.println( "Hello World!" );
        List<Person>[] persons =  PersonDB.getSingleton().getAllPersons();
        List<CompetenceProfile> competenceProfiles = CompetenceProfileDB.getSingleton().getAllCompetenceProfiles();
        List<Competence> competencies = CompetenceDB.getSingleton().getAllCompetencies();
        List<Availability> availabilities = AvailabilityDB.getSingleton().getAllAvailabilities();
        try {
            ObjectWriter pretty = new ObjectMapper().writerWithDefaultPrettyPrinter();
            ObjectMapper compact = new ObjectMapper();
            
            System.out.println("Recruiters:");
            writeDataToJSON("recruiters.json", compact.writeValueAsString(persons[0]));
            System.out.println( pretty.writeValueAsString(persons[0]) );            
            
            System.out.println("Applicants:");
            writeDataToJSON("applicants.json", compact.writeValueAsString(persons[1]));
            System.out.println( pretty.writeValueAsString(persons[1]) );            
            
            System.out.println("Competence profiles:");
            writeDataToJSON("competenceProfiles.json", compact.writeValueAsString(competenceProfiles));
            System.out.println( pretty.writeValueAsString(competenceProfiles) );     
            
            System.out.println("Competencies:");
            writeDataToJSON("competencies.json", compact.writeValueAsString(competencies));
            System.out.println( pretty.writeValueAsString(competencies) ); 
            
            System.out.println("Availabilties:");
            writeDataToJSON("availabilities.json", compact.writeValueAsString(availabilities));
            System.out.println( pretty.writeValueAsString(availabilities) ); 

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return;
    }
    private static void writeDataToJSON(String fileName, String data){
        try {
            FileWriter fw = new FileWriter(FOLDER + fileName);
            PrintWriter pw = new PrintWriter(fw);
            pw.print(data);
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
