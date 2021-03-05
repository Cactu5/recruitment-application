package se.kth.iv1201.group4.integration;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Connects to the person table of the DB.
 *
 * @author Filip Garamvölgyi
 */
public class PersonDB {
    private static final String QUERY = "SELECT * FROM person";
    private static final PersonDB singleton = new PersonDB();
    private final ConnectionDB conDB;
    
    private int missingEmails;
    private int missingUsernames;
    private int missingSsns;
    
    private PersonDB(){
        conDB = new ConnectionDB();
        missingEmails = 0;
        missingUsernames = 0;
        missingSsns = 0;
    }

    /**
     * @return  returns the only instance of the class
     */
    public static PersonDB getSingleton(){return singleton;}

    /**
     * @return  returns an array containing all persons on the database
     *          index 0 are recruiters and index 1 is applicants.
     */
    public List<Person>[] getAllPersons(){
        ArrayList<Person>[] persons = new ArrayList[]{new ArrayList<Person>(), new ArrayList<Person>()};
        HashMap<String, List<String>> res = conDB.getAllRows(QUERY, "person_id", "name",
            "surname","ssn","email","password","role_id","username");
        for(int i = 0; i < res.get("person_id").size(); i++){
            String name = res.get("name").get(i);
            String surname = res.get("surname").get(i);
            String email = res.get("email").get(i);
            String ssn = res.get("ssn").get(i);
            String password = res.get("password").get(i);
            String username = res.get("username").get(i);
            Person p = new Person(
                Integer.valueOf(res.get("person_id").get(i)),
                name == null ? "missingName" : name,
                surname == null ? "missingSurname" : surname,
                email == null ? String.format("fake%05d@fakemegafake.email", missingEmails++) : email,
                ssn == null ? String.format("0%05d006060",missingSsns++) : ssn.replace("-", ""),
                username == null ? String.format("badUserFakeUser%05d", missingUsernames++) : username, 
                password == null ? "NoPassword123!" : password);

            // This is equal to a recruit
            if(res.get("role_id").get(i).equals("1")) persons[0].add(p);
            else persons[1].add(p);
        }
        return persons;
    }
}
