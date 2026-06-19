package pt.ipleiria.es.worldcup.ui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MainScreen extends JPanel {
    private static final Color GROUP_TITLE = new Color(0xF8D12F);
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MMMM d", Locale.ENGLISH);
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");
    private static final String EASTERN = "America/New_York";
    private static final String CENTRAL = "America/Chicago";
    private static final String PACIFIC = "America/Los_Angeles";
    private static final String VANCOUVER = "America/Vancouver";
    private static final String TORONTO = "America/Toronto";
    private static final String MEXICO = "America/Mexico_City";
    private static final CalendarGroup[] CALENDAR_GROUPS = {
            group("Group A",
                    result("2026-06-11", "13:00", "Mexico vs South Africa", "Estadio Azteca", "Mexico City", MEXICO, 2, 0),
                    result("2026-06-11", "20:00", "South Korea vs Czech Republic", "Estadio Akron", "Zapopan", MEXICO, 2, 1),
                    result("2026-06-18", "12:00", "Czech Republic vs South Africa", "Mercedes-Benz Stadium", "Atlanta", EASTERN, 1, 1),
                    result("2026-06-18", "19:00", "Mexico vs South Korea", "Estadio Akron", "Zapopan", MEXICO, 1, 0),
                    match("2026-06-24", "19:00", "Czech Republic vs Mexico", "Estadio Azteca", "Mexico City", MEXICO),
                    match("2026-06-24", "19:00", "South Africa vs South Korea", "Estadio BBVA", "Guadalupe", MEXICO)),
            group("Group B",
                    result("2026-06-12", "15:00", "Canada vs Bosnia and Herzegovina", "BMO Field", "Toronto", TORONTO, 1, 1),
                    result("2026-06-13", "12:00", "Qatar vs Switzerland", "Levi's Stadium", "Santa Clara", PACIFIC, 1, 1),
                    result("2026-06-18", "12:00", "Switzerland vs Bosnia and Herzegovina", "SoFi Stadium", "Inglewood", PACIFIC, 4, 1),
                    result("2026-06-18", "15:00", "Canada vs Qatar", "BC Place", "Vancouver", VANCOUVER, 6, 0),
                    match("2026-06-24", "12:00", "Switzerland vs Canada", "BC Place", "Vancouver", VANCOUVER),
                    match("2026-06-24", "12:00", "Bosnia and Herzegovina vs Qatar", "Lumen Field", "Seattle", PACIFIC)),
            group("Group C",
                    result("2026-06-13", "18:00", "Brazil vs Morocco", "MetLife Stadium", "East Rutherford", EASTERN, 1, 1),
                    result("2026-06-13", "21:00", "Haiti vs Scotland", "Gillette Stadium", "Foxborough", EASTERN, 0, 1),
                    match("2026-06-19", "18:00", "Scotland vs Morocco", "Gillette Stadium", "Foxborough", EASTERN),
                    match("2026-06-19", "20:30", "Brazil vs Haiti", "Lincoln Financial Field", "Philadelphia", EASTERN),
                    match("2026-06-24", "18:00", "Scotland vs Brazil", "Hard Rock Stadium", "Miami Gardens", EASTERN),
                    match("2026-06-24", "18:00", "Morocco vs Haiti", "Mercedes-Benz Stadium", "Atlanta", EASTERN)),
            group("Group D",
                    result("2026-06-12", "18:00", "United States vs Paraguay", "SoFi Stadium", "Inglewood", PACIFIC, 4, 1),
                    result("2026-06-13", "21:00", "Australia vs Turkey", "BC Place", "Vancouver", VANCOUVER, 2, 0),
                    match("2026-06-19", "12:00", "United States vs Australia", "Lumen Field", "Seattle", PACIFIC),
                    match("2026-06-19", "20:00", "Turkey vs Paraguay", "Levi's Stadium", "Santa Clara", PACIFIC),
                    match("2026-06-25", "19:00", "Turkey vs United States", "SoFi Stadium", "Inglewood", PACIFIC),
                    match("2026-06-25", "19:00", "Paraguay vs Australia", "Levi's Stadium", "Santa Clara", PACIFIC)),
            group("Group E",
                    result("2026-06-14", "12:00", "Germany vs Curacao", "NRG Stadium", "Houston", CENTRAL, 7, 1),
                    result("2026-06-14", "19:00", "Ivory Coast vs Ecuador", "Lincoln Financial Field", "Philadelphia", EASTERN, 1, 0),
                    match("2026-06-20", "16:00", "Germany vs Ivory Coast", "BMO Field", "Toronto", TORONTO),
                    match("2026-06-20", "19:00", "Ecuador vs Curacao", "Arrowhead Stadium", "Kansas City", CENTRAL),
                    match("2026-06-25", "16:00", "Curacao vs Ivory Coast", "Lincoln Financial Field", "Philadelphia", EASTERN),
                    match("2026-06-25", "16:00", "Ecuador vs Germany", "MetLife Stadium", "East Rutherford", EASTERN)),
            group("Group F",
                    result("2026-06-14", "15:00", "Netherlands vs Japan", "AT&T Stadium", "Arlington", CENTRAL, 2, 2),
                    result("2026-06-14", "20:00", "Sweden vs Tunisia", "Estadio BBVA", "Guadalupe", MEXICO, 5, 1),
                    match("2026-06-20", "12:00", "Netherlands vs Sweden", "NRG Stadium", "Houston", CENTRAL),
                    match("2026-06-20", "22:00", "Tunisia vs Japan", "Estadio BBVA", "Guadalupe", MEXICO),
                    match("2026-06-25", "18:00", "Japan vs Sweden", "AT&T Stadium", "Arlington", CENTRAL),
                    match("2026-06-25", "18:00", "Tunisia vs Netherlands", "Arrowhead Stadium", "Kansas City", CENTRAL)),
            group("Group G",
                    result("2026-06-15", "12:00", "Belgium vs Egypt", "Lumen Field", "Seattle", PACIFIC, 1, 1),
                    result("2026-06-15", "18:00", "Iran vs New Zealand", "SoFi Stadium", "Inglewood", PACIFIC, 2, 2),
                    match("2026-06-21", "12:00", "Belgium vs Iran", "SoFi Stadium", "Inglewood", PACIFIC),
                    match("2026-06-21", "18:00", "New Zealand vs Egypt", "BC Place", "Vancouver", VANCOUVER),
                    match("2026-06-26", "20:00", "Egypt vs Iran", "Lumen Field", "Seattle", PACIFIC),
                    match("2026-06-26", "20:00", "New Zealand vs Belgium", "BC Place", "Vancouver", VANCOUVER)),
            group("Group H",
                    result("2026-06-15", "12:00", "Spain vs Cape Verde", "Mercedes-Benz Stadium", "Atlanta", EASTERN, 0, 0),
                    result("2026-06-15", "18:00", "Saudi Arabia vs Uruguay", "Hard Rock Stadium", "Miami Gardens", EASTERN, 1, 1),
                    match("2026-06-21", "12:00", "Spain vs Saudi Arabia", "Mercedes-Benz Stadium", "Atlanta", EASTERN),
                    match("2026-06-21", "18:00", "Uruguay vs Cape Verde", "Hard Rock Stadium", "Miami Gardens", EASTERN),
                    match("2026-06-26", "19:00", "Cape Verde vs Saudi Arabia", "NRG Stadium", "Houston", CENTRAL),
                    match("2026-06-26", "18:00", "Uruguay vs Spain", "Estadio Akron", "Zapopan", MEXICO)),
            group("Group I",
                    result("2026-06-16", "15:00", "France vs Senegal", "MetLife Stadium", "East Rutherford", EASTERN, 3, 1),
                    result("2026-06-16", "18:00", "Iraq vs Norway", "Gillette Stadium", "Foxborough", EASTERN, 1, 4),
                    match("2026-06-22", "17:00", "France vs Iraq", "Lincoln Financial Field", "Philadelphia", EASTERN),
                    match("2026-06-22", "20:00", "Norway vs Senegal", "MetLife Stadium", "East Rutherford", EASTERN),
                    match("2026-06-26", "15:00", "Norway vs France", "Gillette Stadium", "Foxborough", EASTERN),
                    match("2026-06-26", "15:00", "Senegal vs Iraq", "BMO Field", "Toronto", TORONTO)),
            group("Group J",
                    result("2026-06-16", "20:00", "Argentina vs Algeria", "Arrowhead Stadium", "Kansas City", CENTRAL, 3, 0),
                    result("2026-06-16", "21:00", "Austria vs Jordan", "Levi's Stadium", "Santa Clara", PACIFIC, 3, 1),
                    match("2026-06-22", "12:00", "Argentina vs Austria", "AT&T Stadium", "Arlington", CENTRAL),
                    match("2026-06-22", "20:00", "Jordan vs Algeria", "Levi's Stadium", "Santa Clara", PACIFIC),
                    match("2026-06-27", "21:00", "Algeria vs Austria", "Arrowhead Stadium", "Kansas City", CENTRAL),
                    match("2026-06-27", "21:00", "Jordan vs Argentina", "AT&T Stadium", "Arlington", CENTRAL)),
            group("Group K",
                    result("2026-06-17", "12:00", "Portugal vs DR Congo", "NRG Stadium", "Houston", CENTRAL, 1, 1),
                    result("2026-06-17", "20:00", "Uzbekistan vs Colombia", "Estadio Azteca", "Mexico City", MEXICO, 1, 3),
                    match("2026-06-23", "12:00", "Portugal vs Uzbekistan", "NRG Stadium", "Houston", CENTRAL),
                    match("2026-06-23", "20:00", "Colombia vs DR Congo", "Estadio Akron", "Zapopan", MEXICO),
                    match("2026-06-27", "19:30", "Colombia vs Portugal", "Hard Rock Stadium", "Miami Gardens", EASTERN),
                    match("2026-06-27", "19:30", "DR Congo vs Uzbekistan", "Mercedes-Benz Stadium", "Atlanta", EASTERN)),
            group("Group L",
                    result("2026-06-17", "15:00", "England vs Croatia", "AT&T Stadium", "Arlington", CENTRAL, 4, 2),
                    result("2026-06-17", "19:00", "Ghana vs Panama", "BMO Field", "Toronto", TORONTO, 1, 0),
                    match("2026-06-23", "16:00", "England vs Ghana", "Gillette Stadium", "Foxborough", EASTERN),
                    match("2026-06-23", "19:00", "Panama vs Croatia", "BMO Field", "Toronto", TORONTO),
                    match("2026-06-27", "17:00", "Panama vs England", "MetLife Stadium", "East Rutherford", EASTERN),
                    match("2026-06-27", "17:00", "Croatia vs Ghana", "Lincoln Financial Field", "Philadelphia", EASTERN))
    };

    private enum View {
        HOME,
        FASES,
        CALENDARIO,
        CLASSIFICACOES
    }

    private View currentView = View.HOME;
    private WorldCupTeam selectedTeam = WorldCupData.defaultTeam();
    private final Timer calendarRefreshTimer;

    public MainScreen() {
        setBackground(AppTheme.BACKGROUND);
        setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), 0, 0));
        calendarRefreshTimer = new Timer(60000, event -> {
            if (currentView == View.CALENDARIO || currentView == View.CLASSIFICACOES) {
                renderScreen();
            }
        });
        calendarRefreshTimer.start();
        renderScreen();
    }

    private void renderScreen() {
        removeAll();
        add(buildSidebar(), constraints(0, 0, 1, 1, GridConstraints.FILL_VERTICAL));
        add(buildMainArea(), constraints(0, 1, 1, 1, GridConstraints.FILL_BOTH));
        revalidate();
        repaint();
    }

    private void show(View view) {
        currentView = view;
        renderScreen();
    }

    private JPanel buildSidebar() {
        JPanel sidebar = panel(AppTheme.SIDEBAR, 17, 1, new Insets(14, 16, 14, 16), 0, 8);
        sidebar.setPreferredSize(new Dimension(256, 720));

        sidebar.add(brandButton(), constraints(0, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
        addNavSection(sidebar, 1, "GERAL", new String[]{"Painel principal"}, new View[]{View.HOME});
        addNavSection(sidebar, 3, "COMPETICOES", new String[]{"Fases", "Calendario", "Classificacoes", "Estatisticas"}, new View[]{View.FASES, View.CALENDARIO, View.CLASSIFICACOES, null});
        addNavSection(sidebar, 5, "ENTIDADES", new String[]{"Equipas", "Arbitros", "Estadios"}, new View[]{null, null, null});
        addNavSection(sidebar, 7, "BILHETES", new String[]{"Comprar", "Tickets purchased"}, new View[]{null, null});
        addNavSection(sidebar, 9, "HOSPITALIDADE", new String[]{"Hoteis", "Locacoes"}, new View[]{null, null});

        sidebar.add(new Spacer(), constraints(11, 0, 6, 1, GridConstraints.FILL_BOTH));
        return sidebar;
    }

    private JButton brandButton() {
        JButton button = navButton("FIFA\u00B0", currentView == View.HOME, View.HOME);
        button.setFont(AppTheme.BRAND_FONT);
        button.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        return button;
    }

    private void addNavSection(JPanel parent, int row, String title, String[] links, View[] targets) {
        JLabel heading = label(title, AppTheme.TEXT, AppTheme.SECTION_FONT);
        parent.add(heading, constraints(row, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));

        JPanel linksPanel = panel(AppTheme.SIDEBAR, links.length, 1, new Insets(0, 0, 12, 0), 0, 1);
        for (int i = 0; i < links.length; i++) {
            View target = i < targets.length ? targets[i] : null;
            JButton link = navButton(links[i], target != null && target == currentView, target);
            linksPanel.add(link, constraints(i, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
        }
        parent.add(linksPanel, constraints(row + 1, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
    }

    private JButton navButton(String text, boolean active, View target) {
        JButton button = new SidebarButton(text);
        button.setUI(new BasicButtonUI());
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setFont(AppTheme.BODY_FONT);
        button.setForeground(active ? AppTheme.TEXT : new Color(0xC9D6EA));
        button.setBackground(active ? new Color(0x1A356E) : AppTheme.SIDEBAR);
        button.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        if (target != null) {
            button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            button.addActionListener(event -> show(target));
        }
        return button;
    }

    private JPanel buildMainArea() {
        JPanel main = panel(AppTheme.BACKGROUND, 2, 1, new Insets(0, 0, 0, 0), 0, 0);
        main.add(buildHeader(), constraints(0, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
        main.add(buildCurrentContent(), constraints(1, 0, 1, 1, GridConstraints.FILL_BOTH));
        return main;
    }

    private JScrollPane buildCurrentContent() {
        return switch (currentView) {
            case FASES -> buildFasesContent();
            case CALENDARIO -> buildCalendarioContent();
            case CLASSIFICACOES -> buildClassificacoesContent();
            case HOME -> buildHomeContent();
        };
    }

    private JPanel buildHeader() {
        JPanel header = panel(AppTheme.HEADER, 1, 4, new Insets(11, 30, 11, 30), 24, 0);
        header.setPreferredSize(new Dimension(900, 72));

        JButton menu = menuButton();
        menu.addActionListener(event -> show(View.HOME));
        header.add(menu, constraints(0, 0, 1, 1, GridConstraints.FILL_BOTH));

        JPanel searchBox = whitePanel(1, 2, new Insets(0, 12, 0, 12), 8, 0);
        JLabel searchIcon = new JLabel(AppIcons.search());
        searchIcon.setForeground(new Color(0x111827));
        searchBox.add(searchIcon, constraints(0, 0, 1, 1, GridConstraints.FILL_NONE));
        JTextField search = new JTextField("Pesquisar fase, equipa, estadio...");
        search.setForeground(new Color(0x111827));
        search.setBorder(BorderFactory.createEmptyBorder());
        searchBox.add(search, constraints(0, 1, 1, 1, GridConstraints.FILL_HORIZONTAL));
        header.add(searchBox, constraints(0, 1, 1, 1, GridConstraints.FILL_HORIZONTAL));

        JPanel team = whitePanel(1, 1, new Insets(0, 8, 0, 8), 0, 0);
        team.setPreferredSize(new Dimension(240, 40));
        JComboBox<WorldCupTeam> selector = new JComboBox<>(WorldCupData.teams());
        selector.setSelectedItem(selectedTeam);
        selector.setRenderer(new TeamRenderer());
        selector.setFont(AppTheme.BODY_FONT);
        selector.setBackground(Color.WHITE);
        selector.setForeground(new Color(0x111827));
        selector.setBorder(BorderFactory.createEmptyBorder());
        selector.addActionListener(event -> {
            Object item = selector.getSelectedItem();
            if (item instanceof WorldCupTeam teamSelection && !teamSelection.equals(selectedTeam)) {
                selectedTeam = teamSelection;
                renderScreen();
            }
        });
        team.add(selector, constraints(0, 0, 1, 1, GridConstraints.FILL_BOTH));
        header.add(team, constraints(0, 2, 1, 1, GridConstraints.FILL_HORIZONTAL));

        JPanel date = whitePanel(1, 2, new Insets(0, 12, 0, 12), 10, 0);
        date.add(label("JUNE 2026", new Color(0x111827), AppTheme.BODY_FONT), constraints(0, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
        JLabel calendar = new JLabel(AppIcons.calendar());
        calendar.setForeground(new Color(0x111827));
        date.add(calendar, constraints(0, 1, 1, 1, GridConstraints.FILL_NONE));
        header.add(date, constraints(0, 3, 1, 1, GridConstraints.FILL_HORIZONTAL));
        return header;
    }

    private JScrollPane buildHomeContent() {
        JPanel content = panel(AppTheme.BACKGROUND, 3, 1, new Insets(16, 32, 42, 32), 0, 18);

        JPanel titleRow = panel(AppTheme.BACKGROUND, 1, 2, new Insets(0, 0, 0, 0), 16, 0);
        JPanel titleText = panel(AppTheme.BACKGROUND, 2, 1, new Insets(0, 0, 0, 0), 0, 3);
        titleText.add(label("CAMPEONATO DO MUNDO FIFA 2026", AppTheme.MUTED, AppTheme.BODY_BOLD_FONT), constraints(0, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
        titleText.add(label("PAINEL PRINCIPAL", AppTheme.TEXT, AppTheme.TITLE_FONT), constraints(1, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
        titleRow.add(titleText, constraints(0, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
        titleRow.add(primaryButton("Abrir fases", () -> show(View.FASES)), constraints(0, 1, 1, 1, GridConstraints.FILL_NONE));
        content.add(titleRow, constraints(0, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));

        content.add(buildOverview(), constraints(1, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
        content.add(buildModuleGrid(), constraints(2, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));

        return scrollPane(content);
    }

    private JScrollPane buildFasesContent() {
        JPanel content = panel(AppTheme.BACKGROUND, 2, 1, new Insets(16, 32, 42, 32), 0, 18);

        JPanel titleRow = panel(AppTheme.BACKGROUND, 1, 2, new Insets(0, 0, 0, 0), 16, 0);
        JPanel titleText = panel(AppTheme.BACKGROUND, 2, 1, new Insets(0, 0, 0, 0), 0, 3);
        titleText.add(label("SELECAO: " + selectedTeam.name().toUpperCase(), AppTheme.MUTED, AppTheme.BODY_BOLD_FONT), constraints(0, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
        titleText.add(label("FASES", AppTheme.TEXT, AppTheme.TITLE_FONT), constraints(1, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
        titleRow.add(titleText, constraints(0, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
        titleRow.add(secondaryButton("Voltar", () -> show(View.HOME)), constraints(0, 1, 1, 1, GridConstraints.FILL_NONE));
        content.add(titleRow, constraints(0, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));

        content.add(new BracketPanel(), constraints(1, 0, 1, 1, GridConstraints.FILL_BOTH));

        return scrollPane(content);
    }

    private JScrollPane buildCalendarioContent() {
        JPanel content = panel(AppTheme.BACKGROUND, 2, 1, new Insets(16, 32, 42, 32), 0, 18);

        JPanel titleRow = panel(AppTheme.BACKGROUND, 1, 1, new Insets(0, 0, 0, 0), 0, 0);
        titleRow.add(label("CALENDAR", AppTheme.TEXT, AppTheme.TITLE_FONT), constraints(0, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
        content.add(titleRow, constraints(0, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));

        JPanel groups = panel(AppTheme.BACKGROUND, 6, 2, new Insets(0, 0, 0, 0), 8, 10);
        for (int i = 0; i < CALENDAR_GROUPS.length; i++) {
            groups.add(calendarCard(CALENDAR_GROUPS[i]), constraints(i / 2, i % 2, 1, 1, GridConstraints.FILL_BOTH));
        }
        content.add(groups, constraints(1, 0, 1, 1, GridConstraints.FILL_BOTH));

        return scrollPane(content);
    }

    private JScrollPane buildClassificacoesContent() {
        JPanel content = panel(AppTheme.BACKGROUND, 2, 1, new Insets(16, 32, 42, 32), 0, 18);

        JPanel titleRow = panel(AppTheme.BACKGROUND, 2, 1, new Insets(0, 0, 0, 0), 0, 3);
        titleRow.add(label("CLASSIFICACOES", AppTheme.TEXT, AppTheme.TITLE_FONT), constraints(0, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
        titleRow.add(label("GRUPOS DO MUNDIAL FIFA 2026", AppTheme.MUTED, AppTheme.BODY_BOLD_FONT), constraints(1, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
        content.add(titleRow, constraints(0, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));

        JPanel groups = panel(AppTheme.BACKGROUND, 6, 2, new Insets(0, 0, 0, 0), 8, 10);
        for (int i = 0; i < CALENDAR_GROUPS.length; i++) {
            groups.add(classificacaoCard(CALENDAR_GROUPS[i]), constraints(i / 2, i % 2, 1, 1, GridConstraints.FILL_BOTH));
        }
        content.add(groups, constraints(1, 0, 1, 1, GridConstraints.FILL_BOTH));

        return scrollPane(content);
    }

    private JPanel classificacaoCard(CalendarGroup group) {
        StandingRow[] rows = standingsFor(group);
        JPanel card = roundedPanel(AppTheme.CHIP, 2, 1, new Insets(12, 18, 14, 18), 0, 8, 10);
        card.setPreferredSize(new Dimension(570, 236));
        card.setMinimumSize(new Dimension(520, 236));
        card.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 16)));

        JPanel header = panel(AppTheme.CHIP, 1, 2, new Insets(0, 0, 2, 0), 14, 0);
        header.add(label(group.name().toUpperCase(), GROUP_TITLE, new Font("Inter", Font.BOLD, 16)), constraints(0, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
        header.add(groupFlags(group), constraints(0, 1, 1, 1, GridConstraints.FILL_NONE));
        card.add(header, constraints(0, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));

        card.add(classificacaoTable(rows), constraints(1, 0, 1, 1, GridConstraints.FILL_BOTH));
        return card;
    }

    private JPanel classificacaoTable(StandingRow[] rows) {
        JPanel table = panel(AppTheme.CHIP, rows.length + 1, 8, new Insets(0, 0, 0, 0), 0, 5);
        addClassificacaoRow(table, 0, new JLabel[]{
                tableHeader("#", 34),
                tableHeader("EQUIPA", 210),
                tableHeader("J", 42),
                tableHeader("V", 42),
                tableHeader("E", 42),
                tableHeader("D", 42),
                tableHeader("DG", 50),
                tableHeader("PTS", 54)
        });

        for (int i = 0; i < rows.length; i++) {
            StandingRow standing = rows[i];
            Color background = classificacaoRowColor(standing, i + 1);
            addClassificacaoRow(table, i + 1, new JLabel[]{
                    tableValue(String.valueOf(i + 1), AppTheme.MUTED, 34, background),
                    classificacaoTeamLabel(standing.team(), background),
                    tableValue(String.valueOf(standing.played()), AppTheme.TEXT, 42, background),
                    tableValue(String.valueOf(standing.wins()), AppTheme.TEXT, 42, background),
                    tableValue(String.valueOf(standing.draws()), AppTheme.TEXT, 42, background),
                    tableValue(String.valueOf(standing.losses()), AppTheme.TEXT, 42, background),
                    tableValue(signedNumber(standing.goalDifference()), AppTheme.TEXT, 50, background),
                    tableValue(String.valueOf(standing.points()), GROUP_TITLE, 54, background)
            });
        }
        return table;
    }

    private void addClassificacaoRow(JPanel table, int row, JLabel[] cells) {
        for (int col = 0; col < cells.length; col++) {
            table.add(cells[col], constraints(row, col, 1, 1, GridConstraints.FILL_BOTH));
        }
    }

    private Color classificacaoRowColor(StandingRow standing, int position) {
        if (standing.team().name().equals(selectedTeam.name())) {
            return new Color(0x1A5BAA);
        }
        if (position <= 2) {
            return new Color(0x153F7B);
        }
        return AppTheme.CHIP;
    }

    private JLabel classificacaoTeamLabel(WorldCupTeam team, Color background) {
        JLabel label = label(team.name(), AppTheme.TEXT, new Font("Inter", Font.BOLD, 12));
        label.setIcon(AppIcons.teamFlag(team.code(), 24, 18));
        label.setIconTextGap(7);
        label.setToolTipText(team.name());
        label.setOpaque(true);
        label.setBackground(background);
        label.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
        label.setPreferredSize(new Dimension(210, 30));
        return label;
    }

    private JLabel tableHeader(String text, int width) {
        JLabel label = label(text, AppTheme.MUTED, new Font("Inter", Font.BOLD, 11));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(width, 18));
        return label;
    }

    private JLabel tableValue(String text, Color color, int width, Color background) {
        JLabel label = label(text, color, new Font("Inter", Font.BOLD, 12));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setOpaque(true);
        label.setBackground(background);
        label.setPreferredSize(new Dimension(width, 30));
        return label;
    }

    private JPanel calendarCard(CalendarGroup group) {
        JPanel card = roundedPanel(AppTheme.CHIP, 2, 1, new Insets(12, 18, 14, 18), 0, 8, 10);
        card.setPreferredSize(new Dimension(570, 326));
        card.setMinimumSize(new Dimension(520, 326));
        card.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 16)));

        JPanel header = panel(AppTheme.CHIP, 1, 2, new Insets(0, 0, 2, 0), 14, 0);
        JLabel title = label(group.name().toUpperCase(), GROUP_TITLE, new Font("Inter", Font.BOLD, 16));
        header.add(title, constraints(0, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
        header.add(groupFlags(group), constraints(0, 1, 1, 1, GridConstraints.FILL_NONE));
        card.add(header, constraints(0, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));

        JPanel matches = panel(AppTheme.CHIP, group.matches().length, 1, new Insets(0, 0, 0, 0), 0, 4);
        for (int i = 0; i < group.matches().length; i++) {
            matches.add(matchRow(group.matches()[i]), constraints(i, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
        }
        card.add(matches, constraints(1, 0, 1, 1, GridConstraints.FILL_BOTH));
        return card;
    }

    private JPanel groupFlags(CalendarGroup group) {
        String[] names = group.teamNames();
        JPanel flags = panel(AppTheme.CHIP, 1, names.length, new Insets(0, 0, 0, 0), 4, 0);
        for (int i = 0; i < names.length; i++) {
            WorldCupTeam team = WorldCupData.findByName(names[i]);
            JLabel flag = new JLabel(team == null ? null : AppIcons.teamFlag(team.code(), 30, 22));
            flag.setToolTipText(names[i]);
            flags.add(flag, constraints(0, i, 1, 1, GridConstraints.FILL_NONE));
        }
        return flags;
    }

    private JPanel matchRow(CalendarMatch match) {
        JPanel row = panel(AppTheme.CHIP, 2, 1, new Insets(1, 0, 1, 0), 0, 2);

        JPanel top = panel(AppTheme.CHIP, 1, 5, new Insets(0, 0, 0, 0), 8, 0);
        JLabel time = label(DATE_FORMAT.format(match.date()).toUpperCase(Locale.ENGLISH) + "  " + TIME_FORMAT.format(match.time()), GROUP_TITLE, new Font("Inter", Font.BOLD, 11));
        time.setPreferredSize(new Dimension(92, 20));
        top.add(time, constraints(0, 0, 1, 1, GridConstraints.FILL_NONE));
        top.add(teamLabel(match.home()), constraints(0, 1, 1, 1, GridConstraints.FILL_HORIZONTAL));
        JLabel versus = label(matchScoreText(match), match.score() == null ? AppTheme.MUTED : GROUP_TITLE, new Font("Inter", Font.BOLD, 10));
        versus.setHorizontalAlignment(SwingConstants.CENTER);
        top.add(versus, constraints(0, 2, 1, 1, GridConstraints.FILL_NONE));
        top.add(teamLabel(match.away()), constraints(0, 3, 1, 1, GridConstraints.FILL_HORIZONTAL));
        top.add(statusLabel(matchStatus(match)), constraints(0, 4, 1, 1, GridConstraints.FILL_NONE));
        row.add(top, constraints(0, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));

        JLabel venue = label(match.venue() + ", " + match.city(), AppTheme.MUTED, new Font("Inter", Font.PLAIN, 11));
        venue.setBorder(BorderFactory.createEmptyBorder(0, 100, 0, 0));
        row.add(venue, constraints(1, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
        return row;
    }

    private String matchScoreText(CalendarMatch match) {
        MatchScore score = match.score();
        if (score == null) {
            return "VS";
        }
        return score.homeGoals() + "-" + score.awayGoals();
    }

    private JLabel teamLabel(String name) {
        WorldCupTeam team = WorldCupData.findByName(name);
        JLabel label = label(name, AppTheme.TEXT, new Font("Inter", Font.BOLD, 12));
        if (team != null) {
            label.setIcon(AppIcons.teamFlag(team.code(), 24, 18));
            label.setIconTextGap(6);
        }
        label.setToolTipText(name);
        return label;
    }

    private JLabel statusLabel(MatchStatus status) {
        JLabel label = label(status.label(), status.color(), new Font("Inter", Font.BOLD, 11));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        label.setPreferredSize(new Dimension(104, 20));
        return label;
    }

    private MatchStatus matchStatus(CalendarMatch match) {
        if (match.score() != null) {
            return MatchStatus.PLAYED;
        }
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime kickoff = match.kickoff();
        if (now.isBefore(kickoff.minusMinutes(30))) {
            return MatchStatus.TO_PLAY;
        }
        if (now.isBefore(kickoff.plusHours(2))) {
            return MatchStatus.LIVE;
        }
        return MatchStatus.NEEDS_RESULT;
    }

    private StandingRow[] standingsFor(CalendarGroup group) {
        Map<String, StandingAccumulator> table = new LinkedHashMap<>();
        for (String teamName : group.teamNames()) {
            table.put(teamName, new StandingAccumulator(teamFor(teamName)));
        }

        for (CalendarMatch match : group.matches()) {
            MatchScore score = match.score();
            if (score != null) {
                StandingAccumulator home = table.get(match.home());
                StandingAccumulator away = table.get(match.away());
                if (home != null && away != null) {
                    home.addMatch(score.homeGoals(), score.awayGoals());
                    away.addMatch(score.awayGoals(), score.homeGoals());
                }
            }
        }

        List<StandingRow> rows = new ArrayList<>();
        for (StandingAccumulator standing : table.values()) {
            rows.add(standing.toRow());
        }
        rows.sort(Comparator
                .comparingInt(StandingRow::points).reversed()
                .thenComparing(Comparator.comparingInt(StandingRow::goalDifference).reversed())
                .thenComparing(Comparator.comparingInt(StandingRow::goalsFor).reversed())
                .thenComparingInt(row -> standingPriority(row.team().name())));
        return rows.toArray(new StandingRow[0]);
    }

    private int standingPriority(String teamName) {
        return switch (teamName) {
            case "Mexico" -> 1;
            case "South Korea" -> 2;
            case "Czech Republic" -> 3;
            case "South Africa" -> 4;
            case "Canada" -> 5;
            case "Switzerland" -> 6;
            case "Bosnia and Herzegovina" -> 7;
            case "Qatar" -> 8;
            case "Scotland" -> 9;
            case "Morocco" -> 10;
            case "Brazil" -> 11;
            case "Haiti" -> 12;
            case "United States" -> 13;
            case "Australia" -> 14;
            case "Turkey" -> 15;
            case "Paraguay" -> 16;
            case "Germany" -> 17;
            case "Ivory Coast" -> 18;
            case "Ecuador" -> 19;
            case "Curacao" -> 20;
            case "Sweden" -> 21;
            case "Netherlands" -> 22;
            case "Japan" -> 23;
            case "Tunisia" -> 24;
            case "Belgium" -> 25;
            case "Egypt" -> 26;
            case "Iran" -> 27;
            case "New Zealand" -> 28;
            case "Uruguay" -> 29;
            case "Saudi Arabia" -> 30;
            case "Spain" -> 31;
            case "Cape Verde" -> 32;
            case "Norway" -> 33;
            case "France" -> 34;
            case "Senegal" -> 35;
            case "Iraq" -> 36;
            case "Argentina" -> 37;
            case "Austria" -> 38;
            case "Jordan" -> 39;
            case "Algeria" -> 40;
            case "Colombia" -> 41;
            case "Portugal" -> 42;
            case "DR Congo" -> 43;
            case "Uzbekistan" -> 44;
            case "England" -> 45;
            case "Ghana" -> 46;
            case "Panama" -> 47;
            case "Croatia" -> 48;
            default -> 1000;
        };
    }

    private WorldCupTeam teamFor(String name) {
        WorldCupTeam team = WorldCupData.findByName(name);
        if (team != null) {
            return team;
        }
        String code = name.length() >= 3 ? name.substring(0, 3).toUpperCase(Locale.ENGLISH) : name.toUpperCase(Locale.ENGLISH);
        return new WorldCupTeam(name, code, "TBD", GROUP_TITLE, AppTheme.MUTED);
    }

    private String signedNumber(int value) {
        if (value > 0) {
            return "+" + value;
        }
        return String.valueOf(value);
    }

    private JPanel buildOverview() {
        JPanel overview = panel(AppTheme.BACKGROUND, 1, 4, new Insets(0, 0, 0, 0), 18, 0);
        String[][] cards = {
                {"Selecao", selectedTeam.code(), selectedTeam.name() + " (" + selectedTeam.confederation() + ") selecionada na barra superior."},
                {"Equipas", "48", "Selecoes qualificadas para o Mundial FIFA 2026."},
                {"Jogos", "104", "Formato expandido com fase de grupos e eliminatorias."},
                {"Sedes", "16", "Estadios distribuidos por Canada, Mexico e Estados Unidos."}
        };

        for (int i = 0; i < cards.length; i++) {
            overview.add(summaryCard(null, cards[i][0], cards[i][1], cards[i][2]), constraints(0, i, 1, 1, GridConstraints.FILL_BOTH));
        }
        return overview;
    }

    private JPanel buildModuleGrid() {
        JPanel grid = panel(AppTheme.BACKGROUND, 2, 3, new Insets(0, 0, 0, 0), 18, 18);
        Object[][] modules = {
                {AppIcons.module("FA", new Color(0x1D5DDB)), "Fases", "Bracket do torneio", "Oitavos, quartos, semifinais, final e disputa de terceiro lugar."},
                {AppIcons.module("CAL", new Color(0x61D394)), "Calendario", "104 jogos", "Datas, estadios, cidades anfitrias e equipas em cada partida."},
                {AppIcons.module("EQ", new Color(0xF5C867)), "Equipas", "48 selecoes", "Plantel, grupo, confederacao e historico de cada participante."},
                {AppIcons.module("TKT", new Color(0xEF6F6C)), "Bilhetes", "Compra", "Reserva, quantidade, termos e bilhetes adquiridos pelo utilizador."},
                {AppIcons.module("EST", new Color(0x7CA7FF)), "Estadios", "16 sedes", "Informacao dos estadios e das cidades do Mundial 2026."},
                {AppIcons.module("VAR", new Color(0xA985FF)), "Arbitragem", "Equipas VAR", "Arbitros principais, assistentes e equipa VAR por jogo."}
        };

        for (int i = 0; i < modules.length; i++) {
            grid.add(summaryCard((javax.swing.Icon) modules[i][0], (String) modules[i][1], (String) modules[i][2], (String) modules[i][3]), constraints(i / 3, i % 3, 1, 1, GridConstraints.FILL_BOTH));
        }
        return grid;
    }

    private JPanel summaryCard(javax.swing.Icon icon, String title, String value, String description) {
        JPanel card = panel(AppTheme.PANEL_SOFT, 1, icon == null ? 1 : 2, new Insets(16, 16, 16, 16), 14, 0);
        card.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 20)));

        if (icon != null) {
            JLabel image = new JLabel(icon);
            card.add(image, constraints(0, 0, 1, 1, GridConstraints.FILL_NONE));
        }

        JPanel text = panel(AppTheme.PANEL_SOFT, 3, 1, new Insets(0, 0, 0, 0), 0, 7);
        text.add(label(title.toUpperCase(), AppTheme.MUTED, AppTheme.BODY_BOLD_FONT), constraints(0, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
        text.add(label(value, AppTheme.TEXT, new Font("Inter", Font.BOLD, 20)), constraints(1, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
        text.add(label("<html><body style='width: 210px'>" + description + "</body></html>", AppTheme.MUTED, AppTheme.BODY_FONT), constraints(2, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
        card.add(text, constraints(0, icon == null ? 0 : 1, 1, 1, GridConstraints.FILL_BOTH));
        return card;
    }

    private JScrollPane scrollPane(JPanel content) {
        JScrollPane scrollPane = new JScrollPane(content);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(AppTheme.BACKGROUND);
        return scrollPane;
    }

    private JButton menuButton() {
        JButton button = new HeaderMenuButton();
        button.setUI(new BasicButtonUI());
        button.setIcon(AppIcons.menu());
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(0x10245A));
        button.setPreferredSize(new Dimension(62, 58));
        button.setMinimumSize(new Dimension(54, 50));
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }

    private JButton primaryButton(String text, Runnable action) {
        JButton button = actionButton(text, AppTheme.ACCENT, new Color(0x08233C));
        button.addActionListener(event -> action.run());
        return button;
    }

    private JButton secondaryButton(String text, Runnable action) {
        JButton button = actionButton(text, AppTheme.PANEL_SOFT, AppTheme.TEXT);
        button.addActionListener(event -> action.run());
        return button;
    }

    private JButton actionButton(String text, Color background, Color foreground) {
        JButton button = new JButton(text);
        button.setBackground(background);
        button.setForeground(foreground);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }

    private JPanel whitePanel(int rows, int cols, Insets insets, int hGap, int vGap) {
        JPanel panel = panel(Color.WHITE, rows, cols, insets, hGap, vGap);
        panel.setPreferredSize(new Dimension(180, 40));
        return panel;
    }

    private JPanel panel(Color background, int rows, int cols, Insets insets, int hGap, int vGap) {
        JPanel panel = new JPanel();
        panel.setOpaque(true);
        panel.setBackground(background);
        panel.setLayout(new GridLayoutManager(rows, cols, insets, hGap, vGap));
        return panel;
    }

    private JPanel roundedPanel(Color background, int rows, int cols, Insets insets, int hGap, int vGap, int radius) {
        JPanel panel = new RoundedPanel(background, radius);
        panel.setLayout(new GridLayoutManager(rows, cols, insets, hGap, vGap));
        return panel;
    }

    private JLabel label(String text, Color color, Font font) {
        JLabel label = new JLabel(text);
        label.setForeground(color);
        label.setFont(font);
        label.setVerticalAlignment(SwingConstants.CENTER);
        return label;
    }

    private GridConstraints constraints(int row, int col, int rowSpan, int colSpan, int fill) {
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

    private static CalendarGroup group(String name, CalendarMatch... matches) {
        return new CalendarGroup(name, matches);
    }

    private static CalendarMatch match(String date, String time, String teams, String venue, String city, String zoneId) {
        return match(date, time, teams, venue, city, zoneId, null);
    }

    private static CalendarMatch result(String date, String time, String teams, String venue, String city, String zoneId, int homeGoals, int awayGoals) {
        return match(date, time, teams, venue, city, zoneId, new MatchScore(homeGoals, awayGoals));
    }

    private static CalendarMatch match(String date, String time, String teams, String venue, String city, String zoneId, MatchScore score) {
        String[] sides = teams.split(" vs ", 2);
        String home = sides.length > 0 ? sides[0] : teams;
        String away = sides.length > 1 ? sides[1] : "TBD";
        return new CalendarMatch(LocalDate.parse(date), LocalTime.parse(time), home, away, venue, city, zoneId, score);
    }

    private record CalendarGroup(String name, CalendarMatch[] matches) {
        private String[] teamNames() {
            LinkedHashSet<String> names = new LinkedHashSet<>();
            for (CalendarMatch match : matches) {
                names.add(match.home());
                names.add(match.away());
            }
            return names.toArray(new String[0]);
        }
    }

    private record CalendarMatch(LocalDate date, LocalTime time, String home, String away, String venue, String city, String zoneId, MatchScore score) {
        private ZonedDateTime kickoff() {
            return ZonedDateTime.of(date, time, ZoneId.of(zoneId));
        }
    }

    private record MatchScore(int homeGoals, int awayGoals) {
    }

    private record StandingRow(WorldCupTeam team, int played, int wins, int draws, int losses, int goalsFor, int goalsAgainst) {
        private int points() {
            return wins * 3 + draws;
        }

        private int goalDifference() {
            return goalsFor - goalsAgainst;
        }
    }

    private static final class StandingAccumulator {
        private final WorldCupTeam team;
        private int played;
        private int wins;
        private int draws;
        private int losses;
        private int goalsFor;
        private int goalsAgainst;

        private StandingAccumulator(WorldCupTeam team) {
            this.team = team;
        }

        private void addMatch(int scored, int conceded) {
            played++;
            goalsFor += scored;
            goalsAgainst += conceded;
            if (scored > conceded) {
                wins++;
            } else if (scored == conceded) {
                draws++;
            } else {
                losses++;
            }
        }

        private StandingRow toRow() {
            return new StandingRow(team, played, wins, draws, losses, goalsFor, goalsAgainst);
        }
    }

    private enum MatchStatus {
        PLAYED("Jogado", new Color(0x61D394)),
        LIVE("Em jogo", new Color(0xF8D12F)),
        NEEDS_RESULT("Resultado pendente", new Color(0xEF6F6C)),
        TO_PLAY("Por jogar", new Color(0xB9C8D8));

        private final String label;
        private final Color color;

        MatchStatus(String label, Color color) {
            this.label = label;
            this.color = color;
        }

        private String label() {
            return label;
        }

        private Color color() {
            return color;
        }
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
            g2.setColor(background);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 7, 7);
            g2.dispose();
            super.paintComponent(g);
        }
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
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(background);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
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
