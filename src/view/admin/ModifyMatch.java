package view.admin;

import controller.database.DBAccess;
import java.awt.Dimension;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import utils.DateConvertUtils;
import model.business.Match;
import model.business.BallboyTeam;
import model.business.Player;
import model.business.Referee;

/**
 * Fenêtre de modification de match
 * @author Kevin GUTIERREZ
 * @author Pierre DAUTREY
 * @author Corentin BECT
 */
public class ModifyMatch extends AddMatch {    
    
    public ModifyMatch(String title, JFrame parent, SchedulePanel selectedTable) {
        super(title,parent, selectedTable);
        initComponents();
    }

    private void initComponents() {
        
        addButton.setText("Modifier");
        
        int selectedRow = selectedTable.getDaySchedule().getSelectedRow();
        int selectedColumn = selectedTable.getDaySchedule().getSelectedColumn();
        
        Match match = (Match) selectedTable.getDaySchedule().getValueAt(selectedRow, selectedColumn);
        
        /* Initialise la JComboBox avec les gagants potentiels et le gagnant 
           sélectionné dans le cas où un gagnant est déjà saisi */
        winnerPanel.getComboBox().addItem(Player.PLAYERS.get(match.getPlayerOneId()));
        winnerPanel.getComboBox().addItem(Player.PLAYERS.get(match.getPlayerTwoId()));
        winnerPanel.getComboBox().setSelectedItem(Player.PLAYERS.get(match.getWinnerId()));
        
        // Initialise les JComboBox avec les joueurs sélectionnés
        playerOne.getComboBox().setSelectedItem(Player.PLAYERS.get(match.getPlayerOneId()));
        playerTwo.getComboBox().setSelectedItem(Player.PLAYERS.get(match.getPlayerTwoId()));
        playerThree.getComboBox().setSelectedItem(Player.PLAYERS.get(match.getPlayerThreeId()));
        playerFour.getComboBox().setSelectedItem(Player.PLAYERS.get(match.getPlayerFourId()));
        
        if(match.getType().equals("Double")) {
            doubleButton.setSelected(true);
            addPlayersComboBox();
            setSize(new Dimension(800,350));
            winnerPanel.getComboBox().addItem(Player.PLAYERS.get(match.getPlayerThreeId()));
            winnerPanel.getComboBox().addItem(Player.PLAYERS.get(match.getPlayerFourId()));
        }
        
        // Initialise les JComboBox avec les équipes de ramasseurs sélectionnées
        ballTeamOnePanel.getComboBox().setSelectedItem(BallboyTeam.BALLBOYSTEAM.get(match.getBallBoyTeamOneId()));
        ballTeamTwoPanel.getComboBox().setSelectedItem(BallboyTeam.BALLBOYSTEAM.get(match.getBallBoyTeamTwoId()));
        
        // Initialise les JComboBox avec les arbitres sélectionnés
        umpirePanel.getComboBox().setSelectedItem(Referee.REFEREES.get(match.getUmpireId()));
        netRefereePanel.getComboBox().setSelectedItem(Referee.REFEREES.get(match.getNetRefereeId()));
       
        winnerPanel.setVisible(true);
        winnerPanel.setMaximumSize(new Dimension(winnerPanel.getPreferredSize().width*2,winnerPanel.getPreferredSize().height));
        revalidate();
        repaint();
        pack();
    }
    
