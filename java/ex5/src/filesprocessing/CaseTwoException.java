package filesprocessing;

/**
 * second type of exception class, extends Exception. this exception will be thrown when a processor error occurs,
 * and will be printed immediately. the program will return the main method afterwards.
 */
public class CaseTwoException extends Exception {

    //class constants

    /** the string to be printed before the informative message when the error occurs*/
    private static final String ERROR = "ERROR: ";

    /** an excepted line on a checked exception class*/
    private static final long serialVersionUID = 1L;

    /**
     * class constructor. calling the super class constructor update the wanted message
     * @param informativeMessage the specific message with the specific error that occurred
     */
    public CaseTwoException(String informativeMessage){
        super(ERROR + informativeMessage);
    }
}
