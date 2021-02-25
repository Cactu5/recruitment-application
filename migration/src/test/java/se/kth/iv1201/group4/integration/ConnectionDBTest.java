package se.kth.iv1201.group4.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import se.kth.iv1201.group4.integration.ConnectionDB;
import org.junit.jupiter.api.Test;

class ConnectionDBTest {
    private final String QUERY = "SELECT * FROM role";

    @Test
    void testDatabaseQueryReturnsAllRows(){
        ConnectionDB conDB = new ConnectionDB();
        assertEquals(2, conDB.getAllRows(QUERY,"role_id", "name").size());
    }
}
