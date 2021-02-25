package se.kth.iv1201.group4.integration;

/** 
 * Represents a row from the competence_profile table in the database
 *
 * @author Filip Garamvölgyi
 */
public class CompetenceProfile {
    private final int competenceProfileId;
    private final int personId;
    private final int competenceId;
    private final float yearsOfExperience;

    CompetenceProfile(final int competenceProfileId, final int personId,
            final int competenceId, final float yearsOfExperience){
        this.competenceProfileId = competenceProfileId;
        this.personId = personId;
        this.competenceId = competenceId;
        this.yearsOfExperience = yearsOfExperience;
    }

    public int getCompetenceProfileId(){return competenceProfileId;}
    public int getPersonId(){return personId;}
    public int getCompetenceId(){return competenceId;}
    public float getYearsOfExperience(){return yearsOfExperience;}
}
