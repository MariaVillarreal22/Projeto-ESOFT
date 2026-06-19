package pt.ipleiria.es.worldcup.ui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;

final class UiSupport {
    private UiSupport() {
    }

    static JPanel panel(Color background, int rows, int cols, Insets insets, int hGap, int vGap) {
        JPanel panel = new JPanel();
        panel.setOpaque(true);
        panel.setBackground(background);
        panel.setLayout(new GridLayoutManager(rows, cols, insets, hGap, vGap));
        return panel;
    }

    static JPanel roundedPanel(Color background, int rows, int cols, Insets insets, int hGap, int vGap, int radius) {
        JPanel panel = new RoundedPanel(background, radius);
        panel.setLayout(new GridLayoutManager(rows, cols, insets, hGap, vGap));
        return panel;
    }

    static JLabel label(String text, Color color, Font font) {
        JLabel label = new JLabel(text);
        label.setForeground(color);
        label.setFont(font);
        label.setVerticalAlignment(SwingConstants.CENTER);
        return label;
    }

    static JLabel centeredLabel(String text, Color color, Font font) {
        JLabel label = label(text, color, font);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }

    static JButton button(String text, Color background, Color foreground) {
        JButton button = new JButton(text);
        button.setUI(new BasicButtonUI());
        button.setBackground(background);
        button.setForeground(foreground);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        return button;
    }

    static GridConstraints constraints(int row, int col, int rowSpan, int colSpan, int fill) {
        return new GridConstraints(
                row,
                col,
                rowSpan,
                colSpan,
                GridConstraints.ANCHOR_CENTER,
                fill,
                GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW | GridConstraints.SIZEPOLICY_WANT_GROW,
                GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW | GridConstraints.SIZEPOLICY_WANT_GROW,
                null,
                null,
                null,
                0,
                false
        );
    }

    static void paintRounded(Graphics g, Color background, int radius, int width, int height) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(background);
        g2.fillRoundRect(0, 0, width, height, radius, radius);
        g2.dispose();
    }

    private static final class RoundedPanel extends JPanel {
        private final Color background;
        private final int radius;

        private RoundedPanel(Color background, int radius) {
            this.background = background;
            this.radius = radius;
            setOpaque(false);
            setBackground(background);
        }

        @Override
        protected void paintComponent(Graphics g) {
            paintRounded(g, background, radius, getWidth(), getHeight());
            super.paintComponent(g);
        }
    }
}
