import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FindTheRecipe {

    public GuiForBook guiForBook;
    public  Filter currentFilterByKind;
    public  Filter getCurrentFilterByTime;
    public  List<RecipeRepresentation> allTheRecipes;
    public  List<RecipeRepresentation> filteredFiles;
    public  GuiForFilteredRecipes guiForFilteredRecipes;

    public FindTheRecipe(GuiForBook guiForBook) throws IOException {
        this.guiForBook = guiForBook;
    }

    public void filterRecipes(String directory) throws ParserConfigurationException, IOException, SAXException {
        //getting the filters from the factory
//        new ProcessFiles(directory);
        filteredFiles = new ArrayList<>();
        currentFilterByKind = FilterFactory.getTheFilter(guiForBook.recipeKindsBox.theCurrentChosenItem,false,null);
        getCurrentFilterByTime = FilterFactory.getTheFilter(null,true,guiForBook.enterTime.getText());
        allTheRecipes = ProcessFiles.allTheRecipes;
        for (RecipeRepresentation recipe:allTheRecipes){
            //getting only the files who matches the conditions
            if (currentFilterByKind.test(recipe)&&getCurrentFilterByTime.test(recipe)){
                filteredFiles.add(recipe);
            }
        }
        presentFilteredFiles(directory);
    }

    //present the recipes found by the search button
    private void presentFilteredFiles(String directory) throws ParserConfigurationException, IOException, SAXException {
//        filterRecipes(directory);
        guiForFilteredRecipes = new GuiForFilteredRecipes(filteredFiles);
    }
}
