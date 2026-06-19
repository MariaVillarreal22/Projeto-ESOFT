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
import java.time.format.DateTimeFormatter;
import java.util.Locale;

final class CalendarPanel {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MMMM d", Locale.ENGLISH);
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");
    private static final Color GROUP_TITLE = new Color(0xF8D12F);

    private JPanel rootPanel;
    private JPanel groupsPanel;

    CalendarPanel() {
        buildUi();
        fillCalendar();
    }

    JPanel getRootPanel() {
        return rootPanel;
    }

    private void fillCalendar() {
        WorldCupFixtures.CalendarGroup[] groups = WorldCupFixtures.groups();
        for (int i = 0; i < groups.length; i++) {
            groupsPanel.add(calendarCard(groups[i]), UiSupport.constraints(i / 2, i % 2, 1, 1, GridConstraints.FILL_BOTH));
        }
    }

    private JPanel calendarCard(WorldCupFixtures.CalendarGroup group) {
        JPanel card = UiSupport.roundedPanel(AppTheme.CHIP, 2, 1, new Insets(12, 18, 14, 18), 0, 8, 10);
        card.setPreferredSize(new Dimension(570, 326));
        card.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 16)));

        JPanel header = UiSupport.panel(AppTheme.CHIP, 1, 2, new Insets(0, 0, 2, 0), 14, 0);
        header.add(UiSupport.label(group.name().toUpperCase(), GROUP_TITLE, new Font("Inter", Font.BOLD, 16)), UiSupport.constraints(0, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
        header.add(groupFlags(group), UiSupport.constraints(0, 1, 1, 1, GridConstraints.FILL_NONE));
        card.add(header, UiSupport.constraints(0, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));

        JPanel matches = UiSupport.panel(AppTheme.CHIP, group.matches().length, 1, new Insets(0, 0, 0, 0), 0, 4);
        for (int i = 0; i < group.matches().length; i++) {
            matches.add(matchRow(group.matches()[i]), UiSupport.constraints(i, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
        }
        card.add(matches, UiSupport.constraints(1, 0, 1, 1, GridConstraints.FILL_BOTH));
        return card;
    }

    private JPanel groupFlags(WorldCupFixtures.CalendarGroup group) {
        String[] names = group.teamNames();
        JPanel flags = UiSupport.panel(AppTheme.CHIP, 1, names.length, new Insets(0, 0, 0, 0), 4, 0);
        for (int i = 0; i < names.length; i++) {
            WorldCupTeam team = WorldCupFixtures.teamFor(names[i]);
            JLabel flag = new JLabel(AppIcons.teamFlag(team.code(), 30, 22));
            flag.setToolTipText(names[i]);
            flags.add(flag, UiSupport.constraints(0, i, 1, 1, GridConstraints.FILL_NONE));
        }
        return flags;
    }

    private JPanel matchRow(WorldCupFixtures.CalendarMatch match) {
        JPanel row = UiSupport.panel(AppTheme.CHIP, 2, 1, new Insets(1, 0, 1, 0), 0, 2);
        JPanel top = UiSupport.panel(AppTheme.CHIP, 1, 5, new Insets(0, 0, 0, 0), 8, 0);
        top.add(UiSupport.label(DATE_FORMAT.format(match.date()).toUpperCase(Locale.ENGLISH) + "  " + TIME_FORMAT.format(match.time()), GROUP_TITLE, new Font("Inter", Font.BOLD, 11)), UiSupport.constraints(0, 0, 1, 1, GridConstraints.FILL_NONE));
        top.add(teamLabel(match.home()), UiSupport.constraints(0, 1, 1, 1, GridConstraints.FILL_HORIZONTAL));
        top.add(UiSupport.centeredLabel(scoreText(match), match.score() == null ? AppTheme.MUTED : GROUP_TITLE, new Font("Inter", Font.BOLD, 11)), UiSupport.constraints(0, 2, 1, 1, GridConstraints.FILL_NONE));
        top.add(teamLabel(match.away()), UiSupport.constraints(0, 3, 1, 1, GridConstraints.FILL_HORIZONTAL));
        WorldCupFixtures.MatchStatus status = WorldCupFixtures.status(match);
        JLabel statusLabel = UiSupport.label(status.label(), status.color(), new Font("Inter", Font.BOLD, 11));
        statusLabel.setPreferredSize(new Dimension(112, 20));
        top.add(statusLabel, UiSupport.constraints(0, 4, 1, 1, GridConstraints.FILL_NONE));
        row.add(top, UiSupport.constraints(0, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
        JLabel venue = UiSupport.label(match.venue() + ", " + match.city(), AppTheme.MUTED, new Font("Inter", Font.PLAIN, 11));
        venue.setBorder(BorderFactory.createEmptyBorder(0, 100, 0, 0));
        row.add(venue, UiSupport.constraints(1, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
        return row;
    }

    private JLabel teamLabel(String name) {
        WorldCupTeam team = WorldCupFixtures.teamFor(name);
        JLabel label = UiSupport.label(name, AppTheme.TEXT, new Font("Inter", Font.BOLD, 12));
        label.setIcon(AppIcons.teamFlag(team.code(), 24, 18));
        label.setIconTextGap(6);
        return label;
    }

    private String scoreText(WorldCupFixtures.CalendarMatch match) {
        if (match.score() == null) {
            return "VS";
        }
        return match.score().homeGoals() + "-" + match.score().awayGoals();
    }

    private void buildUi() {
        rootPanel = UiSupport.panel(AppTheme.BACKGROUND, 2, 1, new Insets(18, 32, 42, 32), 0, 18);
        rootPanel.add(UiSupport.label("CALENDARIO", AppTheme.TEXT, AppTheme.TITLE_FONT), UiSupport.constraints(0, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
        groupsPanel = UiSupport.panel(AppTheme.BACKGROUND, 6, 2, new Insets(0, 0, 0, 0), 8, 10);
        JScrollPane scrollPane = new JScrollPane(groupsPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(AppTheme.BACKGROUND);
        rootPanel.add(scrollPane, UiSupport.constraints(1, 0, 1, 1, GridConstraints.FILL_BOTH));
    }
}
