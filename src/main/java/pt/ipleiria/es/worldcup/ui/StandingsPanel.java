package pt.ipleiria.es.worldcup.ui;

import com.intellij.uiDesigner.core.GridConstraints;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;

final class StandingsPanel {
    private static final Color GROUP_TITLE = new Color(0xF8D12F);

    private JPanel rootPanel;
    private JPanel groupsPanel;
    private final WorldCupTeam selectedTeam;

    StandingsPanel(WorldCupTeam selectedTeam) {
        this.selectedTeam = selectedTeam;
        buildUi();
        fillStandings();
    }

    JPanel getRootPanel() {
        return rootPanel;
    }

    private void fillStandings() {
        WorldCupFixtures.CalendarGroup[] groups = WorldCupFixtures.groups();
        for (int i = 0; i < groups.length; i++) {
            groupsPanel.add(classificacaoCard(groups[i]), UiSupport.constraints(i / 2, i % 2, 1, 1, GridConstraints.FILL_BOTH));
        }
    }

    private JPanel classificacaoCard(WorldCupFixtures.CalendarGroup group) {
        WorldCupFixtures.StandingRow[] rows = WorldCupFixtures.standingsFor(group);
        JPanel card = UiSupport.roundedPanel(AppTheme.CHIP, 2, 1, new Insets(12, 18, 14, 18), 0, 8, 10);
        card.setPreferredSize(new Dimension(570, 236));
        card.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 16)));

        JPanel header = UiSupport.panel(AppTheme.CHIP, 1, 2, new Insets(0, 0, 2, 0), 14, 0);
        header.add(UiSupport.label(group.name().toUpperCase(), GROUP_TITLE, new Font("Inter", Font.BOLD, 16)), UiSupport.constraints(0, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
        header.add(groupFlags(group), UiSupport.constraints(0, 1, 1, 1, GridConstraints.FILL_NONE));
        card.add(header, UiSupport.constraints(0, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
        card.add(classificacaoTable(rows), UiSupport.constraints(1, 0, 1, 1, GridConstraints.FILL_BOTH));
        return card;
    }

    private JPanel groupFlags(WorldCupFixtures.CalendarGroup group) {
        String[] names = group.teamNames();
        JPanel flags = UiSupport.panel(AppTheme.CHIP, 1, names.length, new Insets(0, 0, 0, 0), 4, 0);
        for (int i = 0; i < names.length; i++) {
            WorldCupTeam team = WorldCupFixtures.teamFor(names[i]);
            flags.add(new JLabel(AppIcons.teamFlag(team.code(), 30, 22)), UiSupport.constraints(0, i, 1, 1, GridConstraints.FILL_NONE));
        }
        return flags;
    }

    private JPanel classificacaoTable(WorldCupFixtures.StandingRow[] rows) {
        JPanel table = UiSupport.panel(AppTheme.CHIP, rows.length + 1, 8, new Insets(0, 0, 0, 0), 0, 5);
        addRow(table, 0, new JLabel[]{
                header("#", 34), header("EQUIPA", 210), header("J", 42), header("V", 42),
                header("E", 42), header("D", 42), header("DG", 50), header("PTS", 54)
        });
        for (int i = 0; i < rows.length; i++) {
            WorldCupFixtures.StandingRow standing = rows[i];
            Color bg = rowColor(standing, i + 1);
            addRow(table, i + 1, new JLabel[]{
                    value(String.valueOf(i + 1), AppTheme.MUTED, 34, bg),
                    teamLabel(standing.team(), bg),
                    value(String.valueOf(standing.played()), AppTheme.TEXT, 42, bg),
                    value(String.valueOf(standing.wins()), AppTheme.TEXT, 42, bg),
                    value(String.valueOf(standing.draws()), AppTheme.TEXT, 42, bg),
                    value(String.valueOf(standing.losses()), AppTheme.TEXT, 42, bg),
                    value(signed(standing.goalDifference()), AppTheme.TEXT, 50, bg),
                    value(String.valueOf(standing.points()), GROUP_TITLE, 54, bg)
            });
        }
        return table;
    }

    private void addRow(JPanel table, int row, JLabel[] labels) {
        for (int col = 0; col < labels.length; col++) {
            table.add(labels[col], UiSupport.constraints(row, col, 1, 1, GridConstraints.FILL_BOTH));
        }
    }

    private JLabel header(String text, int width) {
        JLabel label = UiSupport.centeredLabel(text, AppTheme.MUTED, new Font("Inter", Font.BOLD, 11));
        label.setPreferredSize(new Dimension(width, 18));
        return label;
    }

    private JLabel value(String text, Color color, int width, Color background) {
        JLabel label = UiSupport.centeredLabel(text, color, new Font("Inter", Font.BOLD, 12));
        label.setOpaque(true);
        label.setBackground(background);
        label.setPreferredSize(new Dimension(width, 30));
        return label;
    }

    private JLabel teamLabel(WorldCupTeam team, Color background) {
        JLabel label = UiSupport.label(team.name(), AppTheme.TEXT, new Font("Inter", Font.BOLD, 12));
        label.setIcon(AppIcons.teamFlag(team.code(), 24, 18));
        label.setIconTextGap(7);
        label.setOpaque(true);
        label.setBackground(background);
        label.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
        label.setPreferredSize(new Dimension(210, 30));
        return label;
    }

    private Color rowColor(WorldCupFixtures.StandingRow row, int position) {
        if (row.team().name().equals(selectedTeam.name())) {
            return new Color(0x1A5BAA);
        }
        return position <= 2 ? new Color(0x153F7B) : AppTheme.CHIP;
    }

    private String signed(int value) {
        return value > 0 ? "+" + value : String.valueOf(value);
    }

    private void buildUi() {
        rootPanel = UiSupport.panel(AppTheme.BACKGROUND, 2, 1, new Insets(18, 32, 42, 32), 0, 18);
        JPanel title = UiSupport.panel(AppTheme.BACKGROUND, 2, 1, new Insets(0, 0, 0, 0), 0, 3);
        title.add(UiSupport.label("CLASSIFICACOES", AppTheme.TEXT, AppTheme.TITLE_FONT), UiSupport.constraints(0, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
        title.add(UiSupport.label("GRUPOS DO MUNDIAL FIFA 2026", AppTheme.MUTED, AppTheme.BODY_BOLD_FONT), UiSupport.constraints(1, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
        rootPanel.add(title, UiSupport.constraints(0, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
        groupsPanel = UiSupport.panel(AppTheme.BACKGROUND, 6, 2, new Insets(0, 0, 0, 0), 8, 10);
        JScrollPane scrollPane = new JScrollPane(groupsPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(AppTheme.BACKGROUND);
        rootPanel.add(scrollPane, UiSupport.constraints(1, 0, 1, 1, GridConstraints.FILL_BOTH));
    }
}
