//class imports
import java.util.HashSet;
import java.util.TreeSet;
import java.util.LinkedList;

/**
 * this class is built to analyze the performance of given data structures
 */
public class SimpleSetPerformanceAnalyzer {

    //class constants

    /** a large number for the size of the erased list */
    private static final int divisionNumberForMiliSecondsRepresentation = 1000000;

    /** a default value to use while calling the main method for add checks */
    private static final String requestedSingleString = "default value";

    /** The linked list spot in the SimpleSet objects array*/
    private static final int LINKED_LIST_SPOT = 4;

    /** The number of iterations each of the contain operations will do in order to calculate the time. not including
     * LinkedList */
    private static final int iterationsNumberExcludedLinkList = 70000;

    /** The linked list number of iterations*/
    private static final int IterationsNumberForLinkedList = 7000;

    /** The number of data structures we want to measure*/
    private static final int NUMBER_OF_TESTED_COLLECTIONS = 5;

    /** the spot of the last item in the listof collection, before linked list*/
    private static final int SPOT_OF_THE_LAST_BEFORE_LINKED_LIST = 3;

    /** The initial number to go through a loop with*/
    private static final int INITIAL_INT_FOR_LOOPING_OVER_A_LIST = 0;



    //creating objects for all the tests

    /** Tree set new object*/
    private CollectionFacadeSet treeSet1= new CollectionFacadeSet(new TreeSet<>());

    /** Tree set new object*/
    private CollectionFacadeSet treeSet2= new CollectionFacadeSet(new TreeSet<>());

    /** Linked List new object*/
    private CollectionFacadeSet linkedList1 = new CollectionFacadeSet(new LinkedList<>());

    /** Linked List new object*/
    private CollectionFacadeSet linkedList2 = new CollectionFacadeSet(new LinkedList<>());

    /** Hash set new object*/
    private CollectionFacadeSet hashSet1 = new CollectionFacadeSet(new HashSet<>());

    /** Hash set new object*/
    private CollectionFacadeSet hashSet2 = new CollectionFacadeSet(new HashSet<>());

    /** Closed hash set new object*/
    private ClosedHashSet closedHashSet1 = new ClosedHashSet();

    /** Closed hash set new object*/
    private ClosedHashSet closedHashSet2 = new ClosedHashSet();

    /** Open hash set new object*/
    private OpenHashSet openHashSet1 = new OpenHashSet();

    /** Open hash set new object*/
    private OpenHashSet openHashSet2 = new OpenHashSet();

    /** An array that holds all the Simple Set objects that is being tested*/
    private SimpleSet[] collectionsArraydata1 = new SimpleSet[]{treeSet1,hashSet1,openHashSet1,closedHashSet1,linkedList1};

    /** An array that holds all the Simple Set objects that is being tested*/
    private SimpleSet[] collectionsArraydata2 = new SimpleSet[]{treeSet2,hashSet2,openHashSet2,closedHashSet2,linkedList2};

    /** An array that holds all the measured times*/
    private long[] timeForAllCollections = new long[NUMBER_OF_TESTED_COLLECTIONS];

    /**
     * Add a specified element to the set if it's not already in it.
     * @param measuringContain marks if we are currently measuring contain operation or add
     * @param currentData the current data to add to the structures
     * @param requestedSingleString a string for the contain operation
     * @param collectionsArray the spesific array of collections that is being checked
     * @return long list of the measured times
     */
    private long[] measuringRunTime (boolean measuringContain,String[] currentData,
                                     String requestedSingleString,SimpleSet[] collectionsArray){
        //measuring add operation
        if (!measuringContain){
            int i = 0;
            for (SimpleSet collection : collectionsArray){
                timeForAllCollections[i]=timeForAdd(currentData,collection);
                i++;
            }
        }
        //measuring contain operation
        else{
            //dealing with a case of all objects but linked list
            int j =INITIAL_INT_FOR_LOOPING_OVER_A_LIST;
            int i =INITIAL_INT_FOR_LOOPING_OVER_A_LIST;
            while (j<=SPOT_OF_THE_LAST_BEFORE_LINKED_LIST){
                warmUpForContain(requestedSingleString,collectionsArray[j]);
                timeForAllCollections[i]=
                        timeForContain(requestedSingleString,collectionsArray[j],iterationsNumberExcludedLinkList);
                j++;
                i++;
            }
            //dealing with the linked list
            timeForAllCollections[LINKED_LIST_SPOT]=timeForContain(requestedSingleString,
                    collectionsArray[LINKED_LIST_SPOT],IterationsNumberForLinkedList);
            }
        return timeForAllCollections;
        }