    /**
     * Redéfini la méthode addMatch pour qu'elle mette à jour les matchs au lieu
     * d'ajouter de nouveau match
     */
    @Override
    protected void addMatch() {
        String type = "Simple";
        
        // Récupère les informations de l'horaire et du court sélectionné
        int selectedRow = selectedTable.getDaySchedule().getSelectedRow();
        int selectedColumn = selectedTable.getDaySchedule().getSelectedColumn();
        int hour = Integer.parseInt(selectedTable.getDaySchedule().getValueAt(selectedRow, 0).toString().replace("h", ""));
        LocalDateTime time = selectedTable.getDate().atTime(hour, 0);
        Date date = DateConvertUtils.asUtilDate(time);
        String court = selectedTable.getDaySchedule().getColumnName(selectedColumn);
        
        // Récupère les ID des joueurs séléctionnés
        Player player1 =  (Player) playerOne.getComboBox().getSelectedItem();
        String player1Id = player1.getId();
        Player player2 =  (Player) playerTwo.getComboBox().getSelectedItem();
        String player2Id = player2.getId();
        String player3Id = "";
        String player4Id = "";
        boolean playersOk = false;
        
        // Récupère les ID des arbitres séléctionnés
        Referee umpire = (Referee) umpirePanel.getComboBox().getSelectedItem();
        String umpireId = umpire.getId();
        Referee netReferee = (Referee) netRefereePanel.getComboBox().getSelectedItem();
        String netRefereeId = netReferee.getId();
        
        // Récupère les ID des équipes de ramasseurs de balles séléctionnées
        BallboyTeam ballboyTeam1 = (BallboyTeam) ballTeamOnePanel.getComboBox().getSelectedItem();
        String ballboyTeam1Id = ballboyTeam1.getId();
        BallboyTeam ballboyTeam2 = (BallboyTeam) ballTeamTwoPanel.getComboBox().getSelectedItem();
        String ballboyTeam2Id = ballboyTeam2.getId();
        
        // Récupère l'ID du gagnant sélectionné
        Player winner = (Player) winnerPanel.getComboBox().getSelectedItem();
        String winnerId = winner.getId();
        
        // Si type Double sélectionné, récupère les ID des joueurs supplémentaires
        if(doubleButton.isSelected()) {
            type = "Double";
            Player player3 =  (Player) playerThree.getComboBox().getSelectedItem();
            player3Id = player3.getId();
            Player player4 =  (Player) playerFour.getComboBox().getSelectedItem();
            player4Id = player4.getId();
            if(player1Id.equals(player2Id) || player1Id.equals(player3Id) || player1Id.equals(player4Id) ||
               player2Id.equals(player3Id) || player2Id.equals(player4Id) ||
               player3Id.equals(player4Id)) { // Vérifie que les joueurs séléctionnés sont différents
                JOptionPane.showMessageDialog(this,"Veuillez sélectionner des joueurs différents");
            }
            else {
                playersOk = true;
            }
        }
        else {
            if(player1Id.equals(player2Id)) // Vérifie que les joueurs séléctionnés sont différents
                JOptionPane.showMessageDialog(this,"Veuillez sélectionner des joueurs différents");
            else 
                playersOk = true;
        }

        Match match = (Match) selectedTable.getDaySchedule().getValueAt(selectedRow, selectedColumn);
        
        if(playersOk) {
            if(ballboyTeam1Id.equals(ballboyTeam2Id)) { // Vérifie que les ramasseurs séléctionnés sont différents
                JOptionPane.showMessageDialog(this,"Veuillez sélectionner des équipes de ramasseurs différentes");
            }
            else { // Les arbitres de chaise et de filet sont de catégorie différente donc forcément différents
                try {
                    DBAccess db = new DBAccess();
                    if(doubleButton.isSelected()) { // Modifie un match Double
                        db.modifyDoubleMatch(match.getId(),type,date,court,player1Id,player2Id,player3Id,player4Id,umpireId,netRefereeId,ballboyTeam1Id,ballboyTeam2Id, winnerId);
                        Match match2 = new Match(match.getId(), type, player1Id, player2Id, player3Id, player4Id, ballboyTeam1Id, ballboyTeam2Id, umpireId, netRefereeId, null, null, null, null, null, null, null, null, time, court, winnerId, "", null);
                        Match.MATCHES.set(Match.MATCHES.indexOf(match), match2);
                        selectedTable.getDaySchedule().setValueAt(match2, selectedRow, selectedColumn);
                        dispose();
                    }
                    else { // Modifie un match simple
                        db.modifySimpleMatch(match.getId(),type,date,court,player1Id,player2Id,umpireId,netRefereeId,ballboyTeam1Id,ballboyTeam2Id,winnerId);
                        Match match2 = new Match(match.getId(), type, player1Id, player2Id, "", "", ballboyTeam1Id, ballboyTeam2Id, umpireId, netRefereeId, null, null, null, null, null, null, null, null, time, court, winnerId, "", null);
                        Match.MATCHES.set(Match.MATCHES.indexOf(match), match2);
                        selectedTable.getDaySchedule().setValueAt(match2, selectedRow, selectedColumn);
                        dispose();
                    }
                    
                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    Logger.getLogger(AddMatch.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
}
