import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GuiForFilteredRecipes {

    public JFrame frame;
    public List<RecipeRepresentation> documentsToPresent;
    public List<JButton> buttons = new ArrayList<>();
    public GridBagConstraints c = new GridBagConstraints();
    public BufferedImage imageForUnFound;
    public ImageIcon iconForUnFoundImage;

    //constants
    private static final String UN_FOUND_IMAGE = "C:\\Users\\חנית\\Desktop\\images for recipes book\\unfound.png";

    //create an instance with the filtered list
    public GuiForFilteredRecipes(List<RecipeRepresentation> documentsToPresent) throws IOException {
        frame = new JFrame();
        frame.setLayout(new GridBagLayout());
        frame.getContentPane().setBackground(Color.WHITE);
        this.documentsToPresent = documentsToPresent;
        imageForUnFound = ImageIO.read(new File(UN_FOUND_IMAGE));
        iconForUnFoundImage = new ImageIcon(imageForUnFound);
        createButtons();
        presentFrame();
    }

    public void createButtons(){
        //in case there is no match
        if (documentsToPresent.size()!=0){
            for (RecipeRepresentation document:documentsToPresent){
                JButton newButton = new JButton(document.name);
                newButton.addActionListener(new ButtonsActionPresentRecipe(document));
                newButton.setBackground(Color.WHITE);
                newButton.setFont(new Font("Ariel",Font.BOLD,15));
                buttons.add(newButton);
            }
            int x = 0;
            int y = 0;
            for (JButton button:buttons){
                c.gridx = x;
                c.gridy = y;
                frame.add(button,c);
                y+=1;
            }
            frame.setSize(200,150);
        }
        else {
            c.gridy = 0;
            c.gridx = 0;
            frame.setSize(550,100);
            frame.add(new JLabel(iconForUnFoundImage),c);
        }
    }

    public void presentFrame(){
//        frame.setSize(200,150);
        frame.setVisible(true);
    }
}