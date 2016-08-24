package filesprocessing;

//class imports
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * holds the main to activate the file filter program, given a command file and files directory. in charge fo the flow
 * and the main methods of the program
 */
public class DirectoryProcessor {

    //class constants

    /** the message that will be printed when wrong number of arguments error occurs*/
    private static final String WRONG_NUMBER_OF_ARGUMENTS_ERROR_STRING = "wrong number of arguments in command line";

    /** number of the given files directory of the command line arguments*/
    private static final int DIRECTORY_OF_FILES_ARG = 0;

    /** number of the given command file of the command line arguments*/
    private static final int COMMANDS_FILE_ARG = 1;

    //class data members

    /** the section object of the given filer to the current file directory */
    private List<Section> processorSections = new ArrayList<>();

    /** list of files that needs to be filtered, extracted from the given directory*/
    private File[] filesToProcess;

    /**
     * main class that runs the program
     * @param args the given arguments String array in order to activate the requested file filter
     */
    public static void main(String[] args){
        new DirectoryProcessor(args);
    }

    /**
     * class constructor. extracting the args values and sending them to interpretation
     * @param args the given arguments to ren the program
     */
    private DirectoryProcessor(String[] args) {
        //checking if the number of arguments is valid
        try {
            checkCommandLineLength(args);
        }
        catch (CaseTwoException e){
            System.err.println(e.getMessage());
            return;
        }
        //creating a file object from a given path.
        try {
            processorSections = Parser.gettingTheSectionsFromCommandFile(args[COMMANDS_FILE_ARG]);
        }
        catch (CaseTwoException e){
            System.err.println(e.getMessage());
            return;
        }
        //extracting the files to be tested from a given file directory
        filesToProcess = new File(args[DIRECTORY_OF_FILES_ARG]).listFiles();
        try {
            printingTheFilteredOrderedFiles();
        }
        catch (CaseTwoException e){
            System.err.println(e.getMessage());
        }
    }

    /**
     * checking the validity of the given command line length and respond accordingly
     * @param argsCheck the command line string array to be checked
     * @throws CaseTwoException being cached in the previous method and inform the user of the problem, without
     * continuing
     */
    private void checkCommandLineLength(String[] argsCheck)throws CaseTwoException{
        if (argsCheck.length!=2) {
            throw new CaseTwoException(WRONG_NUMBER_OF_ARGUMENTS_ERROR_STRING);
        }
    }

    /**
     * by getting the produced section list anf files, printing in the requestd order the filtered files, following
     * the Warnings if there are some
     * @throws CaseTwoException being cached in the previous method and inform the user of the problem, without
     * continuing
     */
    private void printingTheFilteredOrderedFiles()throws CaseTwoException{
        List<File> filteredFiles = new ArrayList<>();
        //for each section
        for (Section section: processorSections){
            //first print the buffered warnings
            for (String message: section.getExceptionMessageBuffer()){
                System.err.println(message);
            }
            //for each file
            for (File file:filesToProcess){
                //test whether the file is a file. if not, go to the next one
                if (!file.isFile()){
                    continue;
                }
                //test whether the current file is filtered by the current section's filter
                if (section.getSectionFilter().test(file)){
                    filteredFiles.add(file);
                }
            }

            //sort the list by 'abc' before sorting by the requested order,in order to deal with equal values correctly
            Collections.sort(filteredFiles);
            //than reaching the requested order sort
            Collections.sort(filteredFiles,section.getSectionOrder());
            //in case it's not reversed, print the list as it is
            if (!section.getSectionOrder().getIsReversed()){
                for (File currentFile:filteredFiles){
                    System.out.println(currentFile.getName());
                }
            }
            //in case it's reversed, reverse and print
            else {
                Collections.reverse(filteredFiles);
                for (File currentFile:filteredFiles){
                    System.out.println(currentFile.getName());
                }
            }
            //clearTheCurrentFilesList
            filteredFiles.clear();
        }
    }
}
