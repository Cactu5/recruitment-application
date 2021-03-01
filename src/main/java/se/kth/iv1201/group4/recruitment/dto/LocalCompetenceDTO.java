package se.kth.iv1201.group4.recruitment.dto;

/**
 * This LocalCompetenceDTO is a DTO. It contains all of the expected getters for this
 * specific type.
 * 
 * The LocalCompetenceDTO interface provides a method to get the name of the
 * competence.
 * 
 * @auther William Stackenäs
 * @version %I%
 */
public interface LocalCompetenceDTO {
    /**
     * Returns the name of the local competence.
     * 
     * @return the name of the local competence
     */
    public String getName();

    /**
     * Returns the langauge of name of the local competence.
     * 
     * @return the language of the name of the local compentence
     */
    public LanguageDTO getLanguage();

    /**
     * Returns the competence of the local competence.
     * 
     * @return the competence of the name of the local compentence
     */
    public CompetenceDTO getCompetence();
}
