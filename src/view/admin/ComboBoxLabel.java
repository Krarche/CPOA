package view.admin;

import java.awt.BorderLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Panel contenant une JComboBox et un JLabel associé
 * @author Kevin GUTIERREZ
 * @author Pierre DAUTREY
 * @author Corentin BECT
 */
public class ComboBoxLabel extends JPanel {
    private final JLabel label;
    private final JComboBox comboBox;
    
    public ComboBoxLabel(String label, JComboBox comboBox) {       
        this.label = new JLabel(label);
        this.comboBox = new JComboBox();
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        add(label,BorderLayout.NORTH);
        add(getComboBox(),BorderLayout.CENTER);
    }

    public JComboBox getComboBox() {
        return comboBox;
    }
}
