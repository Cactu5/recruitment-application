package se.kth.iv1201.group4.recruitment.util.error;

/**
 * This exception is thrown if a SSN trying to be used already 
 * exists.
 *
 * @author Filip Garamvölgyi
 */
public class SSNAlreadyExistsException extends RuntimeException {
    public SSNAlreadyExistsException(String msg){super(msg);}
}
