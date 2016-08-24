import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//this class has a function to create files from directory, and a function to create documents for files

public class ProcessFiles {

    public static List<Document> listOfDocuments;
    public static File[] allTheFiles;
    public static List<RecipeRepresentation> allTheRecipes = new ArrayList<>();
    //turn the files from the directory to files objects, then turn it to document object, than send it string creator
    //of the name tag or the requested time

    public ProcessFiles(String directory) throws IOException, SAXException, ParserConfigurationException {
        gettingTheFilesFromDirectory(directory);
        gettingTheDocumentsForFiles();
    }
    public static void gettingTheFilesFromDirectory(String directory){
        allTheFiles = new File(directory).listFiles();
    }

    public static void gettingTheDocumentsForFiles()
            throws ParserConfigurationException, IOException, SAXException {
        listOfDocuments = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        if (allTheFiles!=null){
            for (File file:allTheFiles){
                listOfDocuments.add(builder.parse(file));
            }
            gettingTheRecipeRepresentationList();
        }
    }

    private static void gettingTheRecipeRepresentationList() throws IOException {
        for (Document document:listOfDocuments){
            allTheRecipes.add(new RecipeRepresentation(document));
        }
    }
}
