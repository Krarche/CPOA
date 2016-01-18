package model.business;

import controller.database.DBAccess;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Kevin GUTIERREZ
 * @author Pierre DAUTREY
 * @author Corentin BECT
 */
public class Reservation {
    
    private final int id;
    private final String playerid;
    private final String court;
    private final LocalDateTime time;
    public static final List<Reservation> RESERVATIONS = initReservations();

    public Reservation(int id, String playerid, String court, LocalDateTime time) {
        this.id = id;
        this.playerid = playerid;
        this.court = court;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public String getPlayerid() {
        return playerid;
    }

    public String getCourt() {
        return court;
    }

    public LocalDateTime getTime() {
        return time;
    }

    private static List<Reservation> initReservations() {
        try {
            DBAccess db = new DBAccess();
            return db.getAllReservations();
        } catch (ClassNotFoundException | IOException | SQLException ex) {
            Logger.getLogger(Match.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public static int getMaxId() {
        int maxId = 0;
        for(Reservation reservation : Reservation.RESERVATIONS) {
            if(reservation.getId() > maxId) {
                maxId = reservation.getId();
            }
        }
        return maxId + 1;
    }

    @Override
    public String toString() {
        return Player.PLAYERS.get(playerid).getName() + " " + Player.PLAYERS.get(playerid).getFirstname();
    }
    
    
    
    
}
