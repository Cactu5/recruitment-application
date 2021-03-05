package se.kth.iv1201.group4.integration;

import static org.junit.jupiter.api.Assertions.*;

import se.kth.iv1201.group4.integration.*;
import org.junit.jupiter.api.Test;

class AvailabilityDBTest {
    private final String QUERY = "SELECT * FROM availability";

    @Test
    void testIfAvailabilityTableReturnsValidValues(){
        for( Availability a : AvailabilityDB.getSingleton().getAllAvailabilities()){
            assertNotNull(a.getAvailabilityId());
            assertNotNull(a.getToDate());
            assertNotNull(a.getFromDate());
            assertNotNull(a.getPersonId());
        }
    }

    @Test
    void testIfAvailbilityLinksToExistingPerson(){
        for( Availability a : AvailabilityDB.getSingleton().getAllAvailabilities()){
            realPersonId(a.getPersonId());
        }
    }

    //assertEquals(true,true) to show why it's successful
    private void realPersonId(int pid){
        for (Person p : PersonDB.getSingleton().getAllPersons()[0]) {
           if(p.getPersonId() == pid){
               assertEquals(true,true);
               return;
           } 
        }
        for (Person p : PersonDB.getSingleton().getAllPersons()[1]) {
           if(p.getPersonId() == pid){
               assertEquals(true,true);
               return;
           } 
        }
        fail("No person has this ID");
    }
}
