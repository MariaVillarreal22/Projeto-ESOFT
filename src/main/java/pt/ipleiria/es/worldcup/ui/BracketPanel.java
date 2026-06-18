package pt.ipleiria.es.worldcup.ui;

import javax.swing.JPanel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.RoundRectangle2D;

final class BracketPanel extends JPanel {
    private static final String[] HEADERS = {
            "Round of 16", "Round of 8", "Quarter finals", "Semifinal", "Final",
            "Semifinal", "Quarter finals", "Round of 8", "Round of 16"
    };

    BracketPanel() {
        setOpaque(true);
        setBackground(AppTheme.PANEL);
        setPreferredSize(new Dimension(980, 520));
        setMinimumSize(new Dimension(860, 460));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();
        int left = 42;
        int right = width - 42;
        int top = 42;
        int bottom = height - 42;
        int columns = HEADERS.length;
        int step = (right - left) / (columns - 1);

        g2.setColor(AppTheme.MUTED);
        g2.setFont(AppTheme.BODY_FONT);
        for (int i = 0; i < columns; i++) {
            String header = HEADERS[i];
            int x = left + i * step;
            int textWidth = g2.getFontMetrics().stringWidth(header);
            g2.drawString(header, x - textWidth / 2, top - 12);
        }

        Stroke lineStroke = new BasicStroke(1.2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        g2.setStroke(lineStroke);
        g2.setColor(new Color(210, 225, 245, 155));

        drawSide(g2, left, top + 22, bottom - 6, step, true);
        drawSide(g2, right, top + 22, bottom - 6, step, false);

        drawChip(g2, width / 2 - 39, height / 2 - 9);
        drawChip(g2, width / 2 + 16, height / 2 - 9);
        g2.drawLine(width / 2 - 5, height / 2, width / 2 + 16, height / 2);

        g2.setColor(AppTheme.MUTED);
        String third = "Third place";
        g2.drawString(third, width / 2 - g2.getFontMetrics().stringWidth(third) / 2, height / 2 + 54);
        drawChip(g2, width / 2 - 39, height / 2 + 70);
        drawChip(g2, width / 2 + 16, height / 2 + 70);
        g2.dispose();
    }

    private void drawSide(Graphics2D g2, int edgeX, int top, int bottom, int step, boolean leftSide) {
        int direction = leftSide ? 1 : -1;
        int chipWidth = 54;
        int chipHeight = 20;
        int[] counts = {16, 8, 4, 2};

        for (int col = 0; col < counts.length; col++) {
            int count = counts[col];
            int x = edgeX + direction * step * col;
            int available = bottom - top;
            int gap = count == 1 ? available : available / (count - 1);

            for (int i = 0; i < count; i++) {
                int y = top + i * gap - chipHeight / 2;
                int chipX = leftSide ? x : x - chipWidth;
                drawChip(g2, chipX, y);

                if (col < counts.length - 1 && i % 2 == 0) {
                    int pairedY = top + (i + 1) * gap;
                    int nextX = x + direction * step;
                    int midY = (top + i * gap + pairedY) / 2;
                    int startX = leftSide ? chipX + chipWidth : chipX;
                    int bracketX = startX + direction * 16;
                    int nextStartX = leftSide ? nextX : nextX - chipWidth;
                    int endX = leftSide ? nextStartX : nextStartX + chipWidth;

                    g2.drawLine(startX, top + i * gap, bracketX, top + i * gap);
                    g2.drawLine(startX, pairedY, bracketX, pairedY);
                    g2.drawLine(bracketX, top + i * gap, bracketX, pairedY);
                    g2.drawLine(bracketX, midY, endX, midY);
                }
            }
        }
    }

    private void drawChip(Graphics2D g2, int x, int y) {
        g2.setColor(AppTheme.CHIP);
        g2.fill(new RoundRectangle2D.Double(x, y, 54, 20, 8, 8));
    }
}
