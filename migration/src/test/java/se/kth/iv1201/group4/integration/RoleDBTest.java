package se.kth.iv1201.group4.integration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import se.kth.iv1201.group4.integration.RoleDB;
import se.kth.iv1201.group4.integration.Role;
import org.junit.jupiter.api.Test;

class RoleDBTest {
    private final String QUERY = "SELECT * FROM role";

    @Test
    void testIfRoleTableReturnsValidValues(){
        for( Role role : RoleDB.getSingleton().getAllRoles()){
            assertNotNull(role.getRoleId());
            assertNotNull(role.getName());
        }
    }
}
