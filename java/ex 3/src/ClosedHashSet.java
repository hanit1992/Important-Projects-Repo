/**
 * this class represents a closed hash set. implements simple hash set.
 */
public class ClosedHashSet extends SimpleHashSet {

    //class constants

    /** The int that will increase or decrease the size accordingly */
    private static final int SIZE_ADDITION_OR_DEACRITION = 1;

    /** a number to start probing for the valid index  */
    private static final int INITIAL_INT_FOR_PROBING = 0;

    /** a new String object, that an deleted cell will point to  */
    private String DeletedPointer = new String("nothing here!");


    //data members

    /**
     * the array represent the hash table
     */
    private String[] table = new String[this.capacity()];

    /** a sign that will be used in the add and delete function,
     *  in order to mark the intention of the current calling - delete / add after rehashing */
    private boolean completeAfterRehashing = false;

    //class constructors

    /**
     * A default constructor. constructs a new empty open hash set.
     */
    public ClosedHashSet() {}

    /**
     * constructs a new empty open hash set, with the given upper and lower factors.
     * @param upperLoadFactor the given upper load factor for the new set
     * @param lowerLoadFactor the given lower load factor for the new set
     */
    public ClosedHashSet(float upperLoadFactor, float lowerLoadFactor) {
        //overrides the default values
        this.setLowerFactor(lowerLoadFactor);
        this.setUpperFactor(upperLoadFactor);
    }

    /**
     * constructs a new empty hash set, with some default values, and adds to the new set the given data list,
     * entry by entry.
     * @param data - the string array that needs to be added to the new set
     */
    public ClosedHashSet(java.lang.String[] data) {
        for (String element : data) {
            add(element);
        }
    }

    /**
     * Add a specified element to the set if it's not already in it.
     * @param newValue New value to add to the set
     * @return false if newValue already exists in the set
     */
    public boolean add(String newValue) {
        //in case the value is already in the set
        if (this.contains(newValue)){
            return false;}
        if (!completeAfterRehashing) {
            this.changeSize(+SIZE_ADDITION_OR_DEACRITION);
        }
        completeAfterRehashing = false;
        //checking the load factor
        float currentLoadFactor = this.size() / (float)capacity();
        //if the load factor is valid
        if ((currentLoadFactor <= this.getUpperFactor())) {
            int i = INITIAL_INT_FOR_PROBING;
            while (i<capacity()) {
                int index = calculatingIndex(newValue,i,capacity());
                //in case of a null cell or an empty string(which indicates an erased cell),add the element
                if (table[index] == null|| table[index]==DeletedPointer ) {
                    table[index] = newValue;
                    return true;
                }
                i++;
            }
        }
        //in case the load factor isn't valid
        rehashing(false);
        //recursive call to add the value after rehashing
        completeAfterRehashing = true;
        return add(newValue);
    }

    /**
     * this method sets a new capacity of a table, according to the place it was called from
     * then, she adds all the values of the set to a new table with the new capacity
     * @param deletion marks if the rehashing method was called from delete method or add method
     */
    private void rehashing(boolean deletion){
        int newCapacity;
        //divides or doubles the set accordingly.
        if (deletion){newCapacity = this.capacity()/ CAPACITY_CHANGING_INT;}
        else {newCapacity = this.capacity()* CAPACITY_CHANGING_INT;}
        //calls to add all the values to a new table
        addForRehashing(newCapacity);
        this.setCapacity(newCapacity);
    }

    /**
     * this method sets a new table and adds all the set elements to the new table
     * @param newCapacity the new capacity of the new table
     */
    private void addForRehashing(int newCapacity) {
        String[] newTable = new String[newCapacity];
        for (String list : table) {
            if (list == null) {
                continue;
            }
            int i = INITIAL_INT_FOR_PROBING;
            while (i < capacity()) {
                int index = calculatingIndex(list,i,newCapacity);
                //in case of a null cell or an empty string(which indicates an erased cell),add the element
                if (newTable[index] == null) {
                    newTable[index] = list;
                    break;
                }
                i++;
            }
        }
        this.table = newTable;
    }

    /**
     * this method calculates the index int the table by the rehashing function
     * @param searchVal marks if the rehashing method was called from delete method or add method
     * @param i the search iteration
     * @param capacity the capacity of the current table. notice we use it as a parameter because this function is
     *                 being used also by the rehashing method
     * @return the index
     */
    private int calculatingIndex(String searchVal,int i,int capacity){
        return (searchVal.hashCode() + (i+i*i)/2) & (capacity-1);
    }

    /**
     * Look for a specified value in the set.
     * @param searchVal Value to search for
     * @return true if searchVal is found in the set
     */
    public boolean contains(String searchVal) {
        int i = INITIAL_INT_FOR_PROBING;
        while (i<capacity()) {
            int index = calculatingIndex(searchVal,i,capacity());
            //checking if this cell is deleted by pointing to the deleted string, by reference
            if (table[index]==DeletedPointer){
                i++;
                continue;
            }
            //in case we didn't find it
            else if (table[index]== null){
                return false;
            }
            //in case we found it
            else if (table[index].equals(searchVal)) {
                return true;
            }
            i++;
        }
        return false;
    }

    /**
     * Remove the input element from the set.
     * @param toDelete Value to delete
     * @return True if toDelete is found and deleted
     */
    public boolean delete(String toDelete) {
        //returns false if doesn't contain the object
        if (!this.contains(toDelete)){
            return false;
        }
        float currentLoadFactor = this.size() / (float)capacity();
        //if the load factor is valid
        if ((currentLoadFactor > this.getLowerFactor())) {
            int i = INITIAL_INT_FOR_PROBING;
            while (i<capacity()) {
                int index = calculatingIndex(toDelete,i,capacity());
                //the object was found. erased by marking the cell with the delete pointer we set on the constructor.
                if(table[index]!=null && table[index].equals(toDelete)){
                    table[index]=DeletedPointer;
                    this.changeSize(-SIZE_ADDITION_OR_DEACRITION);
                    return true;
                }
                i++;
            }
        }
        //in case the load factor isn't valid
        else {
            rehashing(true);
            //then complete the deletion
            return delete(toDelete);
        }
        return true;
    }
}
