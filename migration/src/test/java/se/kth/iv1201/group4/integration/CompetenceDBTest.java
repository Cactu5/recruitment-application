package se.kth.iv1201.group4.integration;

import static org.junit.jupiter.api.Assertions.*;

import se.kth.iv1201.group4.integration.*;

import org.junit.jupiter.api.Test;

class CompetenceDBTest {

    @Test
    void testIfCompetenceTableReturnsValidValues(){
        for( Competence c : CompetenceDB.getSingleton().getAllCompetencies()){
            assertNotNull(c.getCompetenceId());
            assertNotNull(c.getName());
        }
    }
}
