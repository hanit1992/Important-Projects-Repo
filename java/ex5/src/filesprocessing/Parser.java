package filesprocessing;

//class imports
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * this abstract class interpret the given command file and by it's static method returns a Section objects list,
 * according to the given file
 */
public abstract class Parser {

    //class constants

    /** the filter statement in the command file*/
    private static final String FILTER = "FILTER";

    /** the order statement in the command file*/
    private static final String ORDER = "ORDER";

    /** the printed error message in case of missing order or filter statements*/
    private static final String SECTION_OR_ORDER_MISSING_ERROR_STRING =
            "a section missing the 'ORDER' or 'FILTER' statements";

    /** the printed error message in case of the receiving of a bad command file*/
    private static final String BAD_COMMAND_FILE_ERROR_STRING = "bad Command File was given";

    /** the int number that needs to be added to the counter to reach the nest section line, in the normal case*/
    private static final int ADDITION_FOR_REACHING_THE_NEXT_SECTION_LINE_DEFAULT = 4;

    /** the int number that needs to be added to the counter to reach the nest section line, when the previous section
     * was three lines*/
    private static final int ADDITION_FOR_REACHING_THE_NEXT_SECTION_LINE_CASE_SPACIAL = 3;

    /** the first line of the first section of a given command file*/
    private static final int INITIAL_COMMAND_FILE_LINE = 0;

    /**
     * from a given path, will extract the values and return the sections, according to the relevant terms
     * @param pathName the path of the command file
     * @return list of Section objects
     * @throws CaseTwoException being cached in the previous method and inform the user of the problem, without
     * continuing
     */
    public static List<Section> gettingTheSectionsFromCommandFile(String pathName) throws CaseTwoException{
        //creating a new file from the commands file
        File commandsFile = new File(pathName);
        //making sure this is a valid file.
        if (!commandsFile.isFile()){
            throw new CaseTwoException(BAD_COMMAND_FILE_ERROR_STRING);
        }
        List<String> allTheCommands;
        //read all the lines from the text file. catching java's requested exception
        try {
            allTheCommands = Files.readAllLines(commandsFile.toPath());
        }
        catch (IOException e){
            return null;
        }
        return gettingTheSectionsByLoop(allTheCommands);
    }

    /**
     * helper method, that from a given list of values returns the sections
     * @param commandString the list of the lines of the command file
     * @return list of command file sections
     * @throws CaseTwoException being cached in the previous method and inform the user of the problem, without
     * continuing
     */
    private static List<Section> gettingTheSectionsByLoop(List<String> commandString)throws CaseTwoException{
        List<Section> processorSections = new ArrayList<>();
        int currentSectionStartingLine = INITIAL_COMMAND_FILE_LINE;
        //produces the different sections by the current command file values
        while (commandString.size()>currentSectionStartingLine){
            //in case one of the following happens, the given section isn't valid and will throw the exception and
            //return to the main method
            if (!commandString.get(currentSectionStartingLine).equals(FILTER)||
                     commandString.size()<currentSectionStartingLine+3||
                     (commandString.get(currentSectionStartingLine).equals(FILTER)&&
                     commandString.size()==currentSectionStartingLine)
             ||(commandString.size()>currentSectionStartingLine+2&&
                     !commandString.get(currentSectionStartingLine+2).equals(ORDER))){
                throw new CaseTwoException(SECTION_OR_ORDER_MISSING_ERROR_STRING);
            }
            //in case the next section starts after 3 lines, according to those conditions. adding a section with an
            //empty order
            if (commandString.size()<=currentSectionStartingLine+3 ||
                    commandString.get(currentSectionStartingLine+3).equals(FILTER)){
                processorSections.add
                        (new Section(commandString.get(currentSectionStartingLine+1)," ",currentSectionStartingLine+1));
                currentSectionStartingLine+= ADDITION_FOR_REACHING_THE_NEXT_SECTION_LINE_CASE_SPACIAL;
                continue;
            }
            //in case of the regular full section
            processorSections.add(new Section(commandString.get(currentSectionStartingLine+1)
                    ,commandString.get(currentSectionStartingLine+3),currentSectionStartingLine+1));
            currentSectionStartingLine+= ADDITION_FOR_REACHING_THE_NEXT_SECTION_LINE_DEFAULT;
        }
        return processorSections;
    }
}
