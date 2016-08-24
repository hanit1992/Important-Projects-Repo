import javax.jws.soap.SOAPBinding;
import java.util.LinkedList;
import java.util.TreeSet;

/**
 * Created by חנית on 28/03/2016.
 */
public class trying {
    public static void main(String[] args) {
        OpenHashSet newSet = new OpenHashSet(0.2f, 0.1f);
        String hi = "rrhrjrrh";
        ClosedHashSet newClosedSet = new ClosedHashSet();
        newClosedSet.add(hi);
        String ji = "thththt";
        newClosedSet.add(ji);
        newClosedSet.delete(hi);
        newClosedSet.delete(ji);
        System.out.println(newClosedSet.add(hi));
        System.out.println(newClosedSet.contains(hi));
        System.out.println(newClosedSet.delete(ji));
        System.out.println(newClosedSet.add(ji));
//        String[] stringOfData2 = Ex3Utils.file2array("C:\\Users\\חנית\\IdeaProjects\\ex 3\\src\\com\\company\\data2.txt");
//        for (String k:stringOfData2){
//            newClosedSet.add(k);
//        }
//        for (String k:stringOfData2){
//            newClosedSet.delete(k);
//        }

        String ff = "youuu";
        String hh = "iiiii";
        String huhuhu = "pppp";
        String ryryry = "rjfjfjf";
        String fjfjf = "tytyyttyyt";
        String kkkkk = "peoepeo";
        String oooo = "djkdj";
        newSet.add(ji);
        newSet.add(hi);
        newSet.add(hi);
//        newSet.add(ff);
//        newSet.add(hh);
//        newSet.add(kkkkk);
//        newSet.add(oooo);
//        newSet.add(huhuhu);
//        newSet.add(ryryry);
//        newSet.add(fjfjf);
//        System.out.println(newSet.delete(fjfjf));
//        System.out.println(newSet.delete(fjfjf));
        ClosedHashSet closeNew = new ClosedHashSet(0.5f, 0.4f);
//        System.out.println(closeNew.add(ff));
        closeNew.add(hi);
        closeNew.add(hh);
        closeNew.add(huhuhu);
//        closeNew.add(fjfjf);
//        closeNew.add(ryryry);
//        closeNew.add(ji);
//        closeNew.add(ji);
        //System.out.println(closeNew.contains(ji));
//        System.out.println(newSet.delete(hi));
//        System.out.println(newSet.contains(hi));
        //System.out.println(closeNew.delete(hi));
        //System.out.println(closeNew.delete(hi));
//        TreeSet<String> newsettt = new TreeSet<>();
//        LinkedList<String> myList = new LinkedList<>();
//        CollectionFacadeSet newwww = new CollectionFacadeSet(myList);
//        CollectionFacadeSet newCollection = new CollectionFacadeSet(newsettt);
//        System.out.println(newCollection.add(ji));
//        System.out.println(newwww.add(ji));
//        System.out.println(newwww.add(ji));
//        System.out.println(newwww.delete(ji));
//        newwww.add(fjfjf);
//        System.out.println(newwww.size());
        //ClosedHashSet newqqq = new ClosedHashSet();
        //newqqq.addingToErasedList(9);
        //newqqq.addingToErasedList(8);
        //newqqq.addingToErasedList(7);
        //newqqq.addingToErasedList(0);
        //System.out.println(newqqq.erasedListContain(9));
        //System.out.println(newqqq.erasedListContain(0));
        //newqqq.eraseFromErasedList(0);
        //for (int i:newqqq.ErasedIndexes){
        //  System.out.println(i);
        //}
        //System.out.println(newqqq.ErasedIndexes);


       SimpleSetPerformanceAnalyzer analyze = new SimpleSetPerformanceAnalyzer();
        analyze.executingTheTests();
//        String[] stringOfData1 = Ex3Utils.file2array("C:\\Users\\חנית\\IdeaProjects\\ex 3\\src\\com\\company\\data1.txt");
//        String[] stringOfData2 = Ex3Utils.file2array("C:\\Users\\חנית\\IdeaProjects\\ex 3\\src\\com\\company\\data2.txt");
//
//
//
//        long[] addingData1 = analyze.measuringRunTime(false, stringOfData1, SimpleSetPerformanceAnalyzer.requestedSingleString);
//        for (long k : addingData1) {
//            System.out.println(k);
//        }
////        //comparing values
////        System.out.println("ggggggggggggggggggggggggggggggggggggggg");
////
//        long[] compare1contain = analyze.measuringRunTime(true,stringOfData1,"hi");
//        for (long k : compare1contain){
//            System.out.println(k);
//        }
//        System.out.println("gggggggggggggggggggggggggggggggggggggggg");
//       long[] addtodata2 = analyze.measuringRunTime(false,stringOfData2,SimpleSetPerformanceAnalyzer.requestedSingleString);
//        for (long k : addtodata2){
//            System.out.println(k);
//        }
//
//        System.out.println("gggggggggggggggggggggggggggggggggggggggggggg");
//        long[] containhidata2 = analyze.measuringRunTime(true,stringOfData2,"hi");
//        for (long k:containhidata2){
//           System.out.println(k);
//        }
//
//        System.out.println("gggggggggggggggggggggggggggggggggggggggggggggg");
//        long[] contain23 = analyze.measuringRunTime(true,stringOfData2,"23");
//        for (long k:contain23){
//            System.out.println(k);
//        }
//
//        System.out.println("gggggggggggggggggggggggggggggggggggggggggggggggggg");
//        long[] containlongnummm = analyze.measuringRunTime(true,stringOfData1,"-13170890158");
//        for (long k:containlongnummm){
//            System.out.println(k);
//        }


        }

    }


