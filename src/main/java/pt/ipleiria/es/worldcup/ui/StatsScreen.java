package pt.ipleiria.es.worldcup.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.border.*;
import java.awt.*;

public class StatsScreen extends JPanel {

    private JTable countryTable;
    private JTable goalsTable;
    private JTable contributionTable;
    private JLabel totalItemsValue;
    private JLabel totalGoalsValue;
    private JLabel avgGoalsValue;
    private JLabel yellowCardsValue;
    private JLabel redCardsValue;
    private JComboBox<String> countrySelector;
    private JLabel dateLabel;

    public StatsScreen() {
        setLayout(new BorderLayout());
        setBackground(new Color(19, 32, 74));

        add(createTopPanel(), BorderLayout.NORTH);
        add(createMainContent(), BorderLayout.CENTER);

        loadData();
        loadCountries();
    }

    // Panel superior: FIFA® (izquierda) + Franja celeste (derecha, mismo ancho que el contenido)
    private JPanel createTopPanel() {
        JPanel top = new JPanel(new GridBagLayout());
        top.setBackground(new Color(19, 32, 74));
        top.setBorder(BorderFactory.createEmptyBorder(15, 25, 0, 25));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 0, 0);

        // Columna 0: FIFA® (ancho fijo)
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel title = new JLabel("FIFA®");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(Color.WHITE);
        top.add(title, gbc);

        // Columna 1: Espacio entre FIFA® y la franja celeste
        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        top.add(Box.createHorizontalGlue(), gbc);

        // Columna 2: Franja celeste (ancho fijo = 200 + margen)
        gbc.gridx = 2;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;

