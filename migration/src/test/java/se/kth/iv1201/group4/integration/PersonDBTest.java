package se.kth.iv1201.group4.integration;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import se.kth.iv1201.group4.integration.*;

import org.junit.jupiter.api.Test;

class PersonDBTest {
    private final String QUERY = "SELECT * FROM availability";
    List<String> fakeEmails = new ArrayList<String>();
    List<String> fakeUsernames = new ArrayList<String>();
    List<String> fakeSsns = new ArrayList<String>();

    @Test
    void testIfPersonTableReturnsValidValues(){
        for( Person p : PersonDB.getSingleton().getAllPersons()[1]){
            assertNotNull(p.getName());
            assertNotNull(p.getSurname());
            assertNotNull(p.getUsername());
            assertNotNull(p.getPersonId());
            assertNotNull(p.getSSN());
            assertNotNull(p.getEmail());
            assertTrue(p.getEmail().contains("@"));
            assertEquals(p.getSSN().length(), 12);
        }
        for( Person p : PersonDB.getSingleton().getAllPersons()[0]){
            assertNotNull(p.getName());
            assertNotNull(p.getSurname());
            assertNotNull(p.getUsername());
            assertNotNull(p.getPersonId());
            assertNotNull(p.getSSN());
            assertNotNull(p.getEmail());
            assertTrue(p.getEmail().contains("@"));
            assertEquals(p.getSSN().length(), 12);
        }
    }

    @Test
    void testIfPlaceholderValuesAreValid(){
        for( Person p : PersonDB.getSingleton().getAllPersons()[1]){
            if(p.getEmail().contains("@fakemegafake.email"))
                inFakeEmails(p.getEmail());
            if(p.getUsername().contains("badUserFakeUser"))
                inFakeUsernames(p.getUsername());
            if(p.getSSN().substring(0,2).equals("00"))
                inFakeSsns(p.getSSN());
        }
        for( Person p : PersonDB.getSingleton().getAllPersons()[0]){
            if(p.getEmail().contains("@fakemegafake.email"))
                inFakeEmails(p.getEmail());
            if(p.getUsername().contains("badUserFakeUser"))
                inFakeUsernames(p.getUsername());
            if(p.getSSN().substring(0,2).equals("00"))
                inFakeSsns(p.getSSN());
        }
    }
    
    private void inFakeEmails(String email){
        for(String fake : fakeEmails){
            assertNotEquals(email,fake);
        }
        fakeEmails.add(email);
    }
    private void inFakeUsernames(String username){
        for(String fake : fakeUsernames){
            assertNotEquals(username,fake);
        }
        fakeUsernames.add(username);
    }
private void inFakeSsns(String ssn){
        for(String fake : fakeSsns){
            assertNotEquals(ssn,fake);
        }
        fakeSsns.add(ssn);
    }
}
