package ro.utcn.cpd.franklin.exception;

/**
 * This class is used to throw a new exception when a command line argument is invalid.
 * User: Tamas
 * Date: 05/01/14
 * Time: 14:04
 */
public class InvalidArgumentException extends Exception {

    public InvalidArgumentException(String message) {
        super(message);
    }
}
