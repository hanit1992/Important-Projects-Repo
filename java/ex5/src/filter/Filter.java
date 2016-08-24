package filter;

//class imports
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * this class holds all the filters object in the program, constructed by a given request. implements a
 * functional interface that his only abstract method implementation is being chosen in the factory, according to the
 * specific requested filer
 */
public class Filter implements Predicate<File> {

    //class data members

    /** the predicate object that the filter holds, and will execute 'test' by it's functionality*/
    private Predicate<File> theFilterChoice;

    /** a list of the current error message that the requested filter line yields. will be printed together*/
    private List<String> filterMessageList = new ArrayList<>();

    /**
     * class constructor
     * @param theCurrentFilterChoice a predicate object which according to it, we will implement the current filter
     *                               'test' method
     */
    public Filter (Predicate<File> theCurrentFilterChoice){
        this.theFilterChoice = theCurrentFilterChoice;
        this.bufferingMessagesFromFactory();
    }

    /**
     * will test the given file and decide weather to filter it or not
     * @param f a given file to filter
     * @return true if this file matches, false otherwise
     */
    public boolean test(File f){
        return theFilterChoice.test(f);
    }

    /**
     * will take messages that were made in the factory class and add them to the filter messages field
     */
    private void bufferingMessagesFromFactory(){
        for (String message: FilterFactory.getFilterMessageBuffer()){
            filterMessageList.add(message);
        }
        FilterFactory.getFilterMessageBuffer().clear();
    }

    /**
     * getting the filter message list of the object, in order to pass it to the section and print it together
     * with the other current relevant warnings
     * @return filter's error message list
     */
    public List<String> getTheFilterMessageList(){
        return filterMessageList;
    }
}