package database;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class OracleConnection {
    
    public static Connection getConnection() throws ClassNotFoundException, IOException, SQLException {
        if(CONNECTION != null)
            return CONNECTION;
        else {  
            Properties serverConf = new Properties();            
            Class.forName("oracle.jdbc.driver.OracleDriver");

            serverConf.load(new FileInputStream(CONFIGURATION));                
            String server = serverConf.getProperty("server");
            Integer port = Integer.parseInt(serverConf.getProperty("port"));
            String pilote = serverConf.getProperty("pilote");
            String service = serverConf.getProperty("service");
            String user = serverConf.getProperty("user");
            String password = serverConf.getProperty("password");

            String url = "jdbc:oracle:" + pilote + ":" + user + "/" + password + "@" + server + ":" + port + ":" + service;

            CONNECTION = DriverManager.getConnection(url);
            return CONNECTION;
        }
    }
    
    private static Connection CONNECTION = null;
    private static final String CONFIGURATION = "src/database/connection.properties";
}
