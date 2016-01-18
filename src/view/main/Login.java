package view.main;

import view.player.PlayerMain;
import view.admin.AdminMain;
import controller.database.DBAccess;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * Panel contenant le formulaire de connexion
 * @author Kevin GUTIERREZ
 * @author Pierre DAUTREY
 * @author Corentin BECT
 */
public class Login extends JPanel {
    
    private final JLabel labelUsername = new JLabel("Nom d'utilisateur : ");
    private final JLabel labelPassword = new JLabel("Mot de passe : ");
    private final JTextField textUsername = new JTextField(20);
    private final JPasswordField fieldPassword = new JPasswordField(20);
    private final JButton buttonLogin = new JButton("S'identifier");
    private final JFrame parent;
    
    public Login(JFrame parent) {
        this.parent = parent;
        this.parent.setResizable(false);
        this.parent.getRootPane().setDefaultButton(buttonLogin);
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
        
        buttonLogin.addActionListener((ActionEvent e) -> {
            actionPermorfed(e);
        });  
    }

    private void actionPermorfed(ActionEvent e) {
        if (e.getSource() == buttonLogin)
            performLogin();
    }
    
    private void performLogin() {
        String username = textUsername.getText();
        char[] password = fieldPassword.getPassword();
        String loginType = "null";
        boolean validUsername = false;
        
        // Vérifie si un nom d'utilisateur et mot de passe ont été saisi
        if(username.length() == 0 && password.length == 0)
            JOptionPane.showMessageDialog(this, "Veuillez saisir votre nom d'utilisateur et votre mot de passe.");
        else if(username.length() == 0)
            JOptionPane.showMessageDialog(this, "Veuillez saisir votre nom d'utilisateur.");
        else if(password.length == 0)
            JOptionPane.showMessageDialog(this, "Veuillez saisir votre mot de passe.");
        else {
            // Vérifie le type d'utilisateur saisi
            if (username.startsWith("p")) { 
                loginType = "player";
                validUsername = true;
            }                
            else if (username.startsWith("a")) {
                loginType = "admin";
                validUsername = true;
            }             
            else 
                JOptionPane.showMessageDialog(this, "Veuillez entrer un nom d'utilisateur valide.");   
        }
        
        if(validUsername) {
            try {
                DBAccess db = new DBAccess();
                // Vérifie si les identifiants saisi sont présents dans la base de données
                if(db.checkLogin(username, String.copyValueOf(password))) {  
                    parent.remove(this);
                    if(loginType.equals("player")) { // Ouvre la fenêtre de réservation d'entrainement
                        parent.add(new PlayerMain(this.parent,username),
                                   new GridBagConstraints(-1,-1,1,1,1,1,10,1,new Insets(0, 0, 0, 0),0,0));
                        parent.pack();
                        parent.setLocationRelativeTo(null);
                    }
                    else { // Ouvre la fenêtre de gestion de planning
                        parent.add(new AdminMain(this.parent),
                                   new GridBagConstraints(-1,-1,1,1,1,1,10,1,new Insets(0, 0, 0, 0),0,0));
                        parent.pack();
                        parent.setLocationRelativeTo(null);
                    }
                }
                else
                  JOptionPane.showMessageDialog(this, "Identification échouée.");  
            } catch (ClassNotFoundException | IOException | SQLException | HeadlessException e) {
                System.out.println("Impossible d'établir la connexion à la base de donnée");
            } 
        }
        
            
    }
    
    
    
    
}
