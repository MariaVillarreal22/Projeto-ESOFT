package pt.ipleiria.es.worldcup.ui;

import javax.swing.JFrame;
import java.awt.Dimension;

public class MainFrame extends JFrame {
    public MainFrame() {
        super("FIFA World Cup Manager");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1180, 720));
        setSize(1366, 768);
        setLocationRelativeTo(null);
        setContentPane(new MainScreen());
    }
}
