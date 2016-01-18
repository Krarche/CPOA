package view.player;

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

public class ReservationSchedule extends JPanel {
    
    private JTable daySchedule;
    private final LocalDate date = STARTDATE.plusDays(dayNumber);
    private static final LocalDate STARTDATE = LocalDate.parse("2016-01-14",DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    private static int dayNumber = 0;
    
    public ReservationSchedule() {
        dayNumber++;
        initComponents();        
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        
        daySchedule = new JTable();
        
        daySchedule.setModel(new javax.swing.table.DefaultTableModel(
            new String [][] {
                {"8h", null, null, null, null, null, null},
                {"10h", null, null, null, null, null, null},
                {"12h", null, null, null, null, null, null},
                {"14h", null, null, null, null, null, null},
                {"16h", null, null, null, null, null, null},
                {"18h", null, null, null, null, null, null},
                {"20h", null, null, null, null, null, null}
            },
            new String [] {
                "Heure", "Entrainement 1", "Entrainement 2", "Entrainement 3", "Entrainement 4", "Entrainement 5"
            }
        ));
        
        resizeAndCenterColumn(daySchedule);
        daySchedule.setRowHeight(30);
        daySchedule.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        daySchedule.setCellSelectionEnabled(true);
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
    
    public void resizeAndCenterColumn(JTable table) {
        final TableColumnModel colModel = table.getColumnModel();
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        for (int column = 0; column < table.getColumnCount(); column++) {
            colModel.getColumn(column).setCellRenderer(centerRenderer); // Center the content in column
            int width = 120; // Min width
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer renderer = table.getCellRenderer(row, column);
                Component comp = table.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width +1 , width);
            }
            colModel.getColumn(column).setPreferredWidth(width);
        }
    }

    
}
