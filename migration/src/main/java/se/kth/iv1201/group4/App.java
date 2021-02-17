package se.kth.iv1201.group4;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import se.kth.iv1201.group4.integration.RoleDB;
import se.kth.iv1201.group4.integration.Role;
/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ){
        System.out.println( "Hello World!" );
        List<Role> roles =  RoleDB.getSingleton().getAllRoles();
        try {
            System.out.println( new ObjectMapper().writeValueAsString(roles) );            
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return;
    }
}
