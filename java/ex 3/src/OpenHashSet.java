//class imports
import java.util.Iterator;
/**
 * this class represents an open hash set. implements simple hash set.
 */
public class OpenHashSet extends SimpleHashSet {

    /** The int that will increase or decrease the size accordingly */
    private static final int SIZE_ADDITION_OR_DECREASION = 1;

    //class data members

    /** The wrapped linked list that will be used for this open set */
    private LinkedListWrapper[] table = new LinkedListWrapper[this.capacity()];

    /** a sign that will be used in the add function,
     *  in order to mark the intention of the current calling - delete / add after rehashing */
    private boolean completeAfterRehashing = false;

    //class constructors

    /**
     * A default constructor. constructs a new empty open hash set.
     */
    public OpenHashSet(){}

    /**
     * constructs a new empty open hash set, with the given upper and lower factors.
     * @param upperLoadFactor a given upper factor for the new set
     * @param lowerLoadFactor a given lower factor for the new set
     */
    public OpenHashSet(float upperLoadFactor,float lowerLoadFactor){
        //overrides the default values
        this.setUpperFactor(upperLoadFactor);
        this.setLowerFactor(lowerLoadFactor);
    }

    /**
     * constructs a new empty hash set, with some default values, and adds to the new set the given data list,
     * entry by entry.
     * @param data a string with the data that needs to be added to the new set
     */
    public OpenHashSet(java.lang.String[] data){
        //adding all the data to the new set
        for(String entry:data){
            add(entry);
        }
    }

    /**
     * implementing SimpleHashSet method.
     * Add a specified element to the set if it's not already in it.
     * @param newValue New value to add to the set
     * @return False if newValue already exists in the set
     */
    public boolean add(String newValue){
        if (this.contains(newValue)){return false;}
        if (!completeAfterRehashing){
            this.changeSize(+SIZE_ADDITION_OR_DECREASION);
        }
        //checking the load factor
        float currentLoadFactor = this.size()/(float)this.capacity();
        //checking the current load Factor
        if((currentLoadFactor <= this.getUpperFactor())){
            //producing the index of the current value, by the key
            int index = rehash(newValue,this.capacity());
            //if there isn't any value, create a new list object and put the requested value
            if(this.table[index]==null){
                this.table[index]= new LinkedListWrapper(newValue);
            }
            //if there is a list already, add the requested value
            else{
                this.table[index].addToLinkedList(newValue);
            }
            return true;
        }
        //and in case the load factor is out of boundries, rehash
        else {
            rehashing(false);
            //marks the current state of the call for the add function
            completeAfterRehashing = true;
            //recursive call. this time, after we allocated a new table
            add(newValue);
            completeAfterRehashing = false;
        }
        return true;
    }

    /**
     * creates a new index by the rehashing function, to place or locate in the hash table.
     * @param newValue New value to add to the set
     * @param capacity the current capacity of the table
     * @return False if newValue already exists in the set
     */
    private int rehash(String newValue,float capacity){
        return newValue.hashCode()&(((int)capacity)-1);
    }

    /**
     * being called from the rehashing method - place all the current data in our set in a new table
     * @param newCapacity the new capacity of the new table that we wish to put all the data in
     */
    private void addForRehashing(int newCapacity){
        //crating the new table
        LinkedListWrapper[] newTable = new LinkedListWrapper[newCapacity];
        //going through all the buckets in the set, and through each value in the bucket
        for (LinkedListWrapper list : table){
            if (list==null){
                continue;
            }
            //build an iterator for each of the link lists
            Iterator<String> listIterator = list.creatingAnIterator();
            //activate the iterator and add the founded values to the string array
            while (listIterator.hasNext()){
                String item = listIterator.next();
                int index = rehash(item,newCapacity);
                if (newTable[index]==null){
                    newTable[index]=new LinkedListWrapper(item);
                }
                newTable[index].addToLinkedList(item);
            }
        }
        this.table = newTable;
    }

    /**
     * this method creates a new table, bigger one, and than rehashes the table.
     * in the end, she calls to add the value that his addition was requested before the rehashing process.
     * @param deletion marks if the rehashing method was called from delete method or add method -
     *                 true if was called from delete
     */
    private void rehashing(boolean deletion){
        int newCapacity;
        //in each case of calling, divides or double the capacity accordingly. notice - we divide it by 2, hence the
        //result will always stay an int, so there wouldn't be any run time error (initial int is even)
        if (deletion){newCapacity = this.capacity()/ CAPACITY_CHANGING_INT;}
        else {newCapacity = this.capacity()* CAPACITY_CHANGING_INT;}
        //than adds all the values to a new table
        addForRehashing(newCapacity);
        this.setCapacity(newCapacity);
    }

    /**
     * implementing SimpleHashSet method.
     * checking the current table, to see if a certain value is in it.
     * @param searchVal - the value that we want to search
     * @return true if we found it, false otherwise
     */
    public boolean contains(String searchVal){
        int index = rehash(searchVal,this.capacity());
        //checking in it's null before pointing to the index
        if (this.table[index]!=null){
            //this function of LinkedList<String> returns true or false
            return this.table[index].linkedListContains(searchVal);
        }
        else{return false;}
    }

    /**
     * implementing SimpleHashSet method.
     * Delete a specific element from the set.
     * @param toDelete - the element that needs to be deleted
     * @return true if the element was found and successfully deleted
     */
    public boolean delete(String toDelete){
        if (!this.contains(toDelete)){return false;}
        //checking the load factor.
        float currentLoadFactor = this.size()/(float)this.capacity();
        //in case the load factor is valid
        if((currentLoadFactor > this.getLowerFactor())){
            int index = rehash(toDelete,this.capacity());
            if(table[index] != null){
                //removes the element and returns true.changes the size
                this.changeSize(-SIZE_ADDITION_OR_DECREASION);
                return table[index].deleteFromLinkedList(toDelete);
            }
        }
        //in case that the load factor isn't valid
        rehashing(true);
        //than complete the deletion
        delete(toDelete);
        return true;
    }
}