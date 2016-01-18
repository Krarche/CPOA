package controller.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import utils.DateConvertUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.business.Match;
import model.business.Reservation;
import model.business.Ballboy;
import model.business.BallboyTeam;
import model.business.Player;
import model.business.Referee;

/**
 * Cette classe permet de gérer les différentes requêtes à la base de données
 * en utilisant la connexion crée par la classe OracleConnection
 * @author Kevin GUTIERREZ
 * @author Pierre DAUTREY
 * @author Corentin BECT
 */
public class DBAccess {
    
    private final Connection connection;
    
    /**
     * Initialise le champ connection à l'aide de la méthode getConnection()
     * de la classe OracleConnection
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws SQLException
     */
    public DBAccess() throws ClassNotFoundException, IOException, SQLException {
        connection = OracleConnection.getConnection();
    }
    
    /**
     * Teste si les identifiants fournis existent dans la base de donnée
     * @param personID nom d'utilisateur saisi
     * @param password mot de passe saisi
     * @return true : succès de l'authentification false : echec de l'authentification
     * @throws SQLException
     */
    public boolean checkLogin(String personID, String password) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM gptl_login WHERE username = ? AND password = ?");
        
        preparedStatement.setString(1,personID);
        preparedStatement.setString(2,password);
        
        ResultSet result = preparedStatement.executeQuery();
        
