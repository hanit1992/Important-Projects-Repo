package filter;

//class imports
import filesprocessing.CaseOneException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * factory class to create filter objects, according to the values of the current section requests
 */
public abstract class FilterFactory {

    //class constants

    /** a case of the requested filter by the command file  */
    private static final String NOT = "NOT";

    /** a case of the requested filter by the command file  */
    private static final String NO = "NO";

    /** a case of the requested filter by the command file  */
    private static final String YES = "YES";

    /** a case of the requested filter by the command file  */
    private static final String GREATER_THAN = "greater_than";

    /** a case of the requested filter by the command file  */
    private static final String BETWEEN = "between";

    /** a case of the requested filter by the command file  */
    private static final String SMALLER_THAN = "smaller_than";

    /** a case of the requested filter by the command file  */
    private static final String FILE = "file";

    /** a case of the requested filter by the command file  */
    private static final String CONTAINS = "contains";

    /** a case of the requested filter by the command file  */
    private static final String PREFIX = "prefix";

    /** a case of the requested filter by the command file  */
    private static final String SUFFIX = "suffix";

    /** a case of the requested filter by the command file  */
    private static final String WRITABLE = "writable";

    /** a case of the requested filter by the command file  */
    private static final String EXECUTABLE = "executable";

    /** a case of the requested filter by the command file  */
    private static final String HIDDEN = "hidden";

    /** a case of the requested filter by the command file  */
    private static final String ALL = "all";

    /** the number that converts the given size in bytes to K-bytes */
    private static final int BYTES_CONVERSION_INT = 1024;

    /** the name of the filter spot in the splited list */
    private static final int FILTER_NAME_SPOT = 0;

    /** the first value spot in the splitted list */
    private static final int FIRST_VALUE_SPOT = 1;

    /** the second value spot in the splitted list */
    private static final int SECOND_VALUE_SPOT = 2;

    /** third value spot in the splitted list, only in the case of 'NOT' addition */
    private static final int THIRD_VALUE_SPOT = 3;

    /** the length of a list who has at list one argument */
    private static final int LENGTH_OF_LIST_WITH_AT_LIST_ONE_VARIABLE = 2;

    /** the length of a list who has at list two arguments */
    private static final int LENGTH_OF_LIST_WITH_TWO_VARIABLE = 3;

    /** in case of three arguments - two arguments and also boolean ending statement ('NOT') */
    private static final int LENGTH_OF_LIST_WITH_THREE_ARGUMENTS = 3;

    //data members

    /** a list of all the messages cached by the main function, in order to inform the user of some warnings */
    private static List<String> filterMessageBuffer = new ArrayList<>();

    /** the current section started line of the command file, in order to update the correct lines of warnings */
    public static int filterStartingLine;

    /**
     * from the given filter line of the section, will get the filter object accordingly
     * @param requestedFilter the string line contains the filter details
     * @return the filter object
     */
    public static Filter getTheFilter(String requestedFilter) {
        //announcing the variables to be updated accordingly
        String nameOfFilter;
        //null is the default choice
        String valueOne = null;
        //null is the default choice
        String filterNegation = null;
        //only in the case of "between" filter
        String valueTwo = null;

        //splitting the given string line
        String[] stringOfFilterGivenValues = requestedFilter.split("#");
        //updating the variables
        nameOfFilter = stringOfFilterGivenValues[FILTER_NAME_SPOT];
        //in case you have at list one argument
        if (stringOfFilterGivenValues.length>= LENGTH_OF_LIST_WITH_AT_LIST_ONE_VARIABLE){
            valueOne = stringOfFilterGivenValues[FIRST_VALUE_SPOT];
        }
        //in case you have two
        if (stringOfFilterGivenValues.length>= LENGTH_OF_LIST_WITH_TWO_VARIABLE){
            //in case of a 'not' addition, update the variable
            if (stringOfFilterGivenValues[SECOND_VALUE_SPOT].equals(NOT)){
                filterNegation = stringOfFilterGivenValues[SECOND_VALUE_SPOT];
            }
            else{
                valueTwo = stringOfFilterGivenValues[SECOND_VALUE_SPOT];
                if (stringOfFilterGivenValues.length> LENGTH_OF_LIST_WITH_THREE_ARGUMENTS){
                    filterNegation = stringOfFilterGivenValues[THIRD_VALUE_SPOT];
                }
            }
        }
        //returning the filter by inspecting the given mane and arguments
        return extractingFilterByCases(nameOfFilter,valueOne,valueTwo,filterNegation);
    }

