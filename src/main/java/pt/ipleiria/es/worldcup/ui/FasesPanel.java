package pt.ipleiria.es.worldcup.ui;

import com.intellij.uiDesigner.core.GridConstraints;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;

final class FasesPanel {
    private JPanel rootPanel;
    private JPanel bracketPanel;

    FasesPanel(WorldCupTeam selectedTeam) {
        buildUi(selectedTeam);
        fillBracket();
    }

    JPanel getRootPanel() {
        return rootPanel;
    }

    private void fillBracket() {
        String[][] rounds = {
                {"OITAVOS", "1A vs 2B", "1C vs 2D", "1E vs 2F", "1G vs 2H"},
                {"QUARTOS", "Venc. 01 vs Venc. 02", "Venc. 03 vs Venc. 04", "Venc. 05 vs Venc. 06"},
                {"SEMIFINAIS", "Venc. QF1 vs Venc. QF2", "Venc. QF3 vs Venc. QF4"},
                {"FINAL", "Final", "3o Lugar"}
        };
        for (int i = 0; i < rounds.length; i++) {
            bracketPanel.add(roundColumn(rounds[i]), UiSupport.constraints(0, i, 1, 1, GridConstraints.FILL_BOTH));
        }
    }

    private JPanel roundColumn(String[] items) {
        JPanel column = UiSupport.roundedPanel(AppTheme.CHIP, items.length, 1, new Insets(16, 14, 16, 14), 0, 14, 12);
        column.setPreferredSize(new Dimension(220, 480));
        column.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 18)));
        column.add(UiSupport.centeredLabel(items[0], new Color(0xF8D12F), new Font("Inter", Font.BOLD, 15)), UiSupport.constraints(0, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
        for (int i = 1; i < items.length; i++) {
            column.add(matchBox(items[i]), UiSupport.constraints(i, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
        }
        return column;
    }

    private JPanel matchBox(String text) {
        JPanel box = UiSupport.roundedPanel(new Color(0x153F7B), 2, 1, new Insets(10, 12, 10, 12), 0, 4, 8);
        box.setPreferredSize(new Dimension(180, 76));
        box.add(UiSupport.label(text, AppTheme.TEXT, AppTheme.BODY_BOLD_FONT), UiSupport.constraints(0, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
        box.add(UiSupport.label("Por definir", AppTheme.MUTED, AppTheme.BODY_FONT), UiSupport.constraints(1, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
        return box;
    }

    private void buildUi(WorldCupTeam selectedTeam) {
        rootPanel = UiSupport.panel(AppTheme.BACKGROUND, 2, 1, new Insets(18, 32, 42, 32), 0, 18);
        JPanel titlePanel = UiSupport.panel(AppTheme.BACKGROUND, 2, 1, new Insets(0, 0, 0, 0), 0, 3);
        titlePanel.add(UiSupport.label("SELECAO: " + selectedTeam.name().toUpperCase(), AppTheme.MUTED, AppTheme.BODY_BOLD_FONT), UiSupport.constraints(0, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
        titlePanel.add(UiSupport.label("FASES", AppTheme.TEXT, AppTheme.TITLE_FONT), UiSupport.constraints(1, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
        rootPanel.add(titlePanel, UiSupport.constraints(0, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
        bracketPanel = UiSupport.panel(AppTheme.BACKGROUND, 1, 4, new Insets(0, 0, 0, 0), 18, 0);
        rootPanel.add(bracketPanel, UiSupport.constraints(1, 0, 1, 1, GridConstraints.FILL_BOTH));
    }
}
