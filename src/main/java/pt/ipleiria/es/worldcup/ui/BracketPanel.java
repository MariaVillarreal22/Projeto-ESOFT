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
    private static final int CHIP_WIDTH = 54;
    private static final int CHIP_HEIGHT = 20;
    private static final Color CONNECTOR = new Color(210, 225, 245, 155);
    private static final String[] HEADERS = {
            "Round of 16", "Round of 8", "Quarter finals", "Semifinal", "Final",
            "Semifinal", "Quarter finals", "Round of 8", "Round of 16"
    };

    BracketPanel() {
        setOpaque(false);
        setPreferredSize(new Dimension(1402, 783));
        setMinimumSize(new Dimension(1040, 560));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();
        g2.setColor(AppTheme.PANEL);
        g2.fill(new RoundRectangle2D.Double(0, 0, width - 1, height - 1, 18, 18));
        g2.setColor(new Color(255, 255, 255, 24));
        g2.draw(new RoundRectangle2D.Double(0.5, 0.5, width - 2, height - 2, 18, 18));

        int left = Math.max(58, width / 22);
        int right = width - left;
        int headerY = 54;
        int bracketTop = 96;
        int bracketBottom = height - 76;
        int columns = HEADERS.length;
        int step = (right - left) / (columns - 1);

        g2.setColor(AppTheme.MUTED);
        g2.setFont(AppTheme.BODY_FONT);
        for (int i = 0; i < columns; i++) {
            String header = HEADERS[i];
            int x = left + i * step;
            int textWidth = g2.getFontMetrics().stringWidth(header);
            g2.drawString(header, x - textWidth / 2, headerY);
        }

        Stroke lineStroke = new BasicStroke(1.2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        g2.setStroke(lineStroke);

        int[][] rounds = buildRoundRows(bracketTop, bracketBottom);
        drawSide(g2, left, step, rounds, true);
        drawSide(g2, right, step, rounds, false);

        int finalY = height / 2;
        int leftFinalX = width / 2 - CHIP_WIDTH - 8;
        int rightFinalX = width / 2 + 8;
        g2.setColor(CONNECTOR);
        connectToFinal(g2, left, step, rounds[3], leftFinalX, finalY, true);
        connectToFinal(g2, right, step, rounds[3], rightFinalX, finalY, false);
        drawChip(g2, leftFinalX, finalY - CHIP_HEIGHT / 2);
        drawChip(g2, rightFinalX, finalY - CHIP_HEIGHT / 2);
        g2.setColor(CONNECTOR);
        g2.drawLine(leftFinalX + CHIP_WIDTH, finalY, rightFinalX, finalY);

        g2.setColor(AppTheme.MUTED);
        String third = "Third place";
        int thirdY = Math.min(height - 118, finalY + 150);
        g2.drawString(third, width / 2 - g2.getFontMetrics().stringWidth(third) / 2, thirdY);
        drawChip(g2, leftFinalX, thirdY + 18);
        drawChip(g2, rightFinalX, thirdY + 18);
        g2.setColor(CONNECTOR);
        g2.drawLine(leftFinalX + CHIP_WIDTH, thirdY + 18 + CHIP_HEIGHT / 2, rightFinalX, thirdY + 18 + CHIP_HEIGHT / 2);
        g2.dispose();
    }

    private int[][] buildRoundRows(int top, int bottom) {
        int[][] rounds = new int[4][];
        rounds[0] = new int[16];
        int gap = (bottom - top) / (rounds[0].length - 1);
        for (int i = 0; i < rounds[0].length; i++) {
            rounds[0][i] = top + i * gap;
        }

        for (int round = 1; round < rounds.length; round++) {
            rounds[round] = new int[rounds[round - 1].length / 2];
            for (int i = 0; i < rounds[round].length; i++) {
                rounds[round][i] = (rounds[round - 1][i * 2] + rounds[round - 1][i * 2 + 1]) / 2;
            }
        }
        return rounds;
    }

    private void drawSide(Graphics2D g2, int edgeX, int step, int[][] rounds, boolean leftSide) {
        int direction = leftSide ? 1 : -1;

        g2.setColor(CONNECTOR);
        for (int col = 0; col < rounds.length - 1; col++) {
            drawConnectors(g2, edgeX, step, rounds, col, leftSide);
        }

        for (int col = 0; col < rounds.length; col++) {
            int x = edgeX + direction * step * col;
            int chipX = leftSide ? x : x - CHIP_WIDTH;
            for (int y : rounds[col]) {
                drawChip(g2, chipX, y - CHIP_HEIGHT / 2);
            }
        }
    }

    private void drawConnectors(Graphics2D g2, int edgeX, int step, int[][] rounds, int col, boolean leftSide) {
        int direction = leftSide ? 1 : -1;
        int currentX = edgeX + direction * step * col;
        int nextX = edgeX + direction * step * (col + 1);
        int currentChipX = leftSide ? currentX : currentX - CHIP_WIDTH;
        int nextChipX = leftSide ? nextX : nextX - CHIP_WIDTH;
        int startX = leftSide ? currentChipX + CHIP_WIDTH : currentChipX;
        int endX = leftSide ? nextChipX : nextChipX + CHIP_WIDTH;
        int elbowX = startX + direction * 18;

        for (int i = 0; i < rounds[col + 1].length; i++) {
            int topY = rounds[col][i * 2];
            int bottomY = rounds[col][i * 2 + 1];
            int nextY = rounds[col + 1][i];
            g2.drawLine(startX, topY, elbowX, topY);
            g2.drawLine(startX, bottomY, elbowX, bottomY);
            g2.drawLine(elbowX, topY, elbowX, bottomY);
            g2.drawLine(elbowX, nextY, endX, nextY);
        }
    }

    private void connectToFinal(Graphics2D g2, int edgeX, int step, int[] semifinalRows, int finalX, int finalY, boolean leftSide) {
        int direction = leftSide ? 1 : -1;
        int currentX = edgeX + direction * step * 3;
        int currentChipX = leftSide ? currentX : currentX - CHIP_WIDTH;
        int startX = leftSide ? currentChipX + CHIP_WIDTH : currentChipX;
        int endX = leftSide ? finalX : finalX + CHIP_WIDTH;
        int elbowX = startX + direction * 24;

        for (int rowY : semifinalRows) {
            g2.drawLine(startX, rowY, elbowX, rowY);
        }
        g2.drawLine(elbowX, semifinalRows[0], elbowX, semifinalRows[1]);
        g2.drawLine(elbowX, finalY, endX, finalY);
    }

    private void drawChip(Graphics2D g2, int x, int y) {
        g2.setColor(AppTheme.CHIP);
        g2.fill(new RoundRectangle2D.Double(x, y, CHIP_WIDTH, CHIP_HEIGHT, 8, 8));
    }
}
