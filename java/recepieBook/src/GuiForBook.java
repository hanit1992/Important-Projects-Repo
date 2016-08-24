import org.xml.sax.SAXException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GuiForBook {
    private JFrame frame;
    private JButton searchButton;
    public JBoxForMenu recipeKindsBox;
    public JTextField enterTime;
    private JLabel labelForHead,textLabel;
    private BufferedImage picture1,picture2,picture3,picture4,picture5;
    private GridBagConstraints c;
    public JPanel settingBoxPanel;
    private ImageIcon iconForChoosingMenu1,iconForChoosingMenu2,iconForChoosingMenu3,searchButtonImage;

    //constants
    private static final String[] allKinds =
            new String[]{"--  בחר  --","עוגות","עוגיות","בשר","מרק","חלבי","קינוחים","מאפים","מנה עיקרית","חגים"};
    private static final String IMG_PATH = "C:\\Users\\חנית\\Desktop\\images for recipes book\\picture1.png";
    private static final String TEXT_PATH = "C:\\Users\\חנית\\Desktop\\images for recipes book\\new2.png";
    private static final String CHOOSE_PATH = "C:\\Users\\חנית\\Desktop\\images for recipes book\\chooseDish.png";
    private static final String CHOOSE_TIME = "C:\\Users\\חנית\\Desktop\\images for recipes book\\chooseTime.png";
    private static final String SEARCH_IMAGE = "C:\\Users\\חנית\\Desktop\\images for recipes book\\button.png";
    private static final String MINUTES_LABEL = "C:\\Users\\חנית\\Desktop\\images for recipes book\\minutes tag.png";

    public GuiForBook() throws IOException {
        frame = new JFrame("TheRecipeBook");
        frame.getContentPane().setBackground(Color.WHITE);
        recipeKindsBox = new JBoxForMenu(allKinds);
        recipeKindsBox.setBackground(Color.WHITE);
        recipeKindsBox.setEditable(true);
        recipeKindsBox.addActionListener(new BoxActionListener(recipeKindsBox));
        enterTime = new JTextField(10);
        enterTime.setBackground(Color.WHITE);
        enterTime.setOpaque(true);
        picture1 = ImageIO.read(new File(IMG_PATH));
        ImageIcon icon = new ImageIcon(picture1);
        labelForHead = new JLabel(icon);
        labelForHead.setBackground(Color.WHITE);
        labelForHead.setOpaque(true);
        picture2 = ImageIO.read(new File(TEXT_PATH));
        ImageIcon icon1 = new ImageIcon(picture2);
        textLabel = new JLabel(icon1);
        textLabel.setBackground(Color.WHITE);
        textLabel.setOpaque(true);
        c = new GridBagConstraints();
        settingBoxPanel = new JPanel();
        settingBoxPanel.setOpaque(true);
        settingBoxPanel.setBackground(Color.WHITE);
        settingBoxPanel.setSize(new Dimension(1,400));
        picture3 = ImageIO.read(new File(CHOOSE_PATH));
        iconForChoosingMenu1 = new ImageIcon(picture3);
        picture4 = ImageIO.read(new File(CHOOSE_TIME));
        iconForChoosingMenu2 = new ImageIcon(picture4);
        searchButtonImage = new ImageIcon(ImageIO.read(new File(SEARCH_IMAGE)));
        searchButton = new JButton(searchButtonImage);
        searchButton.setBackground(Color.WHITE);
        searchButton.setOpaque(true);
        searchButton.addActionListener(new ButtonsActionSearch(searchButton,this));
        picture5 = ImageIO.read(new File(MINUTES_LABEL));
        iconForChoosingMenu3 = new ImageIcon(picture5);

    }

    public void activateTheFrame(){

        frame.setLayout(new GridBagLayout());

//        c.fill = GridBagConstraints.HORIZONTAL;
        c.weighty = 2;
        c.weightx = 2;

        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.PAGE_START;
        frame.add(textLabel,c);

//        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 1;
        c.gridy = 0;
        c.anchor = GridBagConstraints.NORTHEAST;
        frame.add(labelForHead,c);

//        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        c.anchor = GridBagConstraints.NORTH;
        frame.add(settingBoxPanel,c);

//        c.fill = GridBagConstraints.HORIZONTAL;

        settingBoxPanel.setLayout(new GridBagLayout());

        c.gridx = 1;
        c.gridy = 0;
        settingBoxPanel.add(recipeKindsBox,c);

        c.gridy = 1;
        settingBoxPanel.add(enterTime,c);

        c.gridy = 2;
        settingBoxPanel.add(searchButton,c);

        c.gridx = 2;
        c.gridy = 0;
        settingBoxPanel.add(new JLabel(iconForChoosingMenu1),c);

        c.gridy = 1;
        settingBoxPanel.add(new JLabel(iconForChoosingMenu2),c);

        c.gridx = 0;
        settingBoxPanel.add(new JLabel(iconForChoosingMenu3),c);

        frame.setSize(1200,1050);
        frame.setBackground(Color.WHITE);
        frame.setVisible(true);
    }

    public static void main(String[] args) throws ParserConfigurationException, SAXException {
        GuiForBook newBook = null;
        try {
            newBook = new GuiForBook();
            new ProcessFiles(args[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        newBook.activateTheFrame();
    }

}
