package order;

//class imports
import filesprocessing.CaseOneException;
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * a factory class, that construct an order object, according to the requested order by the command file
 */
public abstract class OrderFactory {

    //class constants

    /** optional case for order request, provided by the command file */
    private static final String SIZE = "size";

    /** optional case for order request, provided by the command file */
    private static final String TYPE = "type";

    /** optional case for order request, provided by the command file */
    private static final String ABS = "abs";

    /** optional case for order request, provided by the command file */
    private static final String EMPTY_STRING = " ";

    /** the length of a list with no ending but the empty string */
    private static final int LENGTH_OF_LIST__WITH_NO_ENDING = 0;

    /** possible spot of the order name*/
    private static final int SPOT_OF_ORDER_NAME = 0;

    // class data members

    /** the warning messages list to be printed before the section's matched files */
    private static List <String> orderMessageBuffer = new ArrayList<>();

    /** the line that the current order starts with */
    private static int orderStartingLine;

    /**
     * returns an order object, implemented by the requested order by the command file
     * @param requestedOrder the string of the requested order
     * @return an order matched object
     */
    public static Order getTheOrder(String requestedOrder){
        String orderName;
        boolean reverse = false;
        //in case there is only one value
        if (!requestedOrder.contains("#")){
            orderName = requestedOrder;
        }
        //in case there are more
        else{
            String[] stringOfOrderGivenValues = requestedOrder.split("#");
            orderName = stringOfOrderGivenValues[SPOT_OF_ORDER_NAME];
            reverse = true;
        }

        //creating the comparator object for the default cases
        Comparator<File> absDefaultAndEmptyCase = (File file1, File file2) ->
                file1.getAbsolutePath().compareTo(file2.getAbsolutePath());
        switch (orderName){
            case SIZE:
                Comparator<File> sizeComperation = (File file1, File file2)-> (file1.length()>file2.length()? 1 :
                file1.length()==file2.length() ? 0 : -1);
                //in case reverse didn't appear in the order request
                if (!reverse){
                    return new Order(sizeComperation,false);
                }
                return new Order(sizeComperation,true);

            case ABS:
                //in case reverse didn't appear in the order request
                if (!reverse){
                    return new Order(absDefaultAndEmptyCase,false);
                }
                return new Order(absDefaultAndEmptyCase,true);

            case TYPE:
                //using method reference instead of lambda, due to calling of un existing function of a class
                Comparator<File> typeComperation = OrderFactory::getTheComperatorInt;
                //in case reverse didn't appear in the order request
                if (!reverse){
                    return new Order(typeComperation,false);
                }
                return new Order(typeComperation,true);

            //in case we received an empty line after the order announcement
            case EMPTY_STRING:
                return new Order(absDefaultAndEmptyCase,false);

            //creating the regular sorter for any other case
            default:
                try {
                    invalidNameForOrder();
                }
                catch (CaseOneException e){
                    orderMessageBuffer.add(e.getMessage());
                }
                return new Order(absDefaultAndEmptyCase,false);
        }
    }

    /**
     * in case the given name of the order was invalid, throws an exception to be cached at the previous function,
     * in order to continue the program with the default order choice
     * @throws CaseOneException will print a warning with the problematic line, and continue normally after wards,
     * by ignoring the problematic part and replacing it by the default way
     */
    private static void invalidNameForOrder()throws CaseOneException{
        String stringLineNumber = String.valueOf(orderStartingLine+1);
        throw new CaseOneException(stringLineNumber);
    }

    /**
     * returns an int symbols the value of the comperation between two files types
     * @param file1 the first file to be compared
     * @param file2 the second file to be compared
     * @return negative number if the first is smaller, 0 if they were even, 0 otherwise
     */
    private static int getTheComperatorInt(File file1, File file2){
        String file1Ending;
        String file2Ending;
        //splitting the file names by dot
        String[] file1NameString = file1.getName().split("\\.");
        String[] file2NameString = file2.getName().split("\\.");
        //if the name doesn't contain the dot, the ending is the empty string
        if (!file1.getName().contains(".")){
            file1Ending = " ";
        }
        //if the length is 0, also no ending but empty string
        else if (file1NameString.length==LENGTH_OF_LIST__WITH_NO_ENDING){
            file1Ending = " ";
        }
        //in case the ending is different
        else {
            file1Ending =  file1NameString[file1NameString.length-1];
        }
        //same for the second file
        if (!file2.getName().contains(".")){
            file2Ending = " ";
        }
        else if (file2NameString.length==LENGTH_OF_LIST__WITH_NO_ENDING){
            file2Ending = " ";
        }
        else {
            file2Ending = file2NameString[file2NameString.length-1];
        }
        return file1Ending.compareTo(file2Ending);
    }

    public static List<String> getOrderMessageBuffer(){
        return orderMessageBuffer;
    }

    public static void setOrderStartingLine(int newVal){
        orderStartingLine = newVal;
    }
}