package se.kth.iv1201.group4.recruitment.util.error;
/**
 * This exception is thrown if a email trying to be used already 
 * exists.
 *
 * @author Filip Garamvölgyi
 */
public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String msg){
        super(msg);
    }
}
