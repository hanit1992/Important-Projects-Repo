import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ButtonsActionSearch implements ActionListener {

    private GuiForBook newGuiForBook;
    private JButton theButton;
    private static final String DIRECTORY = "C:\\Users\\חנית\\Desktop\\recipes";

    public ButtonsActionSearch(JButton theButton,GuiForBook newGui){
        this.theButton = theButton;
        this.newGuiForBook = newGui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.equals(theButton.getText())) {
            try {
                FindTheRecipe findObject = new FindTheRecipe(newGuiForBook);
                findObject.filterRecipes(DIRECTORY);
            } catch (ParserConfigurationException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (SAXException e1) {
                e1.printStackTrace();
            }
        }

    }
}
