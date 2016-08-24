import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonsActionPresentRecipe implements ActionListener {

    public RecipeRepresentation theRecipeOfTheButton;

    public ButtonsActionPresentRecipe(RecipeRepresentation currentRecipe){
        this.theRecipeOfTheButton = currentRecipe;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        RecipeRepresentation.presentRelevantRecipe(theRecipeOfTheButton);

    }
}
