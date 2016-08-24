package order;

//class imports
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * this class holds the order object of each section in the program. implements Comparator
 */
public class Order implements Comparator<File> {

    //class data members

    /** the comparator object the class uses, in order to implement the specific order object*/
    private Comparator<File> compareObject;

    /** marks the reverse request by the command file*/
    private boolean isReversed;

    /** the list of the warning messages the the specific order message yield*/
    private List<String> orderMessageList = new ArrayList<>();

    /**
     * class constructor. receives a comparator object and a boolean value for the reverse requested functionality,
     * and creates a new order object for the processor to use
     * @param currentComperableObject the object that implements the compare functionality of the order
     * @param isReversed marks if it's reversed
     */
    public Order(Comparator<File> currentComperableObject, boolean isReversed){
        this.compareObject = currentComperableObject;
        this.isReversed = isReversed;
        //update the object message list by receiving the warnings that the factory collected
        this.bufferingMessagesFromFactory();
    }

    /**
     * the method that is being used by the comparator object for specific comparation
     * @param file1 the first file to be compared
     * @param file2 second file to be compared
     * @return negative number if the first is smaller, 0 for equality, positive number otherwise
     */
    public int compare(File file1,File file2){
        return compareObject.compare(file1,file2);
    }

    /**
     * get's the reversed boolean mark
     * @return boolean sign for reversed
     */
    public boolean getIsReversed(){
        return isReversed;
    }

    /**
     * getting the warning list of the object
     * @return the warning list
     */
    public List<String> getOrderMessageList(){
        return orderMessageList;
    }

    /**
     * getting the messages from the factory and adds them to the current order message list
     */
    private void bufferingMessagesFromFactory(){
        for (String message: OrderFactory.getOrderMessageBuffer()){
            orderMessageList.add(message);
        }
        //clearing the factory list
        OrderFactory.getOrderMessageBuffer().clear();
    }
}
