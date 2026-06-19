package pt.ipleiria.es.worldcup;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.border.*;
import java.awt.*;

public class TeamsScreen extends JPanel {

    private JTable teamsTable;
    private JComboBox<String> countrySelector;
    private JLabel dateLabel;

    public TeamsScreen() {
        setLayout(new BorderLayout());
        setBackground(new Color(19, 32, 74));

        add(createTopPanel(), BorderLayout.NORTH);
        add(createMainContent(), BorderLayout.CENTER);

        loadCountries();
        loadTeamsData();
    }

    // ==================== PANEL SUPERIOR ====================
    private JPanel createTopPanel() {
        JPanel top = new JPanel(new GridBagLayout());
        top.setBackground(new Color(19, 32, 74));
        top.setBorder(BorderFactory.createEmptyBorder(15, 25, 0, 25));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 0, 0);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel title = new JLabel("FIFA®");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(Color.WHITE);
        top.add(title, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        top.add(Box.createHorizontalGlue(), gbc);

        gbc.gridx = 2;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;

        JPanel celestePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 5));
        celestePanel.setBackground(new Color(79, 195, 247));
        celestePanel.setBorder(BorderFactory.createEmptyBorder(6, 15, 6, 15));
        celestePanel.setPreferredSize(new Dimension(825, 40));

        JLabel searchIcon = new JLabel("🔍");
        searchIcon.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        searchIcon.setForeground(Color.WHITE);
        celestePanel.add(searchIcon);

        JTextField searchField = new JTextField(50);
        searchField.setBackground(new Color(255, 255, 255, 200));
        searchField.setForeground(new Color(60, 60, 60));
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        searchField.setText("Search...");
        celestePanel.add(searchField);

        countrySelector = new JComboBox<>();
        countrySelector.setBackground(Color.WHITE);
        countrySelector.setForeground(new Color(19, 32, 74));
        countrySelector.setFont(new Font("Segoe UI", Font.BOLD, 12));
        countrySelector.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(4, 10, 4, 10)
        ));
        countrySelector.setPreferredSize(new Dimension(120, 30));
        celestePanel.add(countrySelector);

        dateLabel = new JLabel("JUNE 2026");
        dateLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        dateLabel.setForeground(Color.WHITE);
        celestePanel.add(dateLabel);

        top.add(celestePanel, gbc);

        return top;
    }

    private void loadCountries() {
        String[] countries = {
                // CONMEBOL
                "ARGENTINA", "BRAZIL", "URUGUAY", "ECUADOR", "COLOMBIA", "PARAGUAY",
                // CONCACAF
                "MEXICO", "USA", "CANADA", "COSTA RICA", "PANAMA", "JAMAICA",
                // UEFA
                "GERMANY", "AUSTRIA", "BELGIUM", "BOSNIA", "CROATIA", "SCOTLAND",
                "SPAIN", "FRANCE", "WALES", "ENGLAND", "NORWAY", "NETHERLANDS",
                "PORTUGAL", "CZECHIA", "SWEDEN", "SWITZERLAND",
                // CAF
                "ALGERIA", "CAPE VERDE", "IVORY COAST", "EGYPT", "GHANA",
                "MOROCCO", "CONGO DR", "SENEGAL", "SOUTH AFRICA", "TUNISIA",
                // AFC
                "SAUDI ARABIA", "AUSTRALIA", "SOUTH KOREA", "IRAQ", "IRAN",
                "JAPAN", "JORDAN", "QATAR", "UZBEKISTAN",
                // OFC
                "NEW ZEALAND"
        };
        for (String country : countries) {
            countrySelector.addItem(country);
        }
        countrySelector.setSelectedItem("ARGENTINA");
    }

    // ==================== CONTENIDO PRINCIPAL ====================
    private JPanel createMainContent() {
        JPanel main = new JPanel(new BorderLayout(15, 15));
        main.setBackground(new Color(19, 32, 74));
        main.setBorder(BorderFactory.createEmptyBorder(15, 20, 20, 20));

        main.add(createMenuPanel(), BorderLayout.WEST);
        main.add(createRightPanel(), BorderLayout.CENTER);

        return main;
    }

    // ==================== MENÚ LATERAL ====================
    private JPanel createMenuPanel() {
        JPanel menu = new JPanel();
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.setBackground(new Color(15, 42, 92));
        menu.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60, 80, 140)),
                BorderFactory.createEmptyBorder(20, 15, 20, 15)
        ));
        menu.setPreferredSize(new Dimension(200, 0));

        JLabel compTitle = new JLabel("COMPETIÇÕES");
        compTitle.setFont(new Font("Segoe UI", Font.BOLD, 13));
        compTitle.setForeground(Color.WHITE);
        compTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        menu.add(compTitle);
        menu.add(Box.createRigidArea(new Dimension(0, 5)));

        menu.add(createMenuItem("Fases"));
        menu.add(createMenuItem("Calendário"));
        menu.add(createMenuItem("Classificações"));
        menu.add(createMenuItem("Estatísticas"));
        menu.add(Box.createRigidArea(new Dimension(0, 20)));

        JLabel entityTitle = new JLabel("ENTIDADES");
        entityTitle.setFont(new Font("Segoe UI", Font.BOLD, 13));
        entityTitle.setForeground(Color.WHITE);
        entityTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        menu.add(entityTitle);
        menu.add(Box.createRigidArea(new Dimension(0, 5)));

        menu.add(createMenuItemSelected("Equipas"));
        menu.add(createMenuItem("Árbitros"));
        menu.add(createMenuItem("Estádios"));
        menu.add(Box.createRigidArea(new Dimension(0, 20)));

        JLabel ticketTitle = new JLabel("BILHETES");
        ticketTitle.setFont(new Font("Segoe UI", Font.BOLD, 13));
        ticketTitle.setForeground(Color.WHITE);
        ticketTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        menu.add(ticketTitle);
        menu.add(Box.createRigidArea(new Dimension(0, 5)));

        menu.add(createMenuItem("Comprar"));
        menu.add(createMenuItem("Ticket with purchased"));
        menu.add(Box.createRigidArea(new Dimension(0, 20)));

        JLabel hospTitle = new JLabel("HOSPITALIDADE");
        hospTitle.setFont(new Font("Segoe UI", Font.BOLD, 13));
        hospTitle.setForeground(Color.WHITE);
        hospTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        menu.add(hospTitle);
        menu.add(Box.createRigidArea(new Dimension(0, 5)));

        menu.add(createMenuItem("Hotéis"));
        menu.add(createMenuItem("Locações"));

        return menu;
    }

    private JLabel createMenuItem(String text) {
        JLabel item = new JLabel("  " + text);
        item.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        item.setForeground(new Color(180, 190, 220));
        item.setAlignmentX(Component.LEFT_ALIGNMENT);
        item.setBorder(BorderFactory.createEmptyBorder(3, 0, 3, 0));
        return item;
    }

    private JLabel createMenuItemSelected(String text) {
        JLabel item = new JLabel("  " + text);
        item.setFont(new Font("Segoe UI", Font.BOLD, 12));
        item.setForeground(Color.WHITE);
        item.setAlignmentX(Component.LEFT_ALIGNMENT);
        item.setBorder(BorderFactory.createEmptyBorder(3, 0, 3, 0));
        return item;
    }

    // ==================== PANEL DERECHO ====================
    private JPanel createRightPanel() {
        JPanel right = new JPanel(new BorderLayout(0, 15));
        right.setBackground(new Color(15, 42, 92));
        right.setBorder(BorderFactory.createEmptyBorder(5, 15, 15, 15));

        right.add(createTitlePanel(), BorderLayout.NORTH);
        right.add(createTeamsTablePanel(), BorderLayout.CENTER);

        return right;
    }

    // ==================== TÍTULO ====================
    private JPanel createTitlePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(15, 42, 92));
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        JLabel title = new JLabel("WORLD CUP TEAMS");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(Color.WHITE);
        panel.add(title, BorderLayout.WEST);

        return panel;
    }

    // ==================== TABLA DE EQUIPOS (48 MUNDIAL 2026) ====================
    private JPanel createTeamsTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(15, 42, 92));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60, 80, 140)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        String[] columns = {"#", "COUNTRY", "CONFEDERATION", "CLUSTER", "MATCHES",
                "VICTORIES", "TIES", "DEFEATS", "POINTS"};
        Object[][] data = {
                // UEFA (16)
                {"1", "Germany", "UEFA", "", "0", "0", "0", "0", "0"},
                {"2", "Austria", "UEFA", "", "0", "0", "0", "0", "0"},
                {"3", "Belgium", "UEFA", "", "0", "0", "0", "0", "0"},
                {"4", "Bosnia", "UEFA", "", "0", "0", "0", "0", "0"},
                {"5", "Croatia", "UEFA", "", "0", "0", "0", "0", "0"},
                {"6", "Scotland", "UEFA", "", "0", "0", "0", "0", "0"},
                {"7", "Spain", "UEFA", "", "0", "0", "0", "0", "0"},
                {"8", "France", "UEFA", "", "0", "0", "0", "0", "0"},
                {"9", "Wales", "UEFA", "", "0", "0", "0", "0", "0"},
                {"10", "England", "UEFA", "", "0", "0", "0", "0", "0"},
                {"11", "Norway", "UEFA", "", "0", "0", "0", "0", "0"},
                {"12", "Netherlands", "UEFA", "", "0", "0", "0", "0", "0"},
                {"13", "Portugal", "UEFA", "", "0", "0", "0", "0", "0"},
                {"14", "Czechia", "UEFA", "", "0", "0", "0", "0", "0"},
                {"15", "Sweden", "UEFA", "", "0", "0", "0", "0", "0"},
                {"16", "Switzerland", "UEFA", "", "0", "0", "0", "0", "0"},
                // CAF (10)
                {"17", "Algeria", "CAF", "", "0", "0", "0", "0", "0"},
                {"18", "Cape Verde", "CAF", "", "0", "0", "0", "0", "0"},
                {"19", "Ivory Coast", "CAF", "", "0", "0", "0", "0", "0"},
                {"20", "Egypt", "CAF", "", "0", "0", "0", "0", "0"},
                {"21", "Ghana", "CAF", "", "0", "0", "0", "0", "0"},
                {"22", "Morocco", "CAF", "C", "0", "0", "0", "0", "0"},
                {"23", "Congo DR", "CAF", "", "0", "0", "0", "0", "0"},
                {"24", "Senegal", "CAF", "", "0", "0", "0", "0", "0"},
                {"25", "South Africa", "CAF", "A", "0", "0", "0", "0", "0"},
                {"26", "Tunisia", "CAF", "F", "0", "0", "0", "0", "0"},
                // AFC (9)
                {"27", "Saudi Arabia", "AFC", "H", "0", "0", "0", "0", "0"},
                {"28", "Australia", "AFC", "D", "0", "0", "0", "0", "0"},
                {"29", "South Korea", "AFC", "A", "0", "0", "0", "0", "0"},
                {"30", "Iraq", "AFC", "", "0", "0", "0", "0", "0"},
                {"31", "Iran", "AFC", "G", "0", "0", "0", "0", "0"},
                {"32", "Japan", "AFC", "F", "0", "0", "0", "0", "0"},
                {"33", "Jordan", "AFC", "J", "0", "0", "0", "0", "0"},
                {"34", "Qatar", "AFC", "B", "0", "0", "0", "0", "0"},
                {"35", "Uzbekistan", "AFC", "K", "0", "0", "0", "0", "0"},
                // CONCACAF (6)
                {"36", "Canada", "CONCACAF", "", "0", "0", "0", "0", "0"},
                {"37", "USA", "CONCACAF", "", "0", "0", "0", "0", "0"},
                {"38", "Mexico", "CONCACAF", "", "0", "0", "0", "0", "0"},
                {"39", "Curacao", "CONCACAF", "", "0", "0", "0", "0", "0"},
                {"40", "Haiti", "CONCACAF", "", "0", "0", "0", "0", "0"},
                {"41", "Panama", "CONCACAF", "", "0", "0", "0", "0", "0"},
                // CONMEBOL (6)
                {"42", "Argentina", "CONMEBOL", "", "0", "0", "0", "0", "0"},
                {"43", "Brazil", "CONMEBOL", "", "0", "0", "0", "0", "0"},
                {"44", "Colombia", "CONMEBOL", "", "0", "0", "0", "0", "0"},
                {"45", "Ecuador", "CONMEBOL", "", "0", "0", "0", "0", "0"},
                {"46", "Paraguay", "CONMEBOL", "", "0", "0", "0", "0", "0"},
                {"47", "Uruguay", "CONMEBOL", "", "0", "0", "0", "0", "0"},
                // OFC (1)
                {"48", "New Zealand", "OFC", "", "0", "0", "0", "0", "0"}
        };

        DefaultTableModel model = new DefaultTableModel(data, columns);
        teamsTable = new JTable(model);
        teamsTable.setRowHeight(28);
        teamsTable.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        teamsTable.setShowGrid(true);
        teamsTable.setGridColor(new Color(230, 230, 230));
        teamsTable.setBackground(Color.WHITE);

        JTableHeader header = teamsTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 11));
        header.setBackground(new Color(240, 240, 240));
        header.setForeground(new Color(15, 42, 92));

        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < teamsTable.getColumnCount(); i++) {
            teamsTable.getColumnModel().getColumn(i).setCellRenderer(center);
        }

        teamsTable.getColumnModel().getColumn(0).setPreferredWidth(30);
        teamsTable.getColumnModel().getColumn(0).setMaxWidth(40);
        teamsTable.getColumnModel().getColumn(1).setPreferredWidth(130);
        teamsTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        teamsTable.getColumnModel().getColumn(3).setPreferredWidth(60);
        teamsTable.getColumnModel().getColumn(4).setPreferredWidth(60);
        teamsTable.getColumnModel().getColumn(5).setPreferredWidth(60);
        teamsTable.getColumnModel().getColumn(6).setPreferredWidth(50);
        teamsTable.getColumnModel().getColumn(7).setPreferredWidth(50);
        teamsTable.getColumnModel().getColumn(8).setPreferredWidth(50);

        JScrollPane scroll = new JScrollPane(teamsTable);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(60, 80, 140)));
        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }

    private void loadTeamsData() {
        // Los datos ya se cargan en createTeamsTablePanel()
    }

    // ==================== MAIN PARA PROBAR ====================
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("FIFA World Cup Teams");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1100, 750);
            frame.setLocationRelativeTo(null);
            frame.add(new TeamsScreen());
            frame.setVisible(true);
        });
    }
}