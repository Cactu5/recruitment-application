package se.kth.iv1201.group4.recruitment.util.error;
/**
 * This exception is thrown if a username trying to be used already 
 * exists.
 *
 * @author Filip Garamvölgyi
 */
public class UsernameAlreadyExistsException extends RuntimeException {
    public UsernameAlreadyExistsException(String msg){
        super(msg);
    }
}
