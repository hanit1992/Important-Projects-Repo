import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.function.Predicate;

public class FilterFactory {

    public static Filter getTheFilter(String theFilterRequest,boolean timeFilter, String theTimeRequested)
            throws ParserConfigurationException {
        //getting document builder
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        if (!timeFilter){
            Predicate<RecipeRepresentation> theChosenFilter = (RecipeRepresentation recipe) ->{
                    if (recipe.kind.equals(theFilterRequest)){
                        return true;
                    }
                    return false;

            };
                return new Filter(theChosenFilter);
            }
        else{
            if (theTimeRequested.equals("")){
                Predicate<RecipeRepresentation> filterForNotSpecifiedTime = (RecipeRepresentation recipe) ->{
                    if (Integer.parseInt(recipe.time) <= Integer.MAX_VALUE){
                        return true;
                    }
                    return false;
                };
                return new Filter(filterForNotSpecifiedTime);
            }
            Predicate<RecipeRepresentation> theChosenFilter = (RecipeRepresentation recipe) ->{
                    if (Integer.parseInt(recipe.time) <= Integer.parseInt(theTimeRequested)){
                        return true;
                    }
                    return false;
            };
              return new Filter(theChosenFilter);
        }
        }
}