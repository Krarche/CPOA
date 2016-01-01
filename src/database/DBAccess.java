package database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBAccess {
    
    private final Connection connection;
    
    public DBAccess() throws ClassNotFoundException, IOException, SQLException {
        connection = OracleConnection.getConnection();
    }
    
    public boolean checkLogin(String personID, String password) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM gptl_person WHERE person_id = ? AND password = ?");
        
        preparedStatement.setString(1,personID);
        preparedStatement.setString(2,password);
        
        ResultSet result = preparedStatement.executeQuery();
        
        if(result.next()) {
            result.close();
            preparedStatement.close();
            return true;
        }
        else {
            result.close();
            preparedStatement.close();
            return false;
        }
    }
    
}
