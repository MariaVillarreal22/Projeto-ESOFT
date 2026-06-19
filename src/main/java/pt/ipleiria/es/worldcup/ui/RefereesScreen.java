package pt.ipleiria.es.worldcup.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.border.*;
import java.awt.*;

public class RefereesScreen extends JPanel {

    private JTable refereesTable;
    private JComboBox<String> countrySelector;
    private JLabel dateLabel;
    private JLabel totalValue;
    private JLabel disponiblesValue;
    private JLabel partidosValue;
    private JLabel paisesValue;

    public RefereesScreen() {
        setLayout(new BorderLayout());
        setBackground(new Color(19, 32, 74));

        add(createTopPanel(), BorderLayout.NORTH);
        add(createMainContent(), BorderLayout.CENTER);

        loadCountries();
        loadRefereesData();
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
                "ARGENTINA", "BRAZIL", "URUGUAY", "ECUADOR", "COLOMBIA", "PARAGUAY",
                "MEXICO", "USA", "CANADA", "COSTA RICA", "PANAMA", "JAMAICA",
                "GERMANY", "AUSTRIA", "BELGIUM", "BOSNIA", "CROATIA", "SCOTLAND",
                "SPAIN", "FRANCE", "WALES", "ENGLAND", "NORWAY", "NETHERLANDS",
                "PORTUGAL", "CZECHIA", "SWEDEN", "SWITZERLAND",
                "ALGERIA", "CAPE VERDE", "IVORY COAST", "EGYPT", "GHANA",
                "MOROCCO", "CONGO DR", "SENEGAL", "SOUTH AFRICA", "TUNISIA",
                "SAUDI ARABIA", "AUSTRALIA", "SOUTH KOREA", "IRAQ", "IRAN",
                "JAPAN", "JORDAN", "QATAR", "UZBEKISTAN",
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
        return FifaUiKit.sidebar("Árbitros");
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
        right.add(createTablePanel(), BorderLayout.CENTER);
        right.add(createStatsPanel(), BorderLayout.SOUTH);

        return right;
    }

    // ==================== TÍTULO ====================
    private JPanel createTitlePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(15, 42, 92));
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        JLabel title = new JLabel("ARBITROS");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        panel.add(title, BorderLayout.WEST);

        return panel;
    }

    // ==================== TABLA DE ÁRBITROS ====================
    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(15, 42, 92));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60, 80, 140)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        // Subtítulo
        JLabel subTitle = new JLabel("LIST OF REFEREES");
        subTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        subTitle.setForeground(Color.WHITE);
        subTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        panel.add(subTitle, BorderLayout.NORTH);

        String[] columns = {"NAME", "COUNTRY", "ROLE", "STATE"};
        Object[][] data = {
                {"Szymon Marciniak", "Poland", "Center Referee", "BUSY"},
                {"Michael Oliver", "England", "Center Referee", "REST"},
                {"Tori Penso", "United States", "Center Referee", "ASSET"},
                {"Bruno Boschilia", "Brazil", "Assistant Referee", "BUSY"},
                {"Taleb Salim Al-Marri", "Qatar", "Assistant Referee", "ASSET"},
                {"Marco Di Bello", "Italy", "VAR", "BUSY"},
                {"Khamis Al-Marri", "Qatar", "VAR", "ASSET"},
                {"Jarred Gillett", "Australia", "VAR", "ASSET"}
        };

        DefaultTableModel model = new DefaultTableModel(data, columns);
        refereesTable = new JTable(model);
        refereesTable.setRowHeight(28);
        refereesTable.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        refereesTable.setShowGrid(true);
        refereesTable.setGridColor(new Color(230, 230, 230));
        refereesTable.setBackground(Color.WHITE);

        JTableHeader header = refereesTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 11));
        header.setBackground(new Color(240, 240, 240));
        header.setForeground(new Color(15, 42, 92));

        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < refereesTable.getColumnCount(); i++) {
            refereesTable.getColumnModel().getColumn(i).setCellRenderer(center);
        }

        // Ajustar anchos
        refereesTable.getColumnModel().getColumn(0).setPreferredWidth(180);
        refereesTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        refereesTable.getColumnModel().getColumn(2).setPreferredWidth(130);
        refereesTable.getColumnModel().getColumn(3).setPreferredWidth(80);

        JScrollPane scroll = new JScrollPane(refereesTable);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(60, 80, 140)));
        panel.add(scroll, BorderLayout.CENTER);

        // Botón Añadir
        JButton addButton = new JButton("+ Añadir árbitro");
        addButton.setBackground(new Color(79, 195, 247));
        addButton.setForeground(Color.WHITE);
        addButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        addButton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        addButton.setFocusPainted(false);
        addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(new Color(15, 42, 92));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        buttonPanel.add(addButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    // ==================== ESTADÍSTICAS INFERIORES ====================
    private JPanel createStatsPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 4, 15, 0));
        panel.setBackground(new Color(15, 42, 92));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        panel.add(createStatCard("TOTAL", "18"));
        panel.add(createStatCard("DISPONIBLES", "14"));
        panel.add(createStatCard("PARTIDOS ASSIGNADOS", "32"));
        panel.add(createStatCard("PAÍSES", "9"));

        return panel;
    }

    private JPanel createStatCard(String label, String value) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(new Color(15, 42, 92));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60, 80, 140)),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        JLabel titleLabel = new JLabel(label);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        titleLabel.setForeground(new Color(180, 190, 220));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(titleLabel, BorderLayout.NORTH);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        valueLabel.setForeground(Color.WHITE);
        valueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(valueLabel, BorderLayout.CENTER);

        return card;
    }

    private void loadRefereesData() {
        // Los datos ya se cargan en createTablePanel()
    }

    // ==================== MAIN PARA PROBAR ====================
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("FIFA Referees");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1100, 750);
            frame.setLocationRelativeTo(null);
            frame.add(new RefereesScreen());
            frame.setVisible(true);
        });
    }
}