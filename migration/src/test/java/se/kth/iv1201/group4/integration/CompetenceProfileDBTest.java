package se.kth.iv1201.group4.integration;

import static org.junit.jupiter.api.Assertions.*;

import se.kth.iv1201.group4.integration.*;

import org.junit.jupiter.api.Test;

class CompetenceProfileDBTest {

    @Test
    void testIfCompetenceProfileTableReturnsValidValues(){
        for( CompetenceProfile c : CompetenceProfileDB.getSingleton()
                .getAllCompetenceProfiles()){
            assertNotNull(c.getCompetenceId());
            assertNotNull(c.getCompetenceProfileId());
            assertNotNull(c.getPersonId());
            assertNotNull(c.getYearsOfExperience());
        }
    }
    @Test
    void testIfCompetenceProfileLinksToExistingPerson(){
        for( CompetenceProfile c : CompetenceProfileDB.getSingleton()
                .getAllCompetenceProfiles()){
            realPersonId(c.getPersonId());
        }
    }

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
