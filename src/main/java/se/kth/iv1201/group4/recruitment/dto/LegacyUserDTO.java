package se.kth.iv1201.group4.recruitment.dto;

/**
 * This LegacyUserDTO is a DTO. It contains all of the expected getters for this
 * specific type.
 * 
 * The LegacyUserDTO interface provides methods to get the necessary information of
 * a LegacyUser. Which consists of: {@link se.kth.iv1201.group4.recruitment.domain.Person}.
 * 
 * @author Filip Garamvölgyi
 * @version %I%
 */
public interface LegacyUserDTO {
    public PersonDTO getPerson();
}
