//class imports
import java.util.Iterator;
import java.util.LinkedList;
/**
 * this class will wrap a linked list from the java collection. it will be used by the OpenHasSet class
 */
public class LinkedListWrapper {

    /** The current linkedList that the class wraps */
    private LinkedList<String> linkedList;

    /**
     * class constructor
     * @param initialData - the first data that needs to be added to the new list
     */
    public LinkedListWrapper(String initialData){
        linkedList = new LinkedList<>();
        linkedList.add(initialData);
    }

    /**
     * add an element to the list
     * @param element - a value that needs to be added to the list
     * @return true if the element was added
     */
    public boolean addToLinkedList(String element){
        return linkedList.add(element);}

    /**
     * delete from the list a given value
     * @param element the given value to delete from the list
     * @return true if it was found and deleted
     */
    public boolean deleteFromLinkedList(String element){
        return linkedList.remove(element);}

    /**
     * search for a given value in the list
     * @param element the value to search
     * @return true of the value was found
     */
    public boolean linkedListContains(String element){
        return linkedList.contains(element);}

    /**
     * creates an iterator to move on all the objects in the list
     * @return an iterator that iterates over the linked list
     */
    public Iterator<String> creatingAnIterator(){
        return linkedList.iterator();}

}
