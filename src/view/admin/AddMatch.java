package view.admin;

import controller.database.DBAccess;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import utils.DateConvertUtils;
import model.business.Match;
import model.business.BallboyTeam;
import model.business.Player;
import model.business.Referee;

/**
 * Fenêtre d'ajout de match
 * @author Kevin GUTIERREZ
 * @author Pierre DAUTREY
 * @author Corentin BECT
 */
public class AddMatch extends JDialog {

    protected final JLabel dateLabel = new JLabel("Date : ");
    protected final JLabel timeLabel = new JLabel("Horaire : ");
    protected final JLabel courtLabel = new JLabel("Court : ");
    
    protected final JRadioButton singleButton = new JRadioButton("Simple");
    protected final JRadioButton doubleButton = new JRadioButton("Double");
    
    protected final JPanel playersPanel = new JPanel();
    protected final ComboBoxLabel playerOne = new ComboBoxLabel("Joueur 1 :", new JComboBox());
    protected final ComboBoxLabel playerTwo = new ComboBoxLabel("Joueur 2 :", new JComboBox());
    protected final ComboBoxLabel playerThree = new ComboBoxLabel("Joueur 3 :", new JComboBox());
    protected final ComboBoxLabel playerFour = new ComboBoxLabel("Joueur 4 :", new JComboBox());
    
    protected final ComboBoxLabel ballTeamOnePanel = new ComboBoxLabel("Equipe ramasseur 1 :", new JComboBox());
    protected final ComboBoxLabel ballTeamTwoPanel = new ComboBoxLabel("Equipe ramasseur 2 :", new JComboBox());
    
    protected final ComboBoxLabel umpirePanel = new ComboBoxLabel("Arbitre de chaise :", new JComboBox());
    protected final ComboBoxLabel netRefereePanel = new ComboBoxLabel("Arbitre de filet :", new JComboBox());
    
    protected final ComboBoxLabel winnerPanel = new ComboBoxLabel("Gagnant :", new JComboBox());
    
    protected JButton addButton = new JButton("Ajouter");
    protected final JButton cancelButton = new JButton("Annuler");
    protected final SchedulePanel selectedTable;
    protected final JFrame parent;
    
    public AddMatch(String title, JFrame parent, SchedulePanel selectedTable) {
        super(parent,title,true);
        this.parent = parent;
        this.selectedTable = selectedTable; 
        initComponents();
    }

