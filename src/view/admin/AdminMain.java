package view.admin;

import controller.database.DBAccess;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import model.business.Match;
import net.miginfocom.swing.MigLayout;

/**
 * Fenêtre principale de gestion du planning des matchs
 * @author Kevin GUTIERREZ
 * @author Pierre DAUTREY
 * @author Corentin BECT
 */
public class AdminMain extends JPanel {
    
    private final JFrame parent;
    
    private final JPanel schedulePanel = new JPanel(new MigLayout("","","[center]"));
    private final List<SchedulePanel> scheduleList = new ArrayList();
    private final JButton previousDayButton = new JButton("<<");
    private final JButton nextDayButton = new JButton(">>");
    private SchedulePanel selectedTable;
    
    private final JPanel detailPanel = new JPanel(new MigLayout());
    private final JLabel dateLabel = new JLabel("Date : ");
    private final JLabel timeLabel = new JLabel("Horaire : ");
    private final JLabel eventLabel = new JLabel("Aucun match pour l'horaire sélectionné");
    private final JLabel courtLabel = new JLabel("Court : ");
    
    private final JPanel buttonPanel = new JPanel(new MigLayout());
    private final JButton addMatchButton = new JButton("Ajouter un match");
    private final JButton modifyMatchButton = new JButton("Modifier le match");
    private final JButton deleteMatchButton = new JButton("Supprimer le match");
    
    
    public AdminMain(JFrame parent) {
        this.parent = parent;
        this.parent.setResizable(false);
        this.parent.setLocationRelativeTo(null);
        for(int i = 0; i < 9; i++) {
            scheduleList.add(new SchedulePanel());
        }
        
        selectedTable = scheduleList.get(0);
        
        initComponents();
    }
    
