package ro.utcn.cpd.franklin;

import ro.utcn.cpd.franklin.exception.InvalidArgumentException;
import ro.utcn.cpd.franklin.exception.InvalidNumberOfArgumentsException;

/**
 * The entry point in the application.
 * User: Tamas
 * Date: 05/01/14
 * Time: 13:53
 */
public class Main {
    public static void main(String[] args) throws InvalidNumberOfArgumentsException, InvalidArgumentException {
        // validate arguments number
        if (args.length < 2) {
            throw new InvalidNumberOfArgumentsException();
        }

        int n = Integer.parseInt(args[0]);
        int k = Integer.parseInt(args[1]);

        // validate process number
        if (n < 2) {
            throw new InvalidArgumentException("Number of processes must be greater or equal than 2.");
        }

        // validate identity number
        if (k < 2) {
            throw new InvalidArgumentException("Identity number must be greater or equal than 2.");
        }

        FranklinLeaderElection fle = new FranklinLeaderElection(n, k);
        fle.simulate();
    }


}
