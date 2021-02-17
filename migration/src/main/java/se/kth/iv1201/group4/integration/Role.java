package se.kth.iv1201.group4.integration;

/**
 * Represents table in database.
 *
 * @author Filip Garamvölgyi
 */
public class Role{
    private final int roleId;
    private final String name;

    Role(final int roleId, final String name){
        this.roleId = roleId;
        this.name = name;
    }

    public int getRoleId(){return roleId;}
    public String getName(){return name;}
}
