/**
 * this class represents a simple hash set. implements SimpleSet
 */
public abstract class SimpleHashSet implements SimpleSet {

    //class constants

    /** The initial default capacity */
    private static final int INITIAL_CAPACITY = 16;

    /** The default upper limit */
    private static final float DEFAULT_UPPER_FACTOR = 0.75f;

    /** The default lower limit */
    private static final float DEFAULT_LOWER_FACTOR = 0.25f;

    /** The number in which we will duplicate the capacity or divide the capacity */
    protected static final int CAPACITY_CHANGING_INT = 2 ;


    //data members

    /** The lowerFactor limit for the load factor of the set */
    private float lowerFactor = DEFAULT_LOWER_FACTOR;

    /** The upperFactor limit for the load factor of the set */
    private float upperFactor = DEFAULT_UPPER_FACTOR;

    /** The current capacity of the table */
    private int currentCapacity = INITIAL_CAPACITY;

    /** The number of elements in the set */
    private int setSize =0 ;


    /**
     * Add a specified element to the set if it's not already in it.
     * @param newValue New value to add to the set
     * @return False if newValue already exists in the set
     */
    public abstract boolean add(String newValue);

    /**
     * Look for a specified value in the set.
     * @param searchVal Value to search for
     * @return True iff searchVal is found in the set
     */
    public abstract boolean contains(String searchVal);

    /**
     * Remove the input element from the set.
     * @param toDelete Value to delete
     * @return True iff toDelete is found and deleted
     */
    public abstract boolean delete(String toDelete);

    /**
     * @return The number of elements currently in the set
     */
    public int size(){
        return this.setSize;
    }

    /**
     * size setter
     * @param addition the addition or deletion value to the size
     */
    public void changeSize(int addition){
        this.setSize+=addition;
    }

    /**
     * capacity getter
     * @return the current capacity of the table
     */
    public int capacity(){
        return this.currentCapacity;
    }

    /**
     * upper factor getter
     * @return the current upper factor
     */
    public float getUpperFactor(){
        return upperFactor;
    }

    /**
     * lower factor getter
     * @return the current lower factor
     */
    public float getLowerFactor(){
        return lowerFactor;
    }

    /**
     * lower factor setter
     * @param newFactor the factor that needs to be set
     */
    public void setLowerFactor(float newFactor){
        this.lowerFactor = newFactor;
    }

    /**
     * upper factor setter
     * @param newFactor the factor that needs to be set
     */
    public void setUpperFactor(float newFactor){
        this.upperFactor = newFactor;

    }

    /**
     * capacity setter
     * @param newCapacity the capacity that needs to be set
     */
    public void setCapacity(int newCapacity){
        this.currentCapacity = newCapacity;
    }
}
