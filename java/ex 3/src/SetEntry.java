import java.util.LinkedList;
/**
 * this class will hold each of the entries in the set, for all the kinds of it.
 * each entry will have a value - the actual data it holds, and key - an int that will mark the hash value
 * it gets.
 */
public class SetEntry {

    //class data members
    private String data;
    private int key;
    public static LinkedList<String> entryListForOpenHashSet = new LinkedList<>();

    /**
     * the class constructor.
     * @param itemData - the data of an entry
     * @param itemKey - the key of an item, basically the picture the the hash function produces for this entry.
     */
    public SetEntry(String itemData, int itemKey){
        data = itemData;
        key = itemKey;
    }

    /**
     * returns the entry data
     */
    public String getData(){
        return this.data;
    }

    /**
     * returns the entry key
     */
    public int getKey(){
        return this.key;
    }
}