        JPanel celestePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 5));
        celestePanel.setBackground(new Color(79, 195, 247));
        celestePanel.setBorder(BorderFactory.createEmptyBorder(6, 15, 6, 15));
        celestePanel.setPreferredSize(new Dimension(825, 40));

        // Lupa
        JLabel searchIcon = new JLabel("🔍");
        searchIcon.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        searchIcon.setForeground(Color.WHITE);
        celestePanel.add(searchIcon);

        // Buscador
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

        // Selector de países
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

        // Fecha
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

    private JPanel createMainContent() {
        JPanel main = new JPanel(new BorderLayout(15, 15));
        main.setBackground(new Color(19, 32, 74));
        main.setBorder(BorderFactory.createEmptyBorder(15, 20, 20, 20));

        main.add(createMenuPanel(), BorderLayout.WEST);
        main.add(createRightPanel(), BorderLayout.CENTER);

        return main;
    }

    private JPanel createMenuPanel() {
        return FifaUiKit.sidebar("Estatísticas");
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

    private JPanel createRightPanel() {
        JPanel right = new JPanel(new BorderLayout(0, 15));
        right.setBackground(new Color(15, 42, 92));
        right.setBorder(BorderFactory.createEmptyBorder(5, 15, 15, 15));

        right.add(createStatsPanel(), BorderLayout.NORTH);
        right.add(createTablesPanel(), BorderLayout.CENTER);

        return right;
    }

    private JPanel createStatsPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 5, 15, 8));
        panel.setBackground(new Color(15, 42, 92));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60, 80, 140)),
                BorderFactory.createEmptyBorder(12, 18, 12, 18)
        ));

        String[] titles = {
                "TOTAL NUMBER OF ITEMS",
                "TOTAL GOALS SCORED",
                "AVERAGE GOALS",
                "TOTAL YELLOW CARDS",
                "TOTAL RED CARDS"
        };
        for (String t : titles) {
            JLabel label = new JLabel(t);
            label.setFont(new Font("Segoe UI", Font.PLAIN, 10));
            label.setForeground(new Color(180, 190, 220));
            label.setHorizontalAlignment(SwingConstants.CENTER);
            panel.add(label);
        }

        totalItemsValue = new JLabel("104");
        totalItemsValue.setFont(new Font("Segoe UI", Font.BOLD, 18));
        totalItemsValue.setForeground(Color.WHITE);
        totalItemsValue.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(totalItemsValue);

        totalGoalsValue = new JLabel("200");
        totalGoalsValue.setFont(new Font("Segoe UI", Font.BOLD, 18));
        totalGoalsValue.setForeground(Color.WHITE);
        totalGoalsValue.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(totalGoalsValue);

        avgGoalsValue = new JLabel("3.1");
        avgGoalsValue.setFont(new Font("Segoe UI", Font.BOLD, 18));
        avgGoalsValue.setForeground(Color.WHITE);
        avgGoalsValue.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(avgGoalsValue);

        yellowCardsValue = new JLabel("30");
        yellowCardsValue.setFont(new Font("Segoe UI", Font.BOLD, 18));
        yellowCardsValue.setForeground(Color.WHITE);
        yellowCardsValue.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(yellowCardsValue);

        redCardsValue = new JLabel("15");
        redCardsValue.setFont(new Font("Segoe UI", Font.BOLD, 18));
        redCardsValue.setForeground(Color.WHITE);
        redCardsValue.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(redCardsValue);

        return panel;
    }

    private JPanel createTablesPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 1, 0, 15));
        panel.setBackground(new Color(15, 42, 92));

        panel.add(createCountryTablePanel());
        panel.add(createContributionTablePanel());
        panel.add(createGoalsTablePanel());

        return panel;
    }

    private JPanel createCountryTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(15, 42, 92));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60, 80, 140)),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));

        JLabel title = new JLabel("COUNTRY STATISTICS");
        title.setFont(new Font("Segoe UI", Font.BOLD, 14));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        panel.add(title, BorderLayout.NORTH);

        String[] columns = {"#", "Country", "Victories", "Ties", "Defeats",
                "Goal in Favor", "Goals Against", "Balance", "Points"};
        Object[][] data = {
                {"1", "Spain", "4", "1", "0", "21", "9", "+5", "---"},
                {"2", "Portugal", "3", "2", "1", "19", "10", "+4", "---"},
                {"3", "France", "2", "3", "0", "20", "12", "+3", "---"}
        };

        DefaultTableModel model = new DefaultTableModel(data, columns);
        countryTable = new JTable(model);
        countryTable.setRowHeight(28);
        countryTable.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        countryTable.setShowGrid(true);
        countryTable.setGridColor(new Color(230, 230, 230));
        countryTable.setBackground(Color.WHITE);

        JTableHeader header = countryTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 11));
        header.setBackground(new Color(240, 240, 240));
        header.setForeground(new Color(15, 42, 92));

        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < countryTable.getColumnCount(); i++) {
            countryTable.getColumnModel().getColumn(i).setCellRenderer(center);
        }

        JScrollPane scroll = new JScrollPane(countryTable);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(60, 80, 140)));
        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createContributionTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(15, 42, 92));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60, 80, 140)),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));

        JLabel title = new JLabel("PLAYERS WITH THE GREATEST GOAL CONTRIBUTION");
        title.setFont(new Font("Segoe UI", Font.BOLD, 14));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        panel.add(title, BorderLayout.NORTH);

        String[] columns = {"#", "Player", "Goals"};
        Object[][] data = {
                {"1", "VÍTOR MACHADO FERREIRA", "23"},
                {"2", "PEDRO GONZÁLEZ LÓPEZ", "20"},
                {"3", "MICHAEL AKPOVI O OLISE", "17"}
        };

        DefaultTableModel model = new DefaultTableModel(data, columns);
        contributionTable = new JTable(model);
        contributionTable.setRowHeight(28);
        contributionTable.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        contributionTable.setShowGrid(true);
        contributionTable.setGridColor(new Color(230, 230, 230));
        contributionTable.setBackground(Color.WHITE);

        JTableHeader header = contributionTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 11));
        header.setBackground(new Color(240, 240, 240));
        header.setForeground(new Color(15, 42, 92));

        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < contributionTable.getColumnCount(); i++) {
            contributionTable.getColumnModel().getColumn(i).setCellRenderer(center);
        }

        JScrollPane scroll = new JScrollPane(contributionTable);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(60, 80, 140)));
        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createGoalsTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(15, 42, 92));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60, 80, 140)),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));

        JLabel title = new JLabel("PLAYERS WITH THE MOST GOALS");
        title.setFont(new Font("Segoe UI", Font.BOLD, 14));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        panel.add(title, BorderLayout.NORTH);

        String[] columns = {"#", "Player", "Goals"};
        Object[][] data = {
                {"1", "LIONEL ANDRÉS MESSI CUCCITTINI", "17"},
                {"2", "KILIAN SANMI MBAPPÉ LOTTIN", "17"},
                {"3", "VÍTOR MACHADO FERREIRA", "12"}
        };

        DefaultTableModel model = new DefaultTableModel(data, columns);
        goalsTable = new JTable(model);
        goalsTable.setRowHeight(28);
        goalsTable.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        goalsTable.setShowGrid(true);
        goalsTable.setGridColor(new Color(230, 230, 230));
        goalsTable.setBackground(Color.WHITE);

        JTableHeader header = goalsTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 11));
        header.setBackground(new Color(240, 240, 240));
        header.setForeground(new Color(15, 42, 92));

        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < goalsTable.getColumnCount(); i++) {
            goalsTable.getColumnModel().getColumn(i).setCellRenderer(center);
        }

        JScrollPane scroll = new JScrollPane(goalsTable);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(60, 80, 140)));
        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }

    private void loadData() {
        totalItemsValue.setText("104");
        totalGoalsValue.setText("200");
        avgGoalsValue.setText("3.1");
        yellowCardsValue.setText("30");
        redCardsValue.setText("15");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("FIFA Statistics");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1100, 750);
            frame.setLocationRelativeTo(null);
            frame.add(new StatsScreen());
            frame.setVisible(true);
        });
    }
}