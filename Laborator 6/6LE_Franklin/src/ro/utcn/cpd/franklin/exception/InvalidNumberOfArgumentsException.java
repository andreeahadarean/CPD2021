package ro.utcn.cpd.franklin.exception;

/**
 * This class is used to throw a new exception when invalid number of arguments is given from the command line.
 * User: Tamas
 * Date: 05/01/14
 * Time: 14:00
 */
public class InvalidNumberOfArgumentsException extends Exception {
    public InvalidNumberOfArgumentsException() {
        super("Invalid number of arguments");
    }
}