    private void initComponents() {        
        setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
        
        // Informations du match
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel,BoxLayout.Y_AXIS));
        
        // Récupérer la date sélectionnée
        dateLabel.setText("Date : " + 
                              selectedTable.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
 
        // Récupérer l'heure sélectionnée
        int selectedRow = selectedTable.getDaySchedule().getSelectedRow();
        int selectedColumn = selectedTable.getDaySchedule().getSelectedColumn();

        timeLabel.setText("Horaire : " + 
                          selectedTable.getDaySchedule().getValueAt(selectedRow, 0).toString() + "00");
        
        // Récupérer le court sélectionné
        courtLabel.setText("Court : " + selectedTable.getDaySchedule().getColumnName(selectedColumn));
        
        labelPanel.add(dateLabel); 
        labelPanel.add(timeLabel);       
        labelPanel.add(courtLabel);
        labelPanel.setMaximumSize(labelPanel.getPreferredSize());
                
        // Type du match    
        JPanel typePanel = new JPanel();
        typePanel.setLayout(new BoxLayout(typePanel,BoxLayout.X_AXIS));
        ButtonGroup typeButtonGroup = new ButtonGroup();
        singleButton.setSelected(true);
        typeButtonGroup.add(singleButton);
        typeButtonGroup.add(doubleButton);
        typePanel.add(singleButton);
        typePanel.add(Box.createRigidArea(new Dimension(10,0)));
        typePanel.add(doubleButton);
        
        // Joueurs du match
        for(Entry<String, Player> entry : Player.PLAYERS.entrySet()) {
            playerOne.getComboBox().addItem(entry.getValue());
            playerTwo.getComboBox().addItem(entry.getValue());
            playerThree.getComboBox().addItem(entry.getValue());
            playerFour.getComboBox().addItem(entry.getValue());
        }
        
        
        GridLayout gridLayout = new GridLayout(1,4);
        gridLayout.setHgap(10);
        playersPanel.setLayout(gridLayout);
        playersPanel.add(playerOne);
        playersPanel.add(playerTwo);
        playersPanel.setMaximumSize(new Dimension(playersPanel.getPreferredSize().width*2,playersPanel.getPreferredSize().height));
        
        // Arbitres du match
        for(Entry<String, Referee> entry : Referee.REFEREES.entrySet()) {
            if(entry.getValue().getMatchNumber() < 4 && entry.getValue().getCategory().equals("ITT1")) {
                umpirePanel.getComboBox().addItem(entry.getValue());
            }
            else if(entry.getValue().getMatchNumber() < 4 && entry.getValue().getCategory().equals("JAT2")) {
                netRefereePanel.getComboBox().addItem(entry.getValue());
            }
        }
        
        // Equipe de ramasseurs de balles du match
        for(Entry<String, BallboyTeam> entry : BallboyTeam.BALLBOYSTEAM.entrySet()) {
            ballTeamOnePanel.getComboBox().addItem(entry.getValue());
            ballTeamTwoPanel.getComboBox().addItem(entry.getValue());
        }
        
        JPanel ballRefereePanel = new JPanel();
        gridLayout = new GridLayout(2,3);
        gridLayout.setVgap(10);
        ballRefereePanel.setLayout(gridLayout);
        ballRefereePanel.add(ballTeamOnePanel);
        ballRefereePanel.add(Box.createRigidArea(new Dimension(10,0)));
        ballRefereePanel.add(umpirePanel); 
        ballRefereePanel.add(ballTeamTwoPanel);
        ballRefereePanel.add(Box.createRigidArea(new Dimension(10,0)));
        ballRefereePanel.add(netRefereePanel);
        ballRefereePanel.setMaximumSize(new Dimension(ballRefereePanel.getPreferredSize().width,ballRefereePanel.getPreferredSize().height));
        
        // Gagnant du match
        add(winnerPanel);
        winnerPanel.setVisible(false);
        
        // Boutons d'ajout du match et d'annulation
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel,BoxLayout.X_AXIS));
        buttonPanel.add(addButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(10,0)));
        buttonPanel.add(cancelButton);
        
        
        // Ajout de tout les panels au JDialog principal
        add(Box.createRigidArea(new Dimension(0,10)));
        add(labelPanel);
        add(Box.createRigidArea(new Dimension(0,10)));
        add(typePanel);
        add(Box.createRigidArea(new Dimension(0,10)));
        add(playersPanel);
        add(Box.createRigidArea(new Dimension(0,10)));
        add(ballRefereePanel);
        add(Box.createRigidArea(new Dimension(0,20)));
        add(buttonPanel);
        
        pack();
        setSize(new Dimension(400,320));
        setLocationRelativeTo(parent);
        
        // Ajout des ActionListener
        singleButton.addActionListener((ActionEvent e) -> {
            actionPermorfed(e);
        });
        
        doubleButton.addActionListener((ActionEvent e) -> {
            actionPermorfed(e);
        });
        
        addButton.addActionListener((ActionEvent e) -> {
            actionPermorfed(e);
        });
        
        cancelButton.addActionListener((ActionEvent e) -> {
            actionPermorfed(e);
        });
        
    }

    private void actionPermorfed(ActionEvent e) {
        if (e.getSource() == singleButton) // RadioButton match Simple
            removePlayersComboBox();
        else if(e.getSource() == doubleButton) // RadioButton match Double
            addPlayersComboBox();
        else if(e.getSource() == addButton) // Bouton d'ajout de match
            addMatch();
        else if(e.getSource() == cancelButton) // Bouton d'annulation
            dispose();
    }

    /**
     * Ajoute deux ComboBox supplémentaires si type de match sélectionné est Double
     */
    protected void addPlayersComboBox() {
        playersPanel.add(playerThree);
        playersPanel.add(playerFour);
        setSize(new Dimension(800,320));
        playersPanel.setMaximumSize(new Dimension(playersPanel.getPreferredSize().width+100,playersPanel.getPreferredSize().height));
        playersPanel.revalidate();
        playersPanel.repaint();
        setLocationRelativeTo(parent);
    }
    
    /**
     * Retire deux ComboBox si type de match sélectionné est Simple
     */
    private void removePlayersComboBox() {
        playersPanel.remove(playerThree);      
        playersPanel.remove(playerFour);
        setSize(new Dimension(400,320));
        playersPanel.setMaximumSize(new Dimension(playersPanel.getPreferredSize().width*2,playersPanel.getPreferredSize().height));
        playersPanel.revalidate();
        playersPanel.repaint();
        setLocationRelativeTo(parent);
    }

    /**
     * Vérifie les contraintes avec les informations séléctionnées et ajoute
     * le match à la base de donnée et à l'ArrayList MATCHES de la classe Match
     */
    protected void addMatch() {
        
        int id = Match.getMaxId(); // Récupère l'ID maximum de la liste des matchs
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
        
        if(playersOk) {
            if(ballboyTeam1Id.equals(ballboyTeam2Id)) { // Vérifie que les ramasseurs séléctionnés sont différents
                JOptionPane.showMessageDialog(this,"Veuillez sélectionner des équipes de ramasseurs différentes");
            }
            else { // Les arbitres de chaise et de filet sont de catégorie différente donc forcément différents
                try {
                    DBAccess db = new DBAccess();
                    if(doubleButton.isSelected()) { // Ajoute un match Double
                        db.addDoubleMatch(id,type,date,court,player1Id,player2Id,player3Id,player4Id,umpireId,netRefereeId,ballboyTeam1Id,ballboyTeam2Id);
                        Match match = new Match(id, type, player1Id, player2Id, player3Id, player4Id, ballboyTeam1Id, ballboyTeam2Id, umpireId, netRefereeId, null, null, null, null, null, null, null, null, time, court, "", "", null);
                        Match.MATCHES.add(match);
                        selectedTable.getDaySchedule().setValueAt(match, selectedRow, selectedColumn);
                        dispose();
                    }
                    else { // Ajoute un match Simple
                        db.addSimpleMatch(id,type,date,court,player1Id,player2Id,umpireId,netRefereeId,ballboyTeam1Id,ballboyTeam2Id);
                        Match match = new Match(id, type, player1Id, player2Id, "", "", ballboyTeam1Id, ballboyTeam2Id, umpireId, netRefereeId, null, null, null, null, null, null, null, null, time, court, "", "", null);
                        Match.MATCHES.add(match);
                        selectedTable.getDaySchedule().setValueAt(match, selectedRow, selectedColumn);
                        dispose();
                    }
                    
                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    Logger.getLogger(AddMatch.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    
    
}
