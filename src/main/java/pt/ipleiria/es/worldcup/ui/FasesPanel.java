package pt.ipleiria.es.worldcup.ui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;

final class FasesPanel {
    private JPanel rootPanel;
    private BracketPanel bracketPanel;

    FasesPanel(WorldCupTeam selectedTeam) {
        buildUi(selectedTeam);
        fillBracket();
    }

    JPanel getRootPanel() {
        return rootPanel;
    }

    private void fillBracket() {
        String[][] rounds = {
                {"16AVOS", "J01  1A vs 2C", "J02  1B vs 3D/E/F", "J03  1C vs 2A", "J04  1D vs 3B/E/F", "J05  1E vs 2F", "J06  1F vs 2E", "J07  1G vs 3A/B/C", "J08  1H vs 2G"},
                {"OITAVOS", "O1  Venc. J01 vs J02", "O2  Venc. J03 vs J04", "O3  Venc. J05 vs J06", "O4  Venc. J07 vs J08"},
                {"QUARTOS", "QF1  Venc. O1 vs O2", "QF2  Venc. O3 vs O4"},
                {"SEMIFINAIS", "SF1  Venc. QF1", "SF2  Venc. QF2"},
                {"FINAL", "Final  Venc. SF1 vs SF2", "3o Lugar  Derrotados SF"}
        };
        for (int i = 0; i < rounds.length; i++) {
            List<JPanel> matchBoxes = new ArrayList<>();
            bracketPanel.addRound(matchBoxes);
            bracketPanel.add(roundColumn(rounds[i], matchBoxes), UiSupport.constraints(0, i, 1, 1, GridConstraints.FILL_BOTH));
        }
    }

    private JPanel roundColumn(String[] items, List<JPanel> matchBoxes) {
        JPanel column = UiSupport.roundedPanel(AppTheme.CHIP, items.length, 1, new Insets(14, 12, 14, 12), 0, 10, 12);
        column.setPreferredSize(new Dimension(190, 492));
        column.setMinimumSize(new Dimension(178, 492));
        column.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 18)));
        column.add(UiSupport.centeredLabel(items[0], new Color(0xF8D12F), new Font("Inter", Font.BOLD, 15)), UiSupport.constraints(0, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
        for (int i = 1; i < items.length; i++) {
            JPanel box = matchBox(items[i]);
            matchBoxes.add(box);
            column.add(box, UiSupport.constraints(i, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
        }
        return column;
    }

    private JPanel matchBox(String text) {
        JPanel box = UiSupport.roundedPanel(new Color(0x153F7B), 3, 1, new Insets(8, 10, 8, 10), 0, 2, 8);
        box.setPreferredSize(new Dimension(166, 60));
        String[] parts = text.split("  ", 2);
        String code = parts.length > 1 ? parts[0] : "";
        String matchup = parts.length > 1 ? parts[1] : text;
        box.add(UiSupport.label(code, new Color(0xF8D12F), new Font("Inter", Font.BOLD, 10)), UiSupport.fixedHeightConstraints(0, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
        box.add(UiSupport.label(matchup, AppTheme.TEXT, AppTheme.BODY_BOLD_FONT), UiSupport.constraints(1, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
        box.add(UiSupport.label("Vencedor avanca", AppTheme.MUTED, new Font("Inter", Font.PLAIN, 11)), UiSupport.fixedHeightConstraints(2, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
        return box;
    }

    private void buildUi(WorldCupTeam selectedTeam) {
        rootPanel = UiSupport.panel(AppTheme.BACKGROUND, 2, 1, new Insets(24, 28, 34, 28), 0, 16);
        JPanel titlePanel = UiSupport.panel(AppTheme.BACKGROUND, 2, 1, new Insets(0, 0, 0, 0), 0, 3);
        titlePanel.add(UiSupport.label("SELECAO: " + selectedTeam.name().toUpperCase(), AppTheme.MUTED, AppTheme.BODY_BOLD_FONT), UiSupport.constraints(0, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
        titlePanel.add(UiSupport.label("FASES", AppTheme.TEXT, AppTheme.TITLE_FONT), UiSupport.constraints(1, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
        rootPanel.add(titlePanel, UiSupport.fixedHeightConstraints(0, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
        bracketPanel = new BracketPanel();
        rootPanel.add(bracketPanel, UiSupport.constraints(1, 0, 1, 1, GridConstraints.FILL_BOTH));
    }

    private static final class BracketPanel extends JPanel {
        private final List<List<JPanel>> rounds = new ArrayList<>();

        private BracketPanel() {
            setOpaque(true);
            setBackground(AppTheme.BACKGROUND);
            setLayout(new GridLayoutManager(1, 5, new Insets(0, 0, 0, 0), 18, 0));
        }

        private void addRound(List<JPanel> matchBoxes) {
            rounds.add(matchBoxes);
        }

        @Override
        protected void paintChildren(Graphics g) {
            super.paintChildren(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setStroke(new BasicStroke(2.4f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2.setColor(new Color(0xF8D12F));
            for (int round = 0; round < rounds.size() - 1; round++) {
                drawRoundConnectors(g2, rounds.get(round), rounds.get(round + 1));
            }
            g2.dispose();
        }

        private void drawRoundConnectors(Graphics2D g2, List<JPanel> sources, List<JPanel> targets) {
            if (sources.isEmpty() || targets.isEmpty()) {
                return;
            }
            for (int i = 0; i < sources.size(); i++) {
                int targetIndex = Math.min(i / 2, targets.size() - 1);
                drawConnector(g2, sources.get(i), targets.get(targetIndex));
            }
        }

        private void drawConnector(Graphics2D g2, JPanel source, JPanel target) {
            Point start = SwingUtilities.convertPoint(source, source.getWidth(), source.getHeight() / 2, this);
            Point end = SwingUtilities.convertPoint(target, 0, target.getHeight() / 2, this);
            int midX = start.x + Math.max(18, (end.x - start.x) / 2);

            g2.drawLine(start.x, start.y, midX, start.y);
            g2.drawLine(midX, start.y, midX, end.y);
            g2.drawLine(midX, end.y, end.x - 8, end.y);
            g2.fillOval(start.x - 3, start.y - 3, 6, 6);
            drawArrowHead(g2, end.x - 2, end.y);
        }

        private void drawArrowHead(Graphics2D g2, int x, int y) {
            Polygon arrow = new Polygon(
                    new int[]{x, x - 9, x - 9},
                    new int[]{y, y - 5, y + 5},
                    3
            );
            g2.fillPolygon(arrow);
        }
    }
}
