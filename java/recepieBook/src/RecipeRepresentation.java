import org.w3c.dom.NodeList;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class RecipeRepresentation {
    public String kind;
    public String time;
    public String ingredients;
    public String howTo;
    public String name;
    public JFrame frame;
    public JPanel panel;
    public GridBagConstraints c;
    public TextArea textAreaForName, textAreaForIngredients, textAreaForHowTo;
    public BufferedImage imageIngredients, imageHowTo;
    public ImageIcon iconForIngredientsImage, iconForHowToImage;

    //constants
    private static final String HOW_TO_LABEL = "C:\\Users\\חנית\\Desktop\\images for recipes book\\instructions tag.png";
    private static final String INGREDIENTS_LABEL = "C:\\Users\\חנית\\Desktop\\images for recipes book\\ingredients.png";


    public RecipeRepresentation(org.w3c.dom.Document fileDocument) throws IOException {
        kind = getDetailsOfRecipeString("kind",fileDocument);
        time = getDetailsOfRecipeString("time",fileDocument);
        howTo = getDetailsOfRecipeString("howTo",fileDocument);
        name = getDetailsOfRecipeString("name",fileDocument);
        ingredients = getDetailsOfRecipeString("ingredients",fileDocument);
        frame = new JFrame();
        frame.getContentPane().setBackground(Color.WHITE);
        panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setSize(100,200);
        panel.setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        textAreaForName = new TextArea("",15,40,TextArea.SCROLLBARS_NONE);
        textAreaForName.setEditable(false);
        textAreaForName.setBackground(Color.WHITE);
        textAreaForName.setFont(new Font("Aharoni",Font.BOLD,15));
        textAreaForIngredients = new TextArea("",15,40,TextArea.SCROLLBARS_NONE);
        textAreaForIngredients.setEditable(false);
        textAreaForIngredients.setBackground(Color.WHITE);
        textAreaForIngredients.setFont(new Font("",Font.PLAIN,15));
        textAreaForHowTo = new TextArea("",15,40,TextArea.SCROLLBARS_NONE);
        textAreaForHowTo.setEditable(false);
        textAreaForHowTo.setBackground(Color.WHITE);
        textAreaForHowTo.setFont(new Font("",Font.PLAIN,15));
        imageIngredients = ImageIO.read(new File(INGREDIENTS_LABEL));
        imageHowTo = ImageIO.read(new File(HOW_TO_LABEL));
        iconForIngredientsImage = new ImageIcon(imageIngredients);
        iconForHowToImage = new ImageIcon(imageHowTo);

        c.gridx = 0;
        c.gridy = 0;
        panel.add(textAreaForName,c);
        c.gridy =1;
        panel.add(new JLabel(iconForIngredientsImage),c);
        c.gridy = 2;
        panel.add(textAreaForIngredients,c);
        c.gridy =3;
        panel.add(new JLabel(iconForHowToImage),c);
        c.gridy = 4;
        panel.add(textAreaForHowTo,c);

        frame.add(panel);
        frame.setSize(400,700);
        frame.setResizable(false);

        //how to make it a list
    }

    public static void presentRelevantRecipe(RecipeRepresentation currentRecipe){

        currentRecipe.textAreaForName.setText(currentRecipe.name);
        currentRecipe.textAreaForIngredients.setText(currentRecipe.ingredients);
        currentRecipe.textAreaForHowTo.setText(currentRecipe.howTo);
        currentRecipe.frame.setVisible(true);

    }

    public static String getDetailsOfRecipeString(String tagName, org.w3c.dom.Document document ){
        NodeList list = document.getElementsByTagName(tagName);
        if (list != null && list.getLength() > 0) {
            NodeList subList = list.item(0).getChildNodes();

            if (subList != null && subList.getLength() > 0) {
                return subList.item(0).getNodeValue();
            }
        }
        return null;
    }
}
