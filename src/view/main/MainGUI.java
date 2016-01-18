package view.main;

import controller.database.OracleConnection;
import java.awt.GridBagLayout;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


/**
 * Fenêtre principale de l'application
 * @author Kevin GUTIERREZ
 * @author Pierre DAUTREY
 * @author Corentin BECT
 */
public class MainGUI extends JFrame {
    
    private final Login login = new Login(this);
    
    public MainGUI() {
        super("GPTL");
        initComponents();
    }
    
    private void initComponents() {
        setLayout(new GridBagLayout());
        add(login);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            ex.printStackTrace(System.out);
        }
        
        java.awt.EventQueue.invokeLater(() -> {
            new MainGUI().setVisible(true);
        });
        
        // Lors de la fermeture de l'application, ferme la connexion si existante
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                OracleConnection.closeConnection();
            } catch (SQLException ex) {
                System.out.println("Impossible de fermer la connexion à la base de données.");
            }
            System.out.println("Fermeture du programme.");
        }, "Shutdown-thread"));
    }
}
