package se.kth.iv1201.group4.integration;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Connects to the competence table of the DB.
 *
 * @author Filip Garamvölgyi
 */
public class CompetenceDB {
    private static final String QUERY = "SELECT * FROM competence";
    private static final CompetenceDB singleton = new CompetenceDB();
    private final ConnectionDB conDB;
    
    private CompetenceDB(){
        conDB = new ConnectionDB();
    }

    /**
     * @return  returns the only instance of the class
     */
    public static CompetenceDB getSingleton(){return singleton;}

    /**
     * @return  returns list of all rows in the competence table from the database
     */
    public List<Competence> getAllCompetencies(){
        List<Competence> competencies = new ArrayList<Competence>();
        HashMap<String, List<String>> res = conDB.getAllRows(QUERY, "competence_id","name");
        for(int i = 0; i < res.get("competence_id").size(); i++){
            int CI = Integer.valueOf (res.get("competence_id").get(i));
            String name = res.get("name").get(i);

            competencies.add(new Competence(CI,name));
        }
        return competencies;
    }
}
