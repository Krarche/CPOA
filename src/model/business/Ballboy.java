package model.business;

import controller.database.DBAccess;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kevin GUTIERREZ
 * @author Pierre DAUTREY
 * @author Corentin BECT
 */
public class Ballboy extends Person {

    private final String nationality;
    private final String ballboyTeamId;
    public static final Map<String,Ballboy> BALLBOYS = initBallboys();
    
    
    public Ballboy(String id, String name, String firstname, int age, String nationality, String ballboyTeamId) {
        super(id, name, firstname, age);
        this.nationality = nationality;
        this.ballboyTeamId = ballboyTeamId;
    }

    public String getNationality() {
        return nationality;
    }

    public String getBallboyTeamId() {
        return ballboyTeamId;
    }

    private static Map<String, Ballboy> initBallboys() {
        try {
            DBAccess db = new DBAccess();
            return db.getAllBallboys();
        } catch (ClassNotFoundException | IOException | SQLException ex) {
            Logger.getLogger(Match.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    
}