    /**
     * Measures the time for an add operation in some specific collection for some given data
     * @param currentData the data that needs to be added
     * @param collection the current collection we are measuring
     * @return measured operation time
     */
    private long timeForAdd(String[] currentData,SimpleSet collection){
        //saving the current time
        long timeBefore = System.nanoTime();
        for (String item : currentData){
          collection.add(item);
        }
        //saving the time difference
        long difference = System.nanoTime() - timeBefore;
        //returning the time in mili-seconds
        return (difference/divisionNumberForMiliSecondsRepresentation);
    }

    /**
     * Measures the time for a contain operation in some specific collection for some given element
     * @param element the elements that needs to be searched
     * @param collection the current collection we are measuring
     * @return measured operation time
     */
    private long timeForContain(String element,SimpleSet collection,int iterationsNumber){
        //saving the current time
        long timeBefore = System.nanoTime();
        for (int i=INITIAL_INT_FOR_LOOPING_OVER_A_LIST;i<=iterationsNumber;i++){
            collection.contains(element);
        }
        //saving the time difference
        long difference = System.nanoTime() - timeBefore;
        //returning the time in mili-seconds
        return difference/iterationsNumber;
    }

    /**
     * a warm up method - executes operations for the computer to warm up before the real measuring
     * @param element the elements that needs to be searched
     * @param collection the current collection we are measuring
     */
    private void warmUpForContain(String element, SimpleSet collection){
        for (int i=INITIAL_INT_FOR_LOOPING_OVER_A_LIST; i<=iterationsNumberExcludedLinkList;i++){
            collection.contains(element);
        }
    }

    /**
     * measure the specific requested test. calls the other general functions of the class with some
     * specific values and prints the results to the screen
     */
    public void executingTheTests(){
        //extracting the data from given files
        String[] stringOfData1 =
                Ex3Utils.file2array("C:\\Users\\חנית\\IdeaProjects\\ex 3\\src\\com\\company\\data1.txt");
        String[] stringOfData2 =
                Ex3Utils.file2array("C:\\Users\\חנית\\IdeaProjects\\ex 3\\src\\com\\company\\data2.txt");
        //printing the results to the screen
        System.out.println("here are all the tests results in the folowing order:" +
                " TreeSet,HashSet,OpenHashSet,ClosedHashSet,LinkedList:");

        //measuring data 1
        System.out.println("the results for adding data1");
        long[] addingData1 = this.measuringRunTime(false, stringOfData1, requestedSingleString,collectionsArraydata1);
        for (long k : addingData1) {
            System.out.println(k);
        }

        System.out.println("the results for measuring contain for 'hi' in data1");
        long[] containHiData1 = this.measuringRunTime(true,stringOfData1,"hi",collectionsArraydata1);
        for (long k : containHiData1){
            System.out.println(k);
        }

        System.out.println("the results for measuring contain for '-13170890158' in data1");
        long[] containLongNumData1 = this.measuringRunTime(true,stringOfData1,"-13170890158",collectionsArraydata1);
        for (long k:containLongNumData1){
            System.out.println(k);
        }


        //measuring data 2
        System.out.println("the results for adding data2");
        long[] addingData2 = this.measuringRunTime(false,stringOfData2,requestedSingleString,collectionsArraydata2);
        for (long k : addingData2){
            System.out.println(k);
        }

        System.out.println("the results for measuring contain for '23' in data2");
        long[] contain23Data2 = this.measuringRunTime(true,stringOfData2,"23",collectionsArraydata2);
        for (long k:contain23Data2){
            System.out.println(k);
        }

        System.out.println("the results for measuring contain for 'hi' in data2");
        long[] containHidata2 = this.measuringRunTime(true,stringOfData2,"hi",collectionsArraydata2);
        for (long k:containHidata2){
            System.out.println(k);
        }

    }
}
