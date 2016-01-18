import database.OracleConnection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import schedule.gui.Login;

public class MainGUI extends JFrame {
    
    private final Login login = new Login(this);
    
    public MainGUI() {
        super("GPTL");
        initComponents();
    }
    
    private void initComponents() {
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
