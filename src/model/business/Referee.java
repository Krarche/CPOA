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
public class Referee extends Person {
    
    private final String nationality;
    private final String category;
    private final String refereeTeamId;
    private final int matchNumber;
    public static final Map<String,Referee> REFEREES = initReferees();

    public Referee(String id, String name, String firstname, int age, String nationality, String category, String refereeTeamId, int matchNumber) {
        super(id, name, firstname, age);
        this.nationality = nationality;
        this.category = category;
        this.refereeTeamId = refereeTeamId;
        this.matchNumber = matchNumber;
    }

    public String getNationality() {
        return nationality;
    }

    public String getCategory() {
        return category;
    }

    public String getRefereeTeamId() {
        return refereeTeamId;
    }

    private static Map<String, Referee> initReferees() {
        try {
            DBAccess db = new DBAccess();
            return db.getAllReferees();
        } catch (ClassNotFoundException | IOException | SQLException ex) {
            Logger.getLogger(Match.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public int getMatchNumber() {
        return matchNumber;
    }
    
}
