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
public class Player extends Person {

    private final String nationality;
    private final int atpRank;
    public static final Map<String,Player> PLAYERS = initPlayers();
    
    public Player(String id, String name, String firstname, int age, String nationality, int atpRank) {
        super(id, name, firstname, age);
        this.nationality = nationality;
        this.atpRank = atpRank;
    }

    public String getNationality() {
        return nationality;
    }

    public int getAtpRank() {
        return atpRank;
    }

    private static Map<String,Player> initPlayers() {
        try {
            DBAccess db = new DBAccess();
            return db.getAllPlayers();
        } catch (ClassNotFoundException | IOException | SQLException ex) {
            Logger.getLogger(Match.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
