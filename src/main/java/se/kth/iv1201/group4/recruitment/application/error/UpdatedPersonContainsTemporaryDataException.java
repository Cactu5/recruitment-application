package se.kth.iv1201.group4.recruitment.application.error;

/**
 * This exception is thrown if a {@link se.kth.iv1201.group4.recruitment.domain.LegacyUser}
 * is updated to follow the current Database schema but still contains temporary data
 * from the migration.
 *
 * @author Filip Garamvölgyi
 */
public class UpdatedPersonContainsTemporaryDataException extends Exception {
    public UpdatedPersonContainsTemporaryDataException(String errorMessage){
        super(errorMessage);
    }
}
