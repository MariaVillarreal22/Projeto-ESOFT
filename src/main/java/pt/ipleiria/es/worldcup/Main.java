package pt.ipleiria.es.worldcup;

import pt.ipleiria.es.worldcup.ui.MainFrame;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
                // The default Swing look and feel is enough if the system one is unavailable.
            }

            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
