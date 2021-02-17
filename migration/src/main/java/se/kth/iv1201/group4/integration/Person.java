package se.kth.iv1201.group4.integration;
/**
 * represents a row from the person table in the database
 *
 * @author Filip Garamvölgyi
 */
public class Person{
    private final int personId;
    private final String name;
    private final String surname;
    private final String email;
    private final String ssn;
    private final String username;
    private final String password;

    Person(final int personId, final String name, final String surname, final String email, 
            final String ssn, final String username, final String password){
        this.personId = personId;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.ssn = ssn;
        this.username = username;
        this.password = password;
    }

    public int getPersonId() { return personId; }
    public String getName(){ return name; }
    public String getSurname(){ return surname; }
    public String getEmail(){ return email; }
    public String getSsn() { return ssn; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
}
