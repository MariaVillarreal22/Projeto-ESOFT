package pt.ipleiria.es.worldcup.ui;

import com.intellij.uiDesigner.core.GridConstraints;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;

public class MainScreen extends JPanel {
    private JPanel rootPanel;
    private JPanel sidebarPanel;
    private JPanel mainPanel;
    private JPanel headerPanel;
    private JPanel contentPanel;
    private JButton homeButton;
    private JButton fasesButton;
    private JButton calendarButton;
    private JButton standingsButton;
    private JButton statisticsButton;
    private JButton menuButton;
    private JTextField searchField;
    private JComboBox<WorldCupTeam> teamComboBox;
    private JLabel dateLabel;

    private View currentView = View.HOME;
    private WorldCupTeam selectedTeam = WorldCupData.defaultTeam();
    private final Timer refreshTimer;

    public MainScreen() {
        buildUi();
        setLayout(new BorderLayout());
        add(rootPanel, BorderLayout.CENTER);
        bindActions();
        refreshTimer = new Timer(60000, event -> {
            if (currentView == View.CALENDAR || currentView == View.STANDINGS) {
                renderCurrentView();
            }
        });
        refreshTimer.start();
        renderCurrentView();
    }

    private void bindActions() {
        homeButton.addActionListener(event -> show(View.HOME));
        fasesButton.addActionListener(event -> show(View.FASES));
        calendarButton.addActionListener(event -> show(View.CALENDAR));
        standingsButton.addActionListener(event -> show(View.STANDINGS));
        menuButton.addActionListener(event -> show(View.HOME));
        teamComboBox.addActionListener(event -> {
            Object item = teamComboBox.getSelectedItem();
            if (item instanceof WorldCupTeam team && !team.equals(selectedTeam)) {
                selectedTeam = team;
                renderCurrentView();
            }
        });
    }

    private void show(View view) {
        currentView = view;
        renderCurrentView();
    }