    private void initComponents() {
        setLayout(new MigLayout("align center","[center]",""));
        
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Planning"));
        
        // Table des matchs + bouton jour précédent/suivant
        schedulePanel.add(previousDayButton);
        schedulePanel.add(selectedTable);
        schedulePanel.add(nextDayButton);
        initSchedules();
        
        // Détails du matchs
        detailPanel.add(dateLabel,"wrap");
        detailPanel.add(timeLabel,"wrap");
        detailPanel.add(eventLabel,"wrap");
        detailPanel.add(courtLabel,"wrap");
        
        // Bouton d'ajout, de modification et de suppression de match
        buttonPanel.add(addMatchButton,"wrap");
        buttonPanel.add(modifyMatchButton,"wrap");
        buttonPanel.add(deleteMatchButton);
        
        add(schedulePanel,"wrap");
        add(detailPanel,"split 2");
        add(buttonPanel,"gapleft 50");
        
        
        // Ajout des ActionListener
        previousDayButton.addActionListener((ActionEvent e) -> {
            actionPermorfed(e);
        });
        
        nextDayButton.addActionListener((ActionEvent e) -> {
            actionPermorfed(e);
        });
        
        addMatchButton.addActionListener((ActionEvent e) -> {
            actionPermorfed(e);
        });
        
        modifyMatchButton.addActionListener((ActionEvent e) -> {
            actionPermorfed(e);
        });
        
        deleteMatchButton.addActionListener((ActionEvent e) -> {
            actionPermorfed(e);
        });
        
        /* MouseListener qui va mettre à jour les informations du match à 
          chaque changement de cellule séléctionné sur la table */
        MouseListener tableMouseListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
               updateLabels();
            }
         };
        
        // Ajout du MouseListener sur chaque table
        for(SchedulePanel panel : scheduleList) {
            panel.getDaySchedule().addMouseListener(tableMouseListener);
        }
    }
    
    /**
     * Initialise les matchs déjà existant dans la base de données
     */
    private void initSchedules() {
        for (SchedulePanel panel : scheduleList) {
            JTable table = panel.getDaySchedule();
            for(Match match : Match.MATCHES) {
                int index = table.getColumn(match.getCourt()).getModelIndex();
                if(panel.getDate().equals(match.getTime().toLocalDate()) ) {
                    if(match.getTime().equals(panel.getDate().atTime(8, 0))) 
                        table.setValueAt(match, 0, index);
                    else if(match.getTime().equals(panel.getDate().atTime(11, 0))) 
                        table.setValueAt(match, 1, index);
                    else if(match.getTime().equals(panel.getDate().atTime(15, 0))) 
                        table.setValueAt(match, 2, index);
                    else if(match.getTime().equals(panel.getDate().atTime(18, 0)))
                        table.setValueAt(match, 3, index);
                    else if(match.getTime().equals(panel.getDate().atTime(21, 0)))
                        table.setValueAt(match, 4, index);
                }
            }           
        }
    }

    private void actionPermorfed(ActionEvent e) {
        if(e.getSource() == previousDayButton) { // Bouton jour précédent
            if(scheduleList.indexOf(selectedTable) > 0)
                previousDay();
        }
        else if(e.getSource() == nextDayButton) { // Bouton jour suivant
            if(scheduleList.indexOf(selectedTable) < 8)
                nextDay();
        }
        else if(e.getSource() == addMatchButton) { // Bouton ajout de match
            addMatch();
        }
        else if(e.getSource() == modifyMatchButton) { // Bouton modification de match
            modifyMatch();
        }
        else if(e.getSource() == deleteMatchButton) { // Bouton suppression de match
            deleteMatch();    
        }
            
    }
    
    /**
     * Met à jour les informations des labels avec les informations du match
     * sélectionné
     */
    private void updateLabels() {
        // Teste si la colonne sélectionnée est différente de 0 (horaire)
        if(selectedTable.getDaySchedule().getSelectedColumn() != 0) {
            dateLabel.setText("Date : " + 
                              selectedTable.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        
            int selectedRow = selectedTable.getDaySchedule().getSelectedRow();
            int selectedColumn = selectedTable.getDaySchedule().getSelectedColumn();

            timeLabel.setText("Horaire : " + 
                              selectedTable.getDaySchedule().getValueAt(selectedRow, 0).toString() + "00");
            
            // Teste si la cellule séléctionné contient un match
            if(selectedTable.getDaySchedule().getValueAt(selectedRow, selectedColumn) instanceof Match) {
                Object match = (Match) selectedTable.getDaySchedule().getValueAt(selectedRow, selectedColumn);
                eventLabel.setText("Match : " + match.toString());
            }

            courtLabel.setText("Court : " + selectedTable.getDaySchedule().getColumnName(selectedColumn));
        }
        else { // Réinitialise les labels
            dateLabel.setText("Date : ");
            timeLabel.setText("Horaire : ");
            eventLabel.setText("Aucun match pour l'horaire sélectionné");
            courtLabel.setText("Court : "); 
        }
    }
    
    /**
     * Définit la table du jour précédent comme table sélectionnée et l'affiche
     */
    private void previousDay() {
        selectedTable.getDaySchedule().getSelectionModel().clearSelection();
        selectedTable = scheduleList.get(scheduleList.indexOf(selectedTable) - 1);
        schedulePanel.removeAll();
        schedulePanel.add(previousDayButton);        
        schedulePanel.add(selectedTable);
        schedulePanel.add(nextDayButton);
        revalidate();
        repaint();
    }
    
    /**
     * Définit la table du jour suivant comme table sélectionnée et l'affiche
     */
    private void nextDay() {
        selectedTable.getDaySchedule().getSelectionModel().clearSelection();
        selectedTable = scheduleList.get(scheduleList.indexOf(selectedTable) + 1);
        schedulePanel.removeAll();
        schedulePanel.add(previousDayButton);        
        schedulePanel.add(selectedTable);
        schedulePanel.add(nextDayButton);
        revalidate();
        repaint();
    }
    
    /**
     * Vérifie que la cellule séléctionné peut bien avoir un match ajouté et
     * ouvre la fenêtre d'ajout de match dans le cas échéant
     */
    private void addMatch() {
        // Vérifie si une cellule est bien sélectionnée
        if(selectedTable.getDaySchedule().getSelectedRow() != -1) {
            int selectedRow = selectedTable.getDaySchedule().getSelectedRow();
            // Vérifie si la cellule ne contient pas de match
            int selectedColumn = selectedTable.getDaySchedule().getSelectedColumn();
            if(selectedTable.getDaySchedule().getValueAt(selectedRow, selectedColumn) instanceof Match) {
                JOptionPane.showMessageDialog(this, "Impossible d'ajouter un match.\nIl existe déjà un match pour ce créneau.");
            }
            else if(selectedColumn == 0) { // Vérifie si la cellule séléctionnée n'est pas une cellule d'horaire
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un créneau horaire pour ajouter un match.");
            }
            else {
                new AddMatch("Ajouter un match",parent,selectedTable).setVisible(true);
            }   
        }
        else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un créneau horaire");
        }
    }
    
    /**
     * Vérifie que la cellule séléctionné peut bien avoir un match modifié et
     * ouvre la fenêtre de modification de match dans le cas échéant
     */
    private void modifyMatch() {
        // Vérifie si une cellule est bien sélectionnée
        if(selectedTable.getDaySchedule().getSelectedRow() != -1) {
            int selectedRow = selectedTable.getDaySchedule().getSelectedRow();
            int selectedColumn = selectedTable.getDaySchedule().getSelectedColumn();
            // Vérifie si la cellule contient bien un match
            if(!(selectedTable.getDaySchedule().getValueAt(selectedRow, selectedColumn) instanceof Match)) {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un créneau horaire avec un match pour le modifier.");
            }
            else {
                new ModifyMatch("Ajouter un match",parent,selectedTable).setVisible(true);
            } 
        }
        else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un créneau horaire");
        }
    }
    
    /**
     * Vérifie que la cellule séléctionné peut bien avoir un match supprimé,
     * ouvre la fenêtre de confirmation de suppression de match dans le cas échéant
     * puis supprime le match en cas de confirmation
     */
    private void deleteMatch() {
        // Vérifie si une cellule est bien sélectionnée
        if(selectedTable.getDaySchedule().getSelectedRow() != -1) {
            int selectedRow = selectedTable.getDaySchedule().getSelectedRow();
            int selectedColumn = selectedTable.getDaySchedule().getSelectedColumn();
            // Vérifie si la cellule contient bien un match
            if(!(selectedTable.getDaySchedule().getValueAt(selectedRow, selectedColumn) instanceof Match)) {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un créneau horaire avec un match pour le modifier.");
            }
            else {
                int dialogResult = JOptionPane.showConfirmDialog (this, "Etes-vous sûr de vouloir supprimer ce match ?");
                if(dialogResult == JOptionPane.YES_OPTION) {
                    Match match = (Match) selectedTable.getDaySchedule().getValueAt(selectedRow, selectedColumn);
                    try {
                        DBAccess db = new DBAccess();
                        db.deleteMatch(match);
                        selectedTable.getDaySchedule().setValueAt(null, selectedRow, selectedColumn);
                    } catch (ClassNotFoundException | IOException | SQLException ex) {
                        Logger.getLogger(AdminMain.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }  
        }
        else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un créneau horaire");
        }
    }
    
    
}
