package view.admin;

import java.awt.BorderLayout;
import java.awt.Component;
import java.time.*;
import java.time.format.DateTimeFormatter;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

/**
 * Panel contenant une JTable, une date de début de tournoi + la date 
 * correspondant à chaque JTable
 * @author Kevin GUTIERREZ
 * @author Pierre DAUTREY
 * @author Corentin BECT
 */
public class SchedulePanel extends JPanel {
    
    private JTable daySchedule;
    private final LocalDate date = STARTDATE.plusDays(dayNumber);
    private static final LocalDate STARTDATE = LocalDate.parse("2016-01-14",DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    private static int dayNumber = 0;
    
    public SchedulePanel() {
        dayNumber++;
        initComponents();        
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        
        daySchedule = new JTable();
        
        
        daySchedule.setModel(new javax.swing.table.DefaultTableModel(
            new String [][] { // Initialise les lignes de la table
                {"8h", null, null, null, null, null, null},
                {"11h", null, null, null, null, null, null},
                {"15h", null, null, null, null, null, null},
                {"18h", null, null, null, null, null, null},
                {"21h", null, null, null, null, null, null}
            },
            new String [] { // Initialise les titres des colonnes
                "Heure", "Central", "Annexe 1", "Annexe 2", "Annexe 3", "Entrainement"
            }
        ));
        
        resizeAndCenterColumn(daySchedule); // Redimensionnement et centrage des colonnes
        daySchedule.setRowHeight(50); // Hauteur des lignes
        daySchedule.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        daySchedule.setCellSelectionEnabled(true); // Sélection cellule par cellule
        daySchedule.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        add(getDaySchedule().getTableHeader(),BorderLayout.NORTH);
        add(getDaySchedule(),BorderLayout.CENTER);
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
                                                   "Jour " + dayNumber + " : " + getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                                                   TitledBorder.CENTER, TitledBorder.ABOVE_TOP));
    }

    public JTable getDaySchedule() {
        return daySchedule;
    }
    
    public LocalDate getDate() {
        return date;
    }
    
    /**
     * Redimensionne et centre la table passé en paramètre
     * @param table table à redimensionner et centrer
     */
    public void resizeAndCenterColumn(JTable table) {
        final TableColumnModel colModel = table.getColumnModel();
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        for (int column = 0; column < table.getColumnCount(); column++) {
            colModel.getColumn(column).setCellRenderer(centerRenderer); // Centre le contenu des colonnes
            int width = 120; // Largeur minimum
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer renderer = table.getCellRenderer(row, column);
                Component comp = table.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width +1 , width);
            }
            colModel.getColumn(column).setPreferredWidth(width);
        }
    }

    
}