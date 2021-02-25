package se.kth.iv1201.group4.integration;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Connects to the availability table of the DB.
 *
 * @author Filip Garamvölgyi
 */
public class AvailabilityDB {
    private static final String QUERY = "SELECT * FROM availability";
    private static final AvailabilityDB singleton = new AvailabilityDB();
    private final ConnectionDB conDB;
    
    private AvailabilityDB(){
        conDB = new ConnectionDB();
    }

    /**
     * @return  returns the only instance of the class
     */
    public static AvailabilityDB getSingleton(){return singleton;}

    /**
     * @return  returns all rows of availability table from the database
     */
    public List<Availability> getAllAvailabilities(){
        List<Availability> availabilities = new ArrayList<Availability>();
        HashMap<String, List<String>> res = conDB.getAllRows(QUERY, "availability_id","person_id",
                "from_date","to_date");
        for(int i = 0; i < res.get("availability_id").size(); i++){
            int AI = Integer.valueOf (res.get("availability_id").get(i));
            int PI = Integer.valueOf (res.get("person_id").get(i));
            String fromDate = res.get("from_date").get(i);
            String toDate = res.get("to_date").get(i);

            availabilities.add(new Availability(AI, PI, fromDate, toDate));
        }
        return availabilities;
    }
}
