//class imports
import java.util.Collection;

/**
 * this facade class wraps all the java collections objects, to implement SimpleSet interface
 */
public class CollectionFacadeSet implements SimpleSet {

    /** The current Collection being wrapped by the class */
    protected Collection<String> collection;

    /**
     * class constructor
     * Add a specified element to the set if it's not already in it.
     * @param collection a given java collection
     */
    public CollectionFacadeSet(Collection<String> collection){
        this.collection = collection;
    }

    /**
     * Add a specified element to the set if it's not already in it.
     * @param newValue New value to add to the set
     * @return False iff newValue already exists in the set
     */
    public boolean add(String newValue){
        if (collection.contains(newValue)){
            return false;
        }
        collection.add(newValue);
        return true;
    }

    /**
     * Look for a specified value in the set.
     * @param searchVal Value to search for
     * @return True iff searchVal is found in the set
     */
    public boolean contains(String searchVal) {
        return  (collection.contains(searchVal));
    }

    /**
     * Remove the input element from the set.
     * @param toDelete Value to delete
     * @return True iff toDelete is found and deleted
     */
    public boolean delete(String toDelete){
        return collection.remove(toDelete);
    }

    /**
     * @return The number of elements currently in the set
     */
    public int size(){
        return collection.size();
    }
}