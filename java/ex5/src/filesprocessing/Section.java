package filesprocessing;

//class imports
import filter.Filter;
import filter.FilterFactory;
import order.Order;
import order.OrderFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * holds each section defined by the given commands file of the program
 */
public class Section {

    //class data members

    /** the filter object of the current section */
    private Filter sectionFilter;

    /** the order object of the current section */
    private Order sectionOrder;

    /** a list of all the messages of the section, collected from both classes */
    private List<String> exceptionMessageBuffer = new ArrayList<>();

    /**
     * class constructor. creates a section by receiving the string for the order, the filter, and the starting line
     * of the section in the command file
     * @param filterString the given string for the filter to be constructed by
     * @param orderString the given string for the order to be constructed by
     * @param firstSectionLine the section's first line from the parser
     */
    public Section(String filterString, String orderString, int firstSectionLine){
        //updating the current section started line in the factories, in order to throw the correct line of an exception
        FilterFactory.setFilterStartingLine(firstSectionLine);
        OrderFactory.setOrderStartingLine(firstSectionLine+2);
        //getting the section's order and filter objects
        sectionOrder = OrderFactory.getTheOrder(orderString);
        sectionFilter = FilterFactory.getTheFilter(filterString);
        setToExceptionMessageBuffer();
    }

    /**
     * adding the buffered messages from both filter classes
     */
    private void setToExceptionMessageBuffer(){
        for (String message : sectionFilter.getTheFilterMessageList()){
            exceptionMessageBuffer.add(message);
        }
        for (String message: sectionOrder.getOrderMessageList()){
            exceptionMessageBuffer.add(message);
        }
    }

    /**
     * section filter getter
     * @return section's filter object
     */
    public Filter getSectionFilter(){
        return sectionFilter;
    }

    /**
     * section order getter
     * @return section's order object
     */
    public Order getSectionOrder(){
        return sectionOrder;
    }

    public List<String> getExceptionMessageBuffer(){
        return exceptionMessageBuffer;
    }
}
