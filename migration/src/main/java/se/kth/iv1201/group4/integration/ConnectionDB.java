package se.kth.iv1201.group4.integration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Connects to a MariaDB/MySQL 
 *
 * @author Filip Garamvölgyi
 */
class ConncectionDB {
    private static final String HOST = "jdbc:mysql://localhost:3306/olddb";
    private static final String USER = "admin";
    private static final String PASSWORD = "password";


    public ConncectionDB(){}


    private Connection createConnection(){
        Connection con = null;
        try {
            con = DriverManager.getConnection(HOST,USER,PASSWORD);
        } catch (SQLException e) {
            System.out.println("Could not create connection to DB...");
            e.printStackTrace();
        } finally {
            return con;
        }
    }

    HashMap<String,List<String>> getAllRows(final String query, String...cols){
        HashMap<String,List<String>> rows = new HashMap<String,List<String>>(cols.length);
        try(Connection con = createConnection()){
            Statement stmt = con.createStatement();
            ResultSet results = stmt.executeQuery(query);
            
            for (String col : cols) rows.put(col, new ArrayList<String>()); 
            
            while(results.next()){
                for (String col : cols) rows.get(col).add(results.getString(col)); 
            }
            con.close();
        } catch(SQLException e){
            e.printStackTrace();
        } 
        return rows;
    }
}