        if(result.next()) { // Si le résultat contient une ligne
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
    
    /**
     * Récupère toutes les instances de la table GPTL_MATCH
     * @return list : ArrayList contenant toutes les instances des matchs de la base de donnée
     */
    public ArrayList<Match> getAllMatches () {
        ArrayList<Match> list = new ArrayList();
        try (Statement statement = connection.createStatement(); 
             ResultSet result = statement.executeQuery("SELECT * FROM gptl_match")) {
            while(result.next()) {
                int id = result.getInt("ID");
                String type = result.getString("TYPE");
                String playerOneId = result.getString("PLAYER1_ID");
                String playerTwoId = result.getString("PLAYER2_ID");
                String playerThreeId = result.getString("PLAYER3_ID");
                String playerFourId = result.getString("PLAYER4_ID");
                String ballBoyTeamOneId = result.getString("BALLBOY_TEAM1_ID");
                String ballBoyTeamTwoId = result.getString("BALLBOY_TEAM2_ID");
                String umpireId = result.getString("UMPIRE_ID");
                String netRefereeId = result.getString("NET_REFEREE_ID");
                String lineRefereeOneId = result.getString("LINE_REFEREE1_ID");
                String lineRefereeTwoId = result.getString("LINE_REFEREE2_ID");
                String lineRefereeThreeId = result.getString("LINE_REFEREE3_ID");
                String lineRefereeFourId = result.getString("LINE_REFEREE4_ID");
                String lineRefereeFiveId = result.getString("LINE_REFEREE5_ID");
                String lineRefereeSixId = result.getString("LINE_REFEREE6_ID");
                String lineRefereeSevenId = result.getString("LINE_REFEREE7_ID");
                String lineRefereeEightId = result.getString("LINE_REFEREE8_ID");
                LocalDateTime time = DateConvertUtils.asLocalDateTime(result.getDate("TIME"));
                String court = result.getString("COURT");
                String winnerId = result.getString("WINNER_ID");
                String looserId = result.getString("LOOSER_ID");
                String score = result.getString("SCORE");
                
                list.add(new Match(id, type, playerOneId, playerTwoId, playerThreeId, playerFourId,
                        ballBoyTeamOneId, ballBoyTeamTwoId, umpireId, netRefereeId,
                        lineRefereeOneId, lineRefereeTwoId, lineRefereeThreeId, lineRefereeFourId,
                        lineRefereeFiveId, lineRefereeSixId, lineRefereeSevenId, lineRefereeEightId,
                        time, court, winnerId, looserId, score));
            }  
            result.close();
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    /**
     * Récupère toutes les instances de la table GPTL_PLAYER
     * @return list : Map contenant toutes les instances des joueurs de la base de donnée
     */
    public Map<String,Player> getAllPlayers() {
        Map<String,Player> list = new HashMap();
        try(Statement statement = connection.createStatement(); 
            ResultSet result = statement.executeQuery("SELECT * FROM gptl_player")) {
            while(result.next()) {
                String id = result.getString("id");
                String name = result.getString("name");
                String firstname = result.getString("firstname");
                int age = result.getInt("age");
                String nationality = result.getString("nationality");
                int atpRank = result.getInt("atp_rank");
                list.put(id, new Player(id,name,firstname,age,nationality,atpRank));
            }
            result.close();
            statement.close();
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(DBAccess.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    /**
     * Récupère toutes les instances de la table GPTL_REFEREE
     * @return list : Map contenant toutes les instances des arbitres de la base de donnée
     */
    public Map<String,Referee> getAllReferees() {
        Map<String,Referee> list = new HashMap();
        try(Statement statement = connection.createStatement(); 
            ResultSet result = statement.executeQuery("SELECT * FROM gptl_referee")) {
            while(result.next()) {
                String id = result.getString("id");
                String name = result.getString("name");
                String firstname = result.getString("firstname");
                int age = result.getInt("age");
                String nationality = result.getString("nationality");
                String category = result.getString("category");
                String refereeTeamId = result.getString("referee_team_id");
                int matchNumber = result.getInt("match_number");
                list.put(id, new Referee(id,name,firstname,age,nationality,category,refereeTeamId,matchNumber));
            }
            result.close();
            statement.close();
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(DBAccess.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    /**
     * Récupère toutes les instances de la table GPTL_BALLBOY
     * @return list : Map contenant toutes les instances des ramasseurs de balles de la base de donnée
     */
    public Map<String,Ballboy> getAllBallboys() {
        Map<String,Ballboy> list = new HashMap();
        try(Statement statement = connection.createStatement(); 
            ResultSet result = statement.executeQuery("SELECT * FROM gptl_ballboy")) {
            while(result.next()) {
                String id = result.getString("id");
                String ballboyTeamId = result.getString("ballboy_team_id");
                String name = result.getString("name");
                String firstname = result.getString("firstname");
                int age = result.getInt("age");
                String nationality = result.getString("nationality");
                list.put(id, new Ballboy(id, name, firstname, age, nationality, ballboyTeamId));
            }
            result.close();
            statement.close();
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(DBAccess.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    /**
     * Récupère toutes les instances de la table GPTL_BALLBOY_TEAM
     * @return list : Map contenant toutes les instances des équipes de ramasseurs de balles de la base de donnée
     */
    public Map<String,BallboyTeam> getAllBallboysTeam() {
        Map<String,BallboyTeam> list = new HashMap();
        try(Statement statement = connection.createStatement(); 
            ResultSet result = statement.executeQuery("SELECT * FROM gptl_ballboy_team")) {
            while(result.next()) {
                String id = result.getString("ballboy_team_id");
                Ballboy ballBoyOne = Ballboy.BALLBOYS.get(result.getString("ballboy1_id"));
                Ballboy ballBoyTwo = Ballboy.BALLBOYS.get(result.getString("ballboy2_id"));
                Ballboy ballBoyThree = Ballboy.BALLBOYS.get(result.getString("ballboy3_id"));
                Ballboy ballBoyFour = Ballboy.BALLBOYS.get(result.getString("ballboy4_id"));
                Ballboy ballBoyFive = Ballboy.BALLBOYS.get(result.getString("ballboy5_id"));
                Ballboy ballBoySix = Ballboy.BALLBOYS.get(result.getString("ballboy6_id"));
                list.put(id, new BallboyTeam(id, ballBoyOne, ballBoyTwo, ballBoyThree, ballBoyFour, ballBoyFive, ballBoySix));
            }
            result.close();
            statement.close();
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(DBAccess.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    /**
     * Supprime le match passé en paramètre de la base de donnée
     * @param match Match à supprimé
     */
    public void deleteMatch(Match match) {
        int index = Match.MATCHES.indexOf(match);
        int id = Match.MATCHES.get(index).getId();
        try(PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM gptl_match WHERE id= ?")) {
            preparedStatement.setInt(1,id);
            ResultSet result = preparedStatement.executeQuery();
            result.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        Match.MATCHES.remove(index);
    }

    /**
     * Ajoute le match Simple avec les arguments passés en paramètre
     * @param id id du match
     * @param type type du match
     * @param date date + heure du match
     * @param court court du match
     * @param player1Id ID du premier joueur
     * @param player2Id ID du deuxième joueur
     * @param umpireId ID de l'arbitre de chaise
     * @param netRefereeId ID de l'arbitre de filet
     * @param ballboyTeam1Id ID de la première equipe de ramasseur de balle
     * @param ballboyTeam2Id ID de la deuxième equipe de ramasseur de balle
     */
    public void addSimpleMatch(int id, String type, Date date, String court, String player1Id, String player2Id, String umpireId, String netRefereeId, String ballboyTeam1Id, String ballboyTeam2Id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO gptl_match (id,type,player1_id,player2_id,ballboy_team1_id,ballboy_team2_id,umpire_id,net_referee_id,time,court) VALUES (?,?,?,?,?,?,?,?,?,?)")) {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2,type);
            preparedStatement.setString(3, player1Id);
            preparedStatement.setString(4, player2Id);
            preparedStatement.setString(5, ballboyTeam1Id);
            preparedStatement.setString(6, ballboyTeam2Id);
            preparedStatement.setString(7, umpireId);
            preparedStatement.setString(8, netRefereeId);
            preparedStatement.setDate(9, new java.sql.Date(date.getTime()));
            preparedStatement.setString(10,court);
            preparedStatement.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(DBAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Ajoute le match Double avec les arguments passés en paramètre
     * @param id id du match
     * @param type type du match
     * @param date date + heure du match
     * @param court court du match
     * @param player1Id ID du premier joueur
     * @param player2Id ID du deuxième joueur
     * @param player3Id ID du troisième joueur
     * @param player4Id ID du quatrième joueur
     * @param umpireId ID de l'arbitre de chaise
     * @param netRefereeId ID de l'arbitre de filet
     * @param ballboyTeam1Id ID de la première equipe de ramasseur de balle 
     * @param ballboyTeam2Id ID de la deuxième equipe de ramasseur de balle
     */
    public void addDoubleMatch(int id, String type, Date date, String court, String player1Id, String player2Id, String player3Id, String player4Id, String umpireId, String netRefereeId, String ballboyTeam1Id, String ballboyTeam2Id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO gptl_match (id,type,player1_id,player2_id,player3_id,player4_id,ballboy_team1_id,ballboy_team2_id,umpire_id,net_referee_id,time,court) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)")) {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2,type);
            preparedStatement.setString(3, player1Id);
            preparedStatement.setString(4, player2Id);
            preparedStatement.setString(5, player3Id);
            preparedStatement.setString(6, player4Id);
            preparedStatement.setString(7, ballboyTeam1Id);
            preparedStatement.setString(8, ballboyTeam2Id);
            preparedStatement.setString(9, umpireId);
            preparedStatement.setString(10, netRefereeId);
            preparedStatement.setDate(11, new java.sql.Date(date.getTime()));
            preparedStatement.setString(12,court);
            preparedStatement.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(DBAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Modifie match Double avec les paramètres passés en arguments
     * @param id id du match
     * @param type type du match
     * @param date date + heure du match
     * @param court court du match
     * @param player1Id ID du premier joueur
     * @param player2Id ID du deuxième joueur
     * @param player3Id ID du troisième joueur
     * @param player4Id ID du quatrième joueur
     * @param umpireId ID de l'arbitre de chaise
     * @param netRefereeId ID de l'arbitre de filet
     * @param ballboyTeam1Id ID de la première equipe de ramasseur de balle 
     * @param ballboyTeam2Id ID de la deuxième equipe de ramasseur de balle
     * @param winnerId ID du vainqueur du match
     */
    public void modifyDoubleMatch(int id,String type, Date date, String court, String player1Id, String player2Id, String player3Id, String player4Id, String umpireId, String netRefereeId, String ballboyTeam1Id, String ballboyTeam2Id, String winnerId) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE gptl_match SET type=?,player1_id=?,player2_id=?,player3_id=?,player4_id=?,ballboy_team1_id=?,ballboy_team2_id=?,umpire_id=?,net_referee_id=?,time=?,court=?,winner_id=? WHERE id=?")) {
            preparedStatement.setString(1,type);
            preparedStatement.setString(2, player1Id);
            preparedStatement.setString(3, player2Id);
            preparedStatement.setString(4, player3Id);
            preparedStatement.setString(5, player4Id);
            preparedStatement.setString(6, ballboyTeam1Id);
            preparedStatement.setString(7, ballboyTeam2Id);
            preparedStatement.setString(8, umpireId);
            preparedStatement.setString(9, netRefereeId);
            preparedStatement.setDate(10, new java.sql.Date(date.getTime()));
            preparedStatement.setString(11,court);
            preparedStatement.setString(12,winnerId);
            preparedStatement.setInt(13,id);
            preparedStatement.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(DBAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Modifie le match Simple avec les paramètres passés en arguments
     * @param id id du match
     * @param type type du match
     * @param date date + heure du match
     * @param court court du match
     * @param player1Id ID du premier joueur
     * @param player2Id ID du deuxième joueur
     * @param umpireId ID de l'arbitre de chaise
     * @param netRefereeId ID de l'arbitre de filet
     * @param ballboyTeam1Id ID de la première equipe de ramasseur de balle
     * @param ballboyTeam2Id ID de la deuxième equipe de ramasseur de balle
     * @param winnerId ID du vainqueur du match
     */
    public void modifySimpleMatch(int id, String type, Date date, String court, String player1Id, String player2Id, String umpireId, String netRefereeId, String ballboyTeam1Id, String ballboyTeam2Id, String winnerId) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE gptl_match SET type=?,player1_id=?,player2_id=?,player3_id=?,player4_id=?,ballboy_team1_id=?,ballboy_team2_id=?,umpire_id=?,net_referee_id=?,time=?,court=?,winner_id=? WHERE id=?")) {
            preparedStatement.setString(1,type);
            preparedStatement.setString(2, player1Id);
            preparedStatement.setString(3, player2Id);
            preparedStatement.setNull(4, java.sql.Types.VARCHAR);
            preparedStatement.setNull(5, java.sql.Types.VARCHAR);
            preparedStatement.setString(6, ballboyTeam1Id);
            preparedStatement.setString(7, ballboyTeam2Id);
            preparedStatement.setString(8, umpireId);
            preparedStatement.setString(9, netRefereeId);
            preparedStatement.setDate(10, new java.sql.Date(date.getTime()));
            preparedStatement.setString(11,court);
            preparedStatement.setString(12,winnerId);
            preparedStatement.setInt(13,id);
            preparedStatement.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(DBAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Récupère toutes les instances de la table GPTL_RESERVATION
     * @return list : ArrayList contenant toutes les instances des réservations de la base de donnée
     */
    public List<Reservation> getAllReservations() {
        ArrayList<Reservation> list = new ArrayList();
        try (Statement statement = connection.createStatement(); 
             ResultSet result = statement.executeQuery("SELECT * FROM gptl_reservation")) {
            while(result.next()) {
                int id = result.getInt("ID");
                String playerId = result.getString("PLAYERID");
                String court = result.getString("COURT");
                LocalDateTime time = DateConvertUtils.asLocalDateTime(result.getDate("TIME"));
                
                list.add(new Reservation(id, playerId, court, time));
            }  
            result.close();
            statement.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    /**
     * Ajoute une réservation avec les paramètres passés en arguments
     * @param id id de la réservation
     * @param playerID ID du joueur ayant effectué la réservation
     * @param court court de la réservation
     * @param time date + heure de la réservation
     */
    public void addReservation(int id, String playerID, String court, Date time) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO gptl_reservation (id,playerid,court,time) VALUES (?,?,?,?)")) {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2,playerID);
            preparedStatement.setString(3, court);
            preparedStatement.setDate(4, new java.sql.Date(time.getTime()));
            preparedStatement.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(DBAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Supprime la réservation passée en paramètre
     * @param reservation reservation à supprimée
     */
    public void deleteReservation(Reservation reservation) {
        int index = Reservation.RESERVATIONS.indexOf(reservation);
        int id = Reservation.RESERVATIONS.get(index).getId();
        try(PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM gptl_reservation WHERE id= ?")) {
            preparedStatement.setInt(1,id);
            ResultSet result = preparedStatement.executeQuery();
            result.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        Reservation.RESERVATIONS.remove(index);
    }
}
