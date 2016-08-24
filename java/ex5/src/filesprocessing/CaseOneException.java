package filesprocessing;

/**
 * first type of exception class, extends Exception this exception will be thrown when a processor warning occurs,
 * and will be printed before the matched files. the program will run normally with the default choice for the
 * specific object
 */
public class CaseOneException extends Exception {

    //class constants

    /** the string to be printed before the line of the problematic filter or order when the error occurs*/
    private static final String HEADER_STRING_FOR_WARNING = "Warning in line ";

    /** an excepted line on a checked exception class*/
    private static final long serialVersionUID = 1L;

    /**
     * class constructor. calling the super class constructor update the wanted message
     * @param errorLine the line that the error occurred in
     */
    public CaseOneException(String errorLine){
        super(HEADER_STRING_FOR_WARNING + errorLine);
    }
}
