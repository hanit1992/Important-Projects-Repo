import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class BoxActionListener implements ActionListener {

    JBoxForMenu comboBox;

    public BoxActionListener(JBoxForMenu comboBox){
        this.comboBox = comboBox;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        comboBox.theCurrentChosenItem = comboBox.getSelectedItem().toString();
    }
}
