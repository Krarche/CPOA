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
public class Match {
    
    private final int id;
    private String type;
    private String playerOneId;
    private String playerTwoId;
    private String playerThreeId;
    private String playerFourId;
    private String ballBoyTeamOneId;
    private String ballBoyTeamTwoId;
    private String umpireId;
    private String netRefereeId;
    private String lineRefereeOneId;
    private String lineRefereeTwoId;
    private String lineRefereeThreeId;
    private String lineRefereeFourId;
    private String lineRefereeFiveId;
    private String lineRefereeSixId;
    private String lineRefereeSevenId;
    private String lineRefereeEightId;
    private LocalDateTime time;
    private String court;
    private String winnerId;
    private String looserId;
    private String score;
    public static final List<Match> MATCHES = initMatches();

    public Match(int id, String type, String playerOneId, String playerTwoId, String playerThreeId, String playerFourId, String ballBoyTeamOneId, String ballBoyTeamTwoId, String umpireId, String netRefereeId, String lineRefereeOneId, String lineRefereeTwoId, String lineRefereeThreeId, String lineRefereeFourId, String lineRefereeFiveId, String lineRefereeSixId, String lineRefereeSevenId, String lineRefereeEightId, LocalDateTime time, String court, String winnerId, String looserId, String score) {
        this.id = id;
        this.type = type;
        this.playerOneId = playerOneId;
        this.playerTwoId = playerTwoId;
        this.playerThreeId = playerThreeId;
        this.playerFourId = playerFourId;
        this.ballBoyTeamOneId = ballBoyTeamOneId;
        this.ballBoyTeamTwoId = ballBoyTeamTwoId;
        this.umpireId = umpireId;
        this.netRefereeId = netRefereeId;
        this.lineRefereeOneId = lineRefereeOneId;
        this.lineRefereeTwoId = lineRefereeTwoId;
        this.lineRefereeThreeId = lineRefereeThreeId;
        this.lineRefereeFourId = lineRefereeFourId;
        this.lineRefereeFiveId = lineRefereeFiveId;
        this.lineRefereeSixId = lineRefereeSixId;
        this.lineRefereeSevenId = lineRefereeSevenId;
        this.lineRefereeEightId = lineRefereeEightId;
        this.time = time;
        this.court = court;
        this.winnerId = winnerId;
        this.looserId = looserId;
        this.score = score;
    }
    
    private static List<Match> initMatches() {
        try {
            DBAccess db = new DBAccess();
            return db.getAllMatches();
        } catch (ClassNotFoundException | IOException | SQLException ex) {
            Logger.getLogger(Match.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    @Override
    public String toString() {
        if(type.equals("Simple")) {
            return Player.PLAYERS.get(playerOneId).getName() + " vs " + Player.PLAYERS.get(playerTwoId).getName();
        }
        else {
            return Player.PLAYERS.get(playerOneId).getName() + " " + Player.PLAYERS.get(playerTwoId).getName()
                   + " vs " + Player.PLAYERS.get(playerThreeId).getName() + " " + Player.PLAYERS.get(playerFourId).getName();
        }
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPlayerOneId() {
        return playerOneId;
    }

    public void setPlayerOneId(String playerOneId) {
        this.playerOneId = playerOneId;
    }

    public String getPlayerTwoId() {
        return playerTwoId;
    }

    public void setPlayerTwoId(String playerTwoId) {
        this.playerTwoId = playerTwoId;
    }

    public String getPlayerThreeId() {
        return playerThreeId;
    }

    public void setPlayerThreeId(String playerThreeId) {
        this.playerThreeId = playerThreeId;
    }

    public String getPlayerFourId() {
        return playerFourId;
    }

    public void setPlayerFourId(String playerFourId) {
        this.playerFourId = playerFourId;
    }

    public String getBallBoyTeamOneId() {
        return ballBoyTeamOneId;
    }

    public void setBallBoyTeamOneId(String ballBoyTeamOneId) {
        this.ballBoyTeamOneId = ballBoyTeamOneId;
    }

    public String getBallBoyTeamTwoId() {
        return ballBoyTeamTwoId;
    }

    public void setBallBoyTeamTwoId(String ballBoyTeamTwoId) {
        this.ballBoyTeamTwoId = ballBoyTeamTwoId;
    }

    public String getUmpireId() {
        return umpireId;
    }

    public void setUmpireId(String umpireId) {
        this.umpireId = umpireId;
    }

    public String getNetRefereeId() {
        return netRefereeId;
    }

    public void setNetRefereeId(String netRefereeId) {
        this.netRefereeId = netRefereeId;
    }

    public String getLineRefereeOneId() {
        return lineRefereeOneId;
    }

    public void setLineRefereeOneId(String lineRefereeOneId) {
        this.lineRefereeOneId = lineRefereeOneId;
    }

    public String getLineRefereeTwoId() {
        return lineRefereeTwoId;
    }

    public void setLineRefereeTwoId(String lineRefereeTwoId) {
        this.lineRefereeTwoId = lineRefereeTwoId;
    }

    public String getLineRefereeThreeId() {
        return lineRefereeThreeId;
    }

    public void setLineRefereeThreeId(String lineRefereeThreeId) {
        this.lineRefereeThreeId = lineRefereeThreeId;
    }

    public String getLineRefereeFourId() {
        return lineRefereeFourId;
    }

    public void setLineRefereeFourId(String lineRefereeFourId) {
        this.lineRefereeFourId = lineRefereeFourId;
    }

    public String getLineRefereeFiveId() {
        return lineRefereeFiveId;
    }

    public void setLineRefereeFiveId(String lineRefereeFiveId) {
        this.lineRefereeFiveId = lineRefereeFiveId;
    }

    public String getLineRefereeSixId() {
        return lineRefereeSixId;
    }

    public void setLineRefereeSixId(String lineRefereeSixId) {
        this.lineRefereeSixId = lineRefereeSixId;
    }

    public String getLineRefereeSevenId() {
        return lineRefereeSevenId;
    }

    public void setLineRefereeSevenId(String lineRefereeSevenId) {
        this.lineRefereeSevenId = lineRefereeSevenId;
    }

    public String getLineRefereeEightId() {
        return lineRefereeEightId;
    }

    public void setLineRefereeEightId(String lineRefereeEightId) {
        this.lineRefereeEightId = lineRefereeEightId;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getCourt() {
        return court;
    }

    public void setCourt(String court) {
        this.court = court;
    }

    public String getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(String winnerId) {
        this.winnerId = winnerId;
    }

    public String getLooserId() {
        return looserId;
    }

    public void setLooserId(String looserId) {
        this.looserId = looserId;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
    
    public static int getMaxId() {
        int maxId = 0;
        for(Match match : Match.MATCHES) {
            if(match.getId() > maxId) {
                maxId = match.getId();
            }
        }
        return maxId + 1;
    }
 
}
