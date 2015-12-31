import database.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    
    public static void main(String[] args) {
        try {
            Connection connection = OracleConnection.getConnection();
            Statement requete = connection.createStatement();
            ResultSet resultat = requete.executeQuery("SELECT * FROM emp");
            
            while(resultat.next()) {
                System.out.println(resultat.getInt("empno"));
            }
            
            resultat.close() ;
            requete.close() ;
            connection.close();
        } catch (IOException e) {
                System.out.println("Fichier de configuration introuvable");
        } catch (ClassNotFoundException e) {
            System.out.println("Impossible de charger le driver");
        } catch (SQLException ex) {
            System.out.println("URL de connexion invalide");
        }
    }
}
