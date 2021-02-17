package se.kth.iv1201.group4.integration;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RoleDB {
    private static final String QUERY = "SELECT * FROM role";
    private static final RoleDB singleton = new RoleDB();
    private final ConncectionDB conDB;
    
    private RoleDB(){
        conDB = new ConncectionDB();
    }

    public static RoleDB getSingleton(){return singleton;}
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