    public static void setFilterStartingLine(int newVal){
        filterStartingLine = newVal;
    }

    /**
     * by the division of cases based on the filter name, returning a matching filter object
     * @param nameOfPath name of filter
     * @param valueOne string of first value
     * @param valueTwo string of second value
     * @param filterNegation string of NOT
     * @return the matching filter
     */
    private static Filter extractingFilterByCases(String nameOfPath, String valueOne, String valueTwo, String filterNegation ){
        //local value of predicate object for invalid/other cases. using method reference instead of lambda, due to
        //calling of un existing function of a class
        Predicate<File> takeAll = File::isFile;
        //switch by filter name statement
        switch (nameOfPath){
            case GREATER_THAN:
                Double greaterThanDoubleValue = Double.valueOf(valueOne);
                try {
                    inValidIntForFilter(greaterThanDoubleValue,Double.MAX_VALUE,filterStartingLine+1);
                }
                catch (CaseOneException e){
                    filterMessageBuffer.add(e.getMessage());
                    //continuing with all case
                    return new Filter(takeAll);
                }
                Predicate<File> greaterThanFilter = (File file)-> (file.length()/
                        BYTES_CONVERSION_INT)>greaterThanDoubleValue;

                if (filterNegation==null){
                    return new Filter(greaterThanFilter);
                }
                //case 'NOT' filter, negation of the regular one
                Predicate<File> notGreaterThanFilter = greaterThanFilter.negate();
                return new Filter(notGreaterThanFilter);

            case BETWEEN:
                //returning the given strings to doubles
                Double betweenDoubleValueOne = Double.valueOf(valueOne);
                Double betweenDoubleValueTwo = Double.valueOf(valueTwo);
                try {
                    inValidIntForFilter(betweenDoubleValueOne,betweenDoubleValueTwo,filterStartingLine+1);
                }
                catch (CaseOneException e){
                    filterMessageBuffer.add(e.getMessage());
                    //continuing with all case
                    return new Filter(takeAll);
                }
                Predicate<File> betweenFilter = (File file)-> (file.length()/BYTES_CONVERSION_INT)>betweenDoubleValueOne
                        &&(file.length()/BYTES_CONVERSION_INT)<betweenDoubleValueTwo;
                if (filterNegation==null){
                    return new Filter(betweenFilter);
                }
                //case 'NOT' filter, negation of the regular one
                Predicate<File> notBetweenFilter = betweenFilter.negate();
                return new Filter(notBetweenFilter);

            case SMALLER_THAN:
                //returning the given strings to doubles
                Double smallerThanDoubleValue = Double.valueOf(valueOne);
                try {
                    inValidIntForFilter(smallerThanDoubleValue,Double.MAX_VALUE,filterStartingLine+1);
                }
                catch (CaseOneException e){
                    filterMessageBuffer.add(e.getMessage());
                    //continuing with all case
                    return new Filter(takeAll);
                }
                Predicate<File> smallerThanFilter = (File file)-> (file.length()/BYTES_CONVERSION_INT)
                        <smallerThanDoubleValue;
                if (filterNegation==null){
                    return new Filter(smallerThanFilter);
                }
                //case 'NOT' filter, negation of the regular one
                Predicate<File> notSmallerThanFilter = smallerThanFilter.negate();
                return new Filter(notSmallerThanFilter);

            case FILE:
                Predicate<File> isFileFilter = (File file)-> file.getName().equals(valueOne);
                if (filterNegation==null){
                    return new Filter(isFileFilter);
                }
                //case 'NOT' filter, negation of the regular one
                Predicate<File> notFileFilter = isFileFilter.negate();
                return new Filter(notFileFilter);

            case CONTAINS:
                Predicate<File> containsFilter = (File file)->
                        file.getName().toLowerCase().contains(valueOne.toLowerCase());
                if (filterNegation==null){
                    return new Filter(containsFilter);
                }
                //case 'NOT' filter, negation of the regular one
                Predicate<File> notContainsFilter = containsFilter.negate();
                //continuing with all case
                return new Filter(notContainsFilter);

            case PREFIX:
                Predicate<File> isThePrefix = (File file)-> file.getName().startsWith(valueOne);
                if (filterNegation==null){
                    return new Filter(isThePrefix);
                }
                //case 'NOT' filter, negation of the regular one
                Predicate<File> notThePrefix = isThePrefix.negate();
                return new Filter(notThePrefix);

            case SUFFIX:
                Predicate<File> isTheSuffix = (File file)->file.getName().endsWith(valueOne);
                if (filterNegation==null){
                    return new Filter(isTheSuffix);
                }
                //case 'NOT' filter, negation of the regular one
                Predicate<File> notTheSuffix = isTheSuffix.negate();
                return new Filter(notTheSuffix);

            case WRITABLE:
                try {
                    inValidBooleanForFilter(valueOne,filterStartingLine+1);
                }
                catch (CaseOneException e){
                    filterMessageBuffer.add(e.getMessage());
                    //continuing with all case
                    return new Filter(takeAll);
                }
                //using method reference instead of lambda, due to calling of un existing function of a class
                Predicate<File> isWritable = File::canWrite;
                if ((filterNegation==null && valueOne.equals("YES")|| filterNegation!=null && valueOne.equals(NO))){
                    return new Filter(isWritable);
                }
                //case 'NOT' filter, negation of the regular one
                Predicate<File> notWritable = isWritable.negate();
                return new Filter(notWritable);

            case EXECUTABLE:
                try {
                    inValidBooleanForFilter(valueOne,filterStartingLine+1);
                }
                catch (CaseOneException e){
                    filterMessageBuffer.add(e.getMessage());
                    //continuing with all case
                    return new Filter(takeAll);
                }
                if ((filterNegation==null && valueOne.equals(YES)|| filterNegation!=null && valueOne.equals(NO))) {
                    Predicate<File> isExecutable = File::canExecute;
                    return new Filter(isExecutable);
                }
                //case 'NOT' filter, negation of the regular one
                Predicate<File> notExecutable = (File file)-> !file.canExecute();
                return new Filter(notExecutable);

            case HIDDEN:
                try {
                    inValidBooleanForFilter(valueOne,filterStartingLine+1);
                }
                catch (CaseOneException e){
                    filterMessageBuffer.add(e.getMessage());
                    //continuing with all case
                    return new Filter(takeAll);
                }
                //using method reference instead of lambda, due to calling of un existing function of a class
                Predicate<File> isHidden = File::isHidden;
                if ((filterNegation==null && valueOne.equals(YES)|| filterNegation!=null && valueOne.equals(NO))) {
                    return new Filter(isHidden);
                }
                //case 'NOT' filter, negation of the regular one
                Predicate<File> notHidden = isHidden.negate();
                return new Filter(notHidden);

            case ALL:
                //always true. this case is the case when there is a bad filter name
                if (filterNegation==null){
                    return new Filter(takeAll);
                }
                Predicate<File> takeNothing = takeAll.negate();
                return new Filter(takeNothing);

            default:
                //default case of some bad name
                try {
                    inValidNameForFilter();
                }
                catch (CaseOneException e){
                    filterMessageBuffer.add(e.getMessage());
                }
                return new Filter(takeAll);
        }
    }

