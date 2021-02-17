package se.kth.iv1201.group4.integration;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Connects to the competence_profile table of the DB.
 *
 * @author Filip Garamvölgyi
 */
public class CompetenceProfileDB {
    private static final String QUERY = "SELECT * FROM competence_profile";
    private static final CompetenceProfileDB singleton = new CompetenceProfileDB();
    private final ConncectionDB conDB;
    
    private CompetenceProfileDB(){
        conDB = new ConncectionDB();
    }

    /**
     * @return  returns the only instance of the class
     */
    public static CompetenceProfileDB getSingleton(){return singleton;}

    /**
     * @return  returns list of all rows in the competenceProfile table from the DB
     */
    public List<CompetenceProfile> getAllCompetenceProfiles(){
        List<CompetenceProfile> competenceProfiles = new ArrayList<CompetenceProfile>();
        HashMap<String, List<String>> res = conDB.getAllRows(QUERY, "competence_profile_id", "person_id",
            "competence_id","years_of_experience");
        for(int i = 0; i < res.get("competence_profile_id").size(); i++){
            int CPI = Integer.valueOf (res.get("competence_profile_id").get(i));
            int PI = Integer.valueOf (res.get("person_id").get(i));
            int CI = Integer.valueOf (res.get("competence_id").get(i));
            float YOE = Float.valueOf(res.get("years_of_experience").get(i));

            competenceProfiles.add(new CompetenceProfile(CPI, PI, CI, YOE));
        }
        return competenceProfiles;
    }
}
