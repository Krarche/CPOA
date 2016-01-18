package view.player;

import controller.database.DBAccess;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import utils.DateConvertUtils;
import model.business.Reservation;
import net.miginfocom.swing.MigLayout;

public class PlayerMain extends JPanel {
    
    private final JFrame parent;
    private final String playerId;
    
    private final JPanel schedulePanel = new JPanel(new MigLayout("","","[center]"));
    private final List<ReservationSchedule> scheduleList = new ArrayList();
    private final JButton previousDayButton = new JButton("<<");
    private final JButton nextDayButton = new JButton(">>");
    private ReservationSchedule selectedTable;
    
    private final JPanel buttonPanel = new JPanel(new MigLayout());
    private final JButton addReservationButton = new JButton("Ajouter une réservation");
    private final JButton deleteReservationButton = new JButton("Supprimer la réservation");
    
    public PlayerMain(JFrame parent, String playerId) {
        this.parent = parent;
        this.playerId = playerId;
        this.parent.setResizable(true);
        
        for(int i = 0; i < 9; i++) {
            scheduleList.add(new ReservationSchedule());
        }
        
        selectedTable = scheduleList.get(0);
        
        initComponents();
    }
    
    private void initComponents() {
        setLayout(new MigLayout("align center","[center]",""));
        
        setBorder(BorderFactory.createTitledBorder(
        BorderFactory.createEtchedBorder(), "Reservation"));
        
        schedulePanel.add(previousDayButton);
        schedulePanel.add(selectedTable);
        schedulePanel.add(nextDayButton);
        initSchedules();
        
        buttonPanel.add(addReservationButton);
        buttonPanel.add(deleteReservationButton);
        
        add(schedulePanel,"wrap");
        add(buttonPanel);
        
        previousDayButton.addActionListener((ActionEvent e) -> {
            actionPermorfed(e);
        });
        
        nextDayButton.addActionListener((ActionEvent e) -> {
            actionPermorfed(e);
        });
        
        addReservationButton.addActionListener((ActionEvent e) -> {
            actionPermorfed(e);
        });
        
        deleteReservationButton.addActionListener((ActionEvent e) -> {
            actionPermorfed(e);
        });
        
    }
    
    private void actionPermorfed(ActionEvent e) {
        if(e.getSource() == previousDayButton) {
            if(scheduleList.indexOf(selectedTable) > 0)
                previousDay();
        }
        else if(e.getSource() == nextDayButton) {
            if(scheduleList.indexOf(selectedTable) < 8)
                nextDay();
        }
        else if(e.getSource() == addReservationButton) {
            addReservation();
        }
        else if(e.getSource() == deleteReservationButton) {
            deleteReservation();    
        }
            
    }

    private void deleteReservation() {
        int selectedRow = selectedTable.getDaySchedule().getSelectedRow();
        int selectedColumn = selectedTable.getDaySchedule().getSelectedColumn();
        Reservation reservation = (Reservation) selectedTable.getDaySchedule().getValueAt(selectedRow, selectedColumn);
        if(playerId.equals(reservation.getPlayerid())) {
            if(selectedTable.getDaySchedule().getSelectedRow() != -1) {
                if(!(selectedTable.getDaySchedule().getValueAt(selectedRow, selectedColumn) instanceof Reservation)) {
                    JOptionPane.showMessageDialog(this, "Veuillez sélectionner un créneau horaire avec un match pour le modifier.");
                }
                else {
                    int dialogResult = JOptionPane.showConfirmDialog (this, "Etes-vous sûr de vouloir supprimer ce match ?");
                    if(dialogResult == JOptionPane.YES_OPTION) {
                        try {
                            DBAccess db = new DBAccess();
                            db.deleteReservation(reservation);
                            selectedTable.getDaySchedule().setValueAt(null, selectedRow, selectedColumn);
                        } catch (ClassNotFoundException | IOException | SQLException ex) {
                            Logger.getLogger(PlayerMain.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }  
            }
            else {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un créneau horaire");
            }
        }
        else {
            JOptionPane.showMessageDialog(this, "Vous ne pouvez pas supprimer une réservation qui ne vous appartient pas.");
        }
        
    }

    private void addReservation() {
        if(selectedTable.getDaySchedule().getSelectedRow() != -1) {
            int selectedRow = selectedTable.getDaySchedule().getSelectedRow();
            int selectedColumn = selectedTable.getDaySchedule().getSelectedColumn();
            if(selectedTable.getDaySchedule().getValueAt(selectedRow, selectedColumn) instanceof Reservation) {
                JOptionPane.showMessageDialog(this, "Impossible d'ajouter une réservation.\nIl existe déjà une réservation pour ce créneau.");
            }
            else if(selectedColumn == 0) {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un créneau horaire pour ajouter une réservation.");
            }
            else {
                try {
                    DBAccess db = new DBAccess();
                    int id = Reservation.getMaxId();
                    String playerID = playerId;
                    String court = selectedTable.getDaySchedule().getColumnName(selectedColumn);
                    int hour = Integer.parseInt(selectedTable.getDaySchedule().getValueAt(selectedRow, 0).toString().replace("h", ""));
                    LocalDateTime time = selectedTable.getDate().atTime(hour, 0);
                    Date date = DateConvertUtils.asUtilDate(time);
                    
                    db.addReservation(id,playerID,court,date);
                    Reservation reservation = new Reservation(id, playerID, court, time);
                    Reservation.RESERVATIONS.add(reservation);
                    selectedTable.getDaySchedule().setValueAt(reservation, selectedRow, selectedColumn);
                    
                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    Logger.getLogger(PlayerMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            }   
        }
        else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un créneau horaire");
        }
    }
    
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

    private void initSchedules() {
        for (ReservationSchedule panel : scheduleList) {
            JTable table = panel.getDaySchedule();
            for(Reservation reservation : Reservation.RESERVATIONS) {
                int index = table.getColumn(reservation.getCourt()).getModelIndex();
                if(panel.getDate().equals(reservation.getTime().toLocalDate()) ) {
                    if(reservation.getTime().equals(panel.getDate().atTime(8, 0))) 
                        table.setValueAt(reservation, 0, index);
                    else if(reservation.getTime().equals(panel.getDate().atTime(10, 0))) 
                        table.setValueAt(reservation, 1, index);
                    else if(reservation.getTime().equals(panel.getDate().atTime(12, 0))) 
                        table.setValueAt(reservation, 2, index);
                    else if(reservation.getTime().equals(panel.getDate().atTime(14, 0)))
                        table.setValueAt(reservation, 3, index);
                    else if(reservation.getTime().equals(panel.getDate().atTime(16, 0)))
                        table.setValueAt(reservation, 4, index);
                    else if(reservation.getTime().equals(panel.getDate().atTime(18, 0)))
                        table.setValueAt(reservation, 5, index);
                    else if(reservation.getTime().equals(panel.getDate().atTime(20, 0)))
                        table.setValueAt(reservation, 6, index);
                }
            }           
        }
    }
    
}