    /**
     * the message buffer list getter
     * @return list of buffed messages of the current filter producing
     */
    public static List<String> getFilterMessageBuffer(){
        return filterMessageBuffer;
    }

    /**
     * this method was made in order to throw an exception and is being called whenever we reach a bad name of filter
     * case, in order to continue normally with the default case
     * @throws CaseOneException will print a warning with the problematic line, and continue normally after wards,
     * by ignoring the problematic part and replacing it by the default way
     */
    private static void inValidNameForFilter() throws CaseOneException{
        String stringLine = String.valueOf(filterStartingLine+1);
        throw new CaseOneException(stringLine);
    }

    /**
     * will check if the given numbers are valid for  their own filter type
     * @param valueOne first double parameter
     * @param valueTwo second double parameter
     * @param line number of relevant problematic line
     * @throws CaseOneException will print a warning with the problematic line, and continue normally after wards,
     * by ignoring the problematic part and replacing it by the default way
     */
    private static void inValidIntForFilter(double valueOne, double valueTwo, int line) throws CaseOneException{
        if (!(valueOne<valueTwo) || valueOne<0 || valueTwo<0){
            String stringLine = String.valueOf(line);
            throw new CaseOneException(stringLine);
        }
    }

    /**
     * will check if the boolean values are valid
     * @param valueOne first double parameter
     * @param line number of relevant problematic line
     * @throws CaseOneException will print a warning with the problematic line, and continue normally after wards,
     * by ignoring the problematic part and replacing it by the default way
     */
    private static void inValidBooleanForFilter(String valueOne, int line)throws CaseOneException{
        if (!valueOne.equals(YES)&& !valueOne.equals(NO)){
            String stringLine = String.valueOf(line);
            throw new CaseOneException(stringLine);
        }
    }
}