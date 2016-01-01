package schedule.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login extends JPanel {
    
    private final JLabel labelUsername = new JLabel("Nom d'utilisateur : ");
    private final JLabel labelPassword = new JLabel("Mot de passe : ");
    private final JTextField textUsername = new JTextField(20);
    private final JPasswordField fieldPassword = new JPasswordField(20);
    private final JButton buttonLogin = new JButton("S'identifier");
    
    public Login(JFrame parent) {
        parent.setResizable(false);
        initComponents();
    }
    
    private void initComponents() {
        setLayout(new GridBagLayout());
        
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);
        
        constraints.gridx = 0;
        constraints.gridy = 0;     
        add(labelUsername, constraints);
        
        constraints.gridx = 1;
        add(textUsername, constraints);
         
        constraints.gridx = 0;
        constraints.gridy = 1;     
        add(labelPassword, constraints);
         
        constraints.gridx = 1;
        add(fieldPassword, constraints);
         
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        constraints.anchor = GridBagConstraints.CENTER;
        add(buttonLogin, constraints);
        
        setBorder(BorderFactory.createTitledBorder(
        BorderFactory.createEtchedBorder(), "Identification"));
    }
    
    
}