    private void renderCurrentView() {
        contentPanel.removeAll();
        JPanel viewPanel = switch (currentView) {
            case HOME -> new HomePanel(selectedTeam, () -> show(View.FASES)).getRootPanel();
            case FASES -> new FasesPanel(selectedTeam).getRootPanel();
            case CALENDAR -> new CalendarPanel().getRootPanel();
            case STANDINGS -> new StandingsPanel(selectedTeam).getRootPanel();
        };
        contentPanel.add(viewPanel, BorderLayout.CENTER);
        updateActiveButtons();
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void updateActiveButtons() {
        styleNavButton(homeButton, currentView == View.HOME);
        styleNavButton(fasesButton, currentView == View.FASES);
        styleNavButton(calendarButton, currentView == View.CALENDAR);
        styleNavButton(standingsButton, currentView == View.STANDINGS);
        styleNavButton(statisticsButton, false);
    }

    private void styleNavButton(JButton button, boolean active) {
        button.setForeground(active ? AppTheme.TEXT : new Color(0xC9D6EA));
        button.setBackground(active ? new Color(0x1A356E) : AppTheme.SIDEBAR);
    }

    private JButton navButton(String text) {
        JButton button = new SidebarButton(text);
        button.setUI(new BasicButtonUI());
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setFont(AppTheme.BODY_FONT);
        button.setForeground(new Color(0xC9D6EA));
        button.setBackground(AppTheme.SIDEBAR);
        button.setBorder(BorderFactory.createEmptyBorder(9, 14, 9, 14));
        button.setPreferredSize(new Dimension(224, 38));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void addSidebarSection(JPanel parent, int row, String title, JButton... buttons) {
        parent.add(UiSupport.label(title, AppTheme.TEXT, AppTheme.SECTION_FONT), UiSupport.constraints(row, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
        JPanel links = UiSupport.panel(AppTheme.SIDEBAR, buttons.length, 1, new Insets(0, 0, 12, 0), 0, 1);
        for (int i = 0; i < buttons.length; i++) {
            links.add(buttons[i], UiSupport.constraints(i, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
        }
        parent.add(links, UiSupport.constraints(row + 1, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
    }

    private void buildUi() {
        rootPanel = UiSupport.panel(AppTheme.BACKGROUND, 1, 2, new Insets(0, 0, 0, 0), 0, 0);
        sidebarPanel = UiSupport.panel(AppTheme.SIDEBAR, 11, 1, new Insets(18, 18, 18, 18), 0, 10);
        sidebarPanel.setPreferredSize(new Dimension(276, 720));
        JButton brand = navButton("FIFA\u00B0");
        brand.setFont(AppTheme.BRAND_FONT);
        brand.setForeground(AppTheme.TEXT);
        brand.setPreferredSize(new Dimension(224, 64));
        brand.addActionListener(event -> show(View.HOME));
        sidebarPanel.add(brand, UiSupport.constraints(0, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
        homeButton = navButton("Painel principal");
        fasesButton = navButton("Fases");
        calendarButton = navButton("Calendario");
        standingsButton = navButton("Classificacoes");
        statisticsButton = navButton("Estatisticas");
        addSidebarSection(sidebarPanel, 1, "GERAL", homeButton);
        addSidebarSection(sidebarPanel, 3, "COMPETICOES", fasesButton, calendarButton, standingsButton, statisticsButton);
        addSidebarSection(sidebarPanel, 5, "ENTIDADES", navButton("Equipas"), navButton("Arbitros"), navButton("Estadios"));
        addSidebarSection(sidebarPanel, 7, "BILHETES", navButton("Comprar"), navButton("Tickets purchased"));
        addSidebarSection(sidebarPanel, 9, "HOSPITALIDADE", navButton("Hoteis"), navButton("Locacoes"));
        rootPanel.add(sidebarPanel, UiSupport.constraints(0, 0, 1, 1, GridConstraints.FILL_VERTICAL));

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(true);
        mainPanel.setBackground(AppTheme.BACKGROUND);
        headerPanel = UiSupport.panel(AppTheme.HEADER, 1, 4, new Insets(8, 22, 8, 22), 14, 0);
        headerPanel.setPreferredSize(new Dimension(900, 60));
        menuButton = new HeaderMenuButton();
        menuButton.setUI(new BasicButtonUI());
        menuButton.setIcon(AppIcons.menu());
        menuButton.setForeground(Color.WHITE);
        menuButton.setBackground(new Color(0x10245A));
        menuButton.setPreferredSize(new Dimension(54, 44));
        menuButton.setBorder(BorderFactory.createEmptyBorder());
        menuButton.setBorderPainted(false);
        menuButton.setContentAreaFilled(false);
        menuButton.setFocusPainted(false);
        menuButton.setOpaque(false);
        headerPanel.add(menuButton, UiSupport.constraints(0, 0, 1, 1, GridConstraints.FILL_BOTH));

        JPanel searchPanel = UiSupport.panel(Color.WHITE, 1, 2, new Insets(0, 12, 0, 12), 8, 0);
        searchPanel.setPreferredSize(new Dimension(260, 40));
        JLabel searchIcon = new JLabel(AppIcons.search());
        searchIcon.setForeground(new Color(0x111827));
        searchPanel.add(searchIcon, UiSupport.constraints(0, 0, 1, 1, GridConstraints.FILL_NONE));
        searchField = new JTextField("Pesquisar fase, equipa, estadio...");
        searchField.setBorder(BorderFactory.createEmptyBorder());
        searchPanel.add(searchField, UiSupport.constraints(0, 1, 1, 1, GridConstraints.FILL_HORIZONTAL));
        headerPanel.add(searchPanel, UiSupport.constraints(0, 1, 1, 1, GridConstraints.FILL_HORIZONTAL));

        JPanel comboPanel = UiSupport.panel(Color.WHITE, 1, 1, new Insets(0, 8, 0, 8), 0, 0);
        comboPanel.setPreferredSize(new Dimension(240, 40));
        teamComboBox = new JComboBox<>(WorldCupData.teams());
        teamComboBox.setSelectedItem(selectedTeam);
        teamComboBox.setRenderer(new TeamRenderer());
        teamComboBox.setBorder(BorderFactory.createEmptyBorder());
        teamComboBox.setBackground(Color.WHITE);
        comboPanel.add(teamComboBox, UiSupport.constraints(0, 0, 1, 1, GridConstraints.FILL_BOTH));
        headerPanel.add(comboPanel, UiSupport.constraints(0, 2, 1, 1, GridConstraints.FILL_HORIZONTAL));

        JPanel datePanel = UiSupport.panel(Color.WHITE, 1, 2, new Insets(0, 12, 0, 12), 10, 0);
        datePanel.setPreferredSize(new Dimension(176, 40));
        dateLabel = UiSupport.label("JUNE 2026", new Color(0x111827), AppTheme.BODY_FONT);
        datePanel.add(dateLabel, UiSupport.constraints(0, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
        JLabel calendar = new JLabel(AppIcons.calendar());
        calendar.setForeground(new Color(0x111827));
        datePanel.add(calendar, UiSupport.constraints(0, 1, 1, 1, GridConstraints.FILL_NONE));
        headerPanel.add(datePanel, UiSupport.constraints(0, 3, 1, 1, GridConstraints.FILL_HORIZONTAL));

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(true);
        contentPanel.setBackground(AppTheme.BACKGROUND);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        rootPanel.add(mainPanel, UiSupport.constraints(0, 1, 1, 1, GridConstraints.FILL_BOTH));
    }

    private enum View {
        HOME,
        FASES,
        CALENDAR,
        STANDINGS
    }

    private static final class TeamRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof WorldCupTeam team) {
                label.setText(team.code() + "  " + team.name().toUpperCase());
                label.setIcon(AppIcons.teamFlag(team.code()));
            }
            label.setBorder(BorderFactory.createEmptyBorder(3, 4, 3, 4));
            return label;
        }
    }

    private static final class SidebarButton extends JButton {
        private SidebarButton(String text) {
            super(text);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Color background = getModel().isPressed() ? new Color(0x24498F) : getBackground();
            if (getModel().isRollover()) {
                background = new Color(0x18305F);
            }
            g2.setColor(background);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 7, 7);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    private static final class HeaderMenuButton extends JButton {
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Color base = getModel().isPressed() ? new Color(0x07163D) : getBackground();
            if (getModel().isRollover()) {
                base = new Color(0x17336F);
            }
            g2.setColor(base);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
            g2.setColor(new Color(255, 255, 255, 72));
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 8, 8);
            g2.dispose();
            super.paintComponent(g);
        }
    }
}
