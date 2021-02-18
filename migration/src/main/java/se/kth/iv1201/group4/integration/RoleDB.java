package se.kth.iv1201.group4.integration;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Represents the connection to the Role table of the database.
 *
 * @author Filip Garamvölgyi
 */
class RoleDB {
    private static final String QUERY = "SELECT * FROM role";
    private static final RoleDB singleton = new RoleDB();
    private final ConnectionDB conDB;
    
    private RoleDB(){
        conDB = new ConnectionDB();
    }
    
    /**
     * @return  returns the only instance of the class {@link RoleDB}
     */
    static RoleDB getSingleton(){return singleton;}
    
    /**
     * @return  returns every single row from the role table represented as
     *          an instance of {@link se.se.kth.iv1201.group4.integration.Role}
     */
    public List<Role> getAllRoles(){
        List<Role> roles = new ArrayList<Role>();
        HashMap<String, List<String>> res = conDB.getAllRows(QUERY, "role_id", "name");
        for(int i = 0; i < res.get("role_id").size(); i++){
            roles.add(
                new Role( Integer.valueOf(res.get("role_id").get(i)), res.get("name").get(i))
            );
        }
        return roles;
    }
}
