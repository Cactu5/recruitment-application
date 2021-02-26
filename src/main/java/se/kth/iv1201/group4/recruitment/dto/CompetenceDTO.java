package se.kth.iv1201.group4.recruitment.dto;

/**
 * This CompetenceDTO is a DTO. It contains all of the expected getters for this
 * specific type.
 * 
 * The CompetenceDTO interface provides a method to get the name of the
 * competence.
 * 
 * @author Cactu5
 * @auther William Stackenäs
 * @version %I%
 */
public interface CompetenceDTO {
    /**
     * Returns the name of the competence.
     * 
     * @return the name of the competence
     */
    public String getName();

    /**
     * Returns the langauge of name of the competence.
     * 
     * @return the language of the name of the compentence
     */
    public LanguageDTO getLanguage();
}
