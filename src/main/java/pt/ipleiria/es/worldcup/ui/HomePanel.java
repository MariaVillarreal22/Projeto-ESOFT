package pt.ipleiria.es.worldcup.ui;

import com.intellij.uiDesigner.core.GridConstraints;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;

final class HomePanel {
    private JPanel rootPanel;
    private JPanel titlePanel;
    private JPanel overviewPanel;
    private JPanel modulesPanel;
    private JButton openFasesButton;

    HomePanel(WorldCupTeam selectedTeam, Runnable openFases) {
        buildUi();
        openFasesButton.addActionListener(event -> openFases.run());
        fillOverview(selectedTeam);
        fillModules();
    }

    JPanel getRootPanel() {
        return rootPanel;
    }

    private void fillOverview(WorldCupTeam selectedTeam) {
        String[][] cards = {
                {"Selecao", selectedTeam.code(), selectedTeam.name() + " (" + selectedTeam.confederation() + ") selecionada no topo."},
                {"Equipas", "48", "Selecoes qualificadas para o Mundial FIFA 2026."},
                {"Jogos", "104", "Fase de grupos e eliminatorias."},
                {"Sedes", "16", "Canada, Mexico e Estados Unidos."}
        };
        for (int i = 0; i < cards.length; i++) {
            overviewPanel.add(summaryCard(cards[i][0], cards[i][1], cards[i][2]), UiSupport.constraints(0, i, 1, 1, GridConstraints.FILL_BOTH));
        }
    }

    private void fillModules() {
        Object[][] modules = {
                {AppIcons.module("FA", new Color(0x1D5DDB)), "Fases", "Bracket do torneio"},
                {AppIcons.module("CAL", new Color(0x61D394)), "Calendario", "Datas, estadios e jogos"},
                {AppIcons.module("CL", new Color(0xF8D12F)), "Classificacoes", "Tabela dos grupos"},
                {AppIcons.module("EQ", new Color(0xF5C867)), "Equipas", "48 selecoes"},
                {AppIcons.module("EST", new Color(0x7CA7FF)), "Estadios", "16 sedes"},
                {AppIcons.module("TKT", new Color(0xEF6F6C)), "Bilhetes", "Compra e reservas"}
        };
        for (int i = 0; i < modules.length; i++) {
            JPanel card = moduleCard((javax.swing.Icon) modules[i][0], (String) modules[i][1], (String) modules[i][2]);
            modulesPanel.add(card, UiSupport.constraints(i / 3, i % 3, 1, 1, GridConstraints.FILL_BOTH));
        }
    }

    private JPanel summaryCard(String title, String value, String description) {
        JPanel card = UiSupport.roundedPanel(AppTheme.PANEL_SOFT, 3, 1, new Insets(14, 16, 14, 16), 0, 6, 10);
        card.setPreferredSize(new Dimension(230, 118));
        card.setMinimumSize(new Dimension(210, 118));
        card.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 20)));
        card.add(UiSupport.label(title.toUpperCase(), AppTheme.MUTED, AppTheme.BODY_BOLD_FONT), UiSupport.constraints(0, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
        card.add(UiSupport.label(value, AppTheme.TEXT, new Font("Inter", Font.BOLD, 22)), UiSupport.constraints(1, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
        card.add(UiSupport.label("<html><body style='width:190px'>" + description + "</body></html>", AppTheme.MUTED, AppTheme.BODY_FONT), UiSupport.constraints(2, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
        return card;
    }

    private JPanel moduleCard(javax.swing.Icon icon, String title, String description) {
        JPanel card = UiSupport.roundedPanel(AppTheme.PANEL_SOFT, 1, 2, new Insets(16, 16, 16, 16), 14, 0, 10);
        card.setPreferredSize(new Dimension(300, 108));
        card.setMinimumSize(new Dimension(260, 108));
        card.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 20)));
        card.add(new JLabel(icon), UiSupport.constraints(0, 0, 1, 1, GridConstraints.FILL_NONE));
        JPanel text = UiSupport.panel(AppTheme.PANEL_SOFT, 2, 1, new Insets(0, 0, 0, 0), 0, 6);
        text.add(UiSupport.label(title, AppTheme.TEXT, new Font("Inter", Font.BOLD, 16)), UiSupport.constraints(0, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
        text.add(UiSupport.label(description, AppTheme.MUTED, AppTheme.BODY_FONT), UiSupport.constraints(1, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
        card.add(text, UiSupport.constraints(0, 1, 1, 1, GridConstraints.FILL_BOTH));
        return card;
    }

    private void buildUi() {
        rootPanel = UiSupport.panel(AppTheme.BACKGROUND, 3, 1, new Insets(24, 28, 34, 28), 0, 18);
        titlePanel = UiSupport.panel(AppTheme.BACKGROUND, 1, 2, new Insets(0, 0, 0, 0), 16, 0);
        JPanel titleText = UiSupport.panel(AppTheme.BACKGROUND, 2, 1, new Insets(0, 0, 0, 0), 0, 3);
        titleText.add(UiSupport.label("CAMPEONATO DO MUNDO FIFA 2026", AppTheme.MUTED, AppTheme.BODY_BOLD_FONT), UiSupport.constraints(0, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
        titleText.add(UiSupport.label("PAINEL PRINCIPAL", AppTheme.TEXT, AppTheme.TITLE_FONT), UiSupport.constraints(1, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
        titlePanel.add(titleText, UiSupport.constraints(0, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
        openFasesButton = UiSupport.button("Abrir fases", AppTheme.ACCENT, new Color(0x08233C));
        openFasesButton.setOpaque(true);
        openFasesButton.setPreferredSize(new Dimension(132, 38));
        titlePanel.add(openFasesButton, UiSupport.constraints(0, 1, 1, 1, GridConstraints.FILL_NONE));
        rootPanel.add(titlePanel, UiSupport.constraints(0, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
        overviewPanel = UiSupport.panel(AppTheme.BACKGROUND, 1, 4, new Insets(0, 0, 0, 0), 14, 0);
        rootPanel.add(overviewPanel, UiSupport.constraints(1, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
        modulesPanel = UiSupport.panel(AppTheme.BACKGROUND, 2, 3, new Insets(0, 0, 0, 0), 16, 16);
        rootPanel.add(modulesPanel, UiSupport.constraints(2, 0, 1, 1, GridConstraints.FILL_BOTH));
    }
}
