package controller.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Cette classe permet de gérer la connexion à la base de donnée Oracle afin de
 * n'avoir qu'une seule connexion active
 * @author Kevin GUTIERREZ
 * @author Pierre DAUTREY
 * @author Corentin BECT
 */
public class OracleConnection {
    
    private static Connection CONNECTION = null;
    private static final String CONFIGURATION = "connection.properties";
    
    /**
     * Si le champ CONNECTION est différent de null, renvoie la connexion active, 
     * sinon, initialise la connexion et la renvoie
     * @return CONNECTION : la connexion active de l'application
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public static Connection getConnection() throws ClassNotFoundException, IOException, SQLException {
        if(CONNECTION != null)
            return CONNECTION;
        else {  
            Properties serverConf = new Properties();            
            Class.forName("oracle.jdbc.driver.OracleDriver");
            serverConf.load(OracleConnection.class.getResourceAsStream(CONFIGURATION));
            String server = serverConf.getProperty("server");
            Integer port = Integer.parseInt(serverConf.getProperty("port"));
            String driver = serverConf.getProperty("driver");
            String service = serverConf.getProperty("service");
            String user = serverConf.getProperty("user");
            String password = serverConf.getProperty("password");

            String url = "jdbc:oracle:" + driver + ":" + user + "/" + password + "@" + server + ":" + port + ":" + service;

            CONNECTION = DriverManager.getConnection(url);
            System.out.println("Connexion à la base de données établie.");
            return CONNECTION;
        }
    }
    
    /**
     * Si le champ "CONNECTION" est différent de null, ferme la connexion et la 
     * remet à null
     * @throws SQLException
     */
    public static void closeConnection() throws SQLException {
        if(CONNECTION != null) {
            CONNECTION.close();
            CONNECTION = null;
            System.out.println("Connexion à la base de données fermée.");
        }
        else
            System.out.println("Aucune connexion à fermer.");
        
        
    }
}
