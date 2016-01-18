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
public class BallboyTeam {
    
    private final String id;
    private final Ballboy ballboyOne;
    private final Ballboy ballboyTwo;
    private final Ballboy ballboyThree;
    private final Ballboy ballboyFour;
    private final Ballboy ballboyFive;
    private final Ballboy ballboySix;
    public static final Map<String,BallboyTeam> BALLBOYSTEAM = initBallboysTeam();
    

    public BallboyTeam(String id, Ballboy ballboyOne, Ballboy ballboyTwo, Ballboy ballboyThree, Ballboy ballboyFour, Ballboy ballboyFive, Ballboy ballboySix) {
        this.id = id;
        this.ballboyOne = ballboyOne;
        this.ballboyTwo = ballboyTwo;
        this.ballboyThree = ballboyThree;
        this.ballboyFour = ballboyFour;
        this.ballboyFive = ballboyFive;
        this.ballboySix = ballboySix;
    }

    private static Map<String, BallboyTeam> initBallboysTeam() {
        try {
            DBAccess db = new DBAccess();
            return db.getAllBallboysTeam();
        } catch (ClassNotFoundException | IOException | SQLException ex) {
            Logger.getLogger(Match.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public String toString() {
        return "Equipe " + getId();
    }

    public String getId() {
        return id;
    }

    public Ballboy getBallboyOne() {
        return ballboyOne;
    }

    public Ballboy getBallboyTwo() {
        return ballboyTwo;
    }

    public Ballboy getBallboyThree() {
        return ballboyThree;
    }

    public Ballboy getBallboyFour() {
        return ballboyFour;
    }

    public Ballboy getBallboyFive() {
        return ballboyFive;
    }

    public Ballboy getBallboySix() {
        return ballboySix;
    }
    
    
    
}
