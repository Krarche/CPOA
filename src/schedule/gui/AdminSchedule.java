package schedule.gui;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class AdminSchedule extends JPanel {
    
    private final JFrame parent;
    
    public AdminSchedule(JFrame parent) {
        this.parent = parent;
        this.parent.setResizable(true);
        initComponents();
    }
    
    private void initComponents() {
        setSize(600, 400);
        setBorder(BorderFactory.createTitledBorder(
        BorderFactory.createEtchedBorder(), "Planning"));
    } 
    
}
