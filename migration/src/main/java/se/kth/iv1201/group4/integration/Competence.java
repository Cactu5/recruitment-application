package se.kth.iv1201.group4.integration;

/**
 * A representation of a row from the comptence table in the database
 *
 * @author Filip Garamvölgyi
 */
public class Competence {
    private final int competenceId;
    private final String name;

    Competence(final int competenceId, final String name){
        this.competenceId = competenceId;
        this.name = name;
    }

    public int getCompetenceId(){return competenceId;}
    public String getName(){return name;}
}
