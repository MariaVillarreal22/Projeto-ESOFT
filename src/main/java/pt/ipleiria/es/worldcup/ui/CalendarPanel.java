package pt.ipleiria.es.worldcup.ui;

import com.intellij.uiDesigner.core.GridConstraints;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Rectangle;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

final class CalendarPanel {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MMMM d", Locale.ENGLISH);
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");
    private static final Color GROUP_TITLE = new Color(0xF8D12F);

    private JPanel rootPanel;
    private JPanel groupsPanel;
    private JScrollPane scrollPane;

    CalendarPanel() {
        buildUi();
        fillCalendar();
        resetScrollPosition();
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
        JPanel card = UiSupport.roundedPanel(AppTheme.CHIP, 2, 1, new Insets(10, 14, 10, 14), 0, 5, 10);
        card.setPreferredSize(new Dimension(500, 268));
        card.setMinimumSize(new Dimension(468, 268));
        card.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 16)));

        JPanel header = UiSupport.panel(AppTheme.CHIP, 1, 2, new Insets(0, 0, 2, 0), 14, 0);
        header.add(UiSupport.label(group.name().toUpperCase(), GROUP_TITLE, new Font("Inter", Font.BOLD, 15)), UiSupport.constraints(0, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
        header.add(groupFlags(group), UiSupport.constraints(0, 1, 1, 1, GridConstraints.FILL_NONE));
        card.add(header, UiSupport.fixedHeightConstraints(0, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));

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
            JLabel flag = new JLabel(AppIcons.teamFlag(team.code(), 28, 21));
            flag.setToolTipText(names[i]);
            flags.add(flag, UiSupport.constraints(0, i, 1, 1, GridConstraints.FILL_NONE));
        }
        return flags;
    }

    private JPanel matchRow(WorldCupFixtures.CalendarMatch match) {
        JPanel row = UiSupport.panel(AppTheme.CHIP, 2, 1, new Insets(0, 0, 0, 0), 0, 1);
        JPanel top = UiSupport.panel(AppTheme.CHIP, 1, 5, new Insets(0, 0, 0, 0), 5, 0);
        JLabel time = UiSupport.label(DATE_FORMAT.format(match.date()).toUpperCase(Locale.ENGLISH) + "  " + TIME_FORMAT.format(match.time()), GROUP_TITLE, new Font("Inter", Font.BOLD, 9));
        time.setPreferredSize(new Dimension(82, 18));
        top.add(time, UiSupport.constraints(0, 0, 1, 1, GridConstraints.FILL_NONE));
        top.add(teamLabel(match.home()), UiSupport.constraints(0, 1, 1, 1, GridConstraints.FILL_HORIZONTAL));
        JLabel score = UiSupport.centeredLabel(scoreText(match), match.score() == null ? AppTheme.MUTED : GROUP_TITLE, new Font("Inter", Font.BOLD, 10));
        score.setPreferredSize(new Dimension(30, 18));
        top.add(score, UiSupport.constraints(0, 2, 1, 1, GridConstraints.FILL_NONE));
        top.add(teamLabel(match.away()), UiSupport.constraints(0, 3, 1, 1, GridConstraints.FILL_HORIZONTAL));
        WorldCupFixtures.MatchStatus status = WorldCupFixtures.status(match);
        JLabel statusLabel = UiSupport.label(status.label(), status.color(), new Font("Inter", Font.BOLD, 10));
        statusLabel.setPreferredSize(new Dimension(92, 18));
        top.add(statusLabel, UiSupport.constraints(0, 4, 1, 1, GridConstraints.FILL_NONE));
        row.add(top, UiSupport.constraints(0, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
        JLabel venue = UiSupport.label(match.venue() + ", " + match.city(), AppTheme.MUTED, new Font("Inter", Font.PLAIN, 10));
        venue.setBorder(BorderFactory.createEmptyBorder(0, 86, 0, 0));
        row.add(venue, UiSupport.constraints(1, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
        return row;
    }

    private JLabel teamLabel(String name) {
        WorldCupTeam team = WorldCupFixtures.teamFor(name);
        JLabel label = UiSupport.label(name, AppTheme.TEXT, new Font("Inter", Font.BOLD, 11));
        label.setIcon(AppIcons.teamFlag(team.code(), 22, 16));
        label.setIconTextGap(5);
        label.setPreferredSize(new Dimension(102, 18));
        return label;
    }

    private String scoreText(WorldCupFixtures.CalendarMatch match) {
        if (match.score() == null) {
            return "VS";
        }
        return match.score().homeGoals() + "-" + match.score().awayGoals();
    }

    private void buildUi() {
        rootPanel = new JPanel(new BorderLayout(0, 10));
        rootPanel.setOpaque(true);
        rootPanel.setBackground(AppTheme.BACKGROUND);
        rootPanel.setBorder(BorderFactory.createEmptyBorder(12, 22, 20, 22));
        rootPanel.add(UiSupport.label("CALENDARIO", AppTheme.TEXT, AppTheme.TITLE_FONT), BorderLayout.NORTH);
        groupsPanel = UiSupport.panel(AppTheme.BACKGROUND, 6, 2, new Insets(0, 0, 0, 0), 10, 10);
        scrollPane = new JScrollPane(groupsPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(22);
        scrollPane.getViewport().setBackground(AppTheme.BACKGROUND);
        rootPanel.add(scrollPane, BorderLayout.CENTER);
    }

    private void resetScrollPosition() {
        SwingUtilities.invokeLater(() -> {
            scrollPane.getVerticalScrollBar().setValue(0);
            scrollPane.getHorizontalScrollBar().setValue(0);
            groupsPanel.scrollRectToVisible(new Rectangle(0, 0, 1, 1));
        });
    }
}
