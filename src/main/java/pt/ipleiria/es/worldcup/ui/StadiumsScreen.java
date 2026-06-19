package pt.ipleiria.es.worldcup.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.border.*;
import java.awt.*;

public class StadiumsScreen extends JPanel {

    private JTable stadiumsTable;
    private JComboBox<String> countrySelector;
    private JLabel dateLabel;

    public StadiumsScreen() {
        setLayout(new BorderLayout());
        setBackground(new Color(19, 32, 74));

        add(createTopPanel(), BorderLayout.NORTH);
        add(createMainContent(), BorderLayout.CENTER);

        loadCountries();
        loadStadiumsData();
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
        return FifaUiKit.sidebar("Estádios");
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

        return right;
    }

    // ==================== TÍTULO ====================
    private JPanel createTitlePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(15, 42, 92));
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        JLabel title = new JLabel("ESTÁDIOS");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        panel.add(title, BorderLayout.WEST);

        return panel;
    }

    // ==================== TABLA DE ESTÁDIOS (16 MUNDIAL 2026) ====================
    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(15, 42, 92));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60, 80, 140)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        String[] columns = {"#", "ESTÁDIO", "CIDADE", "PAÍS", "CAPACIDADE", "INAUGURAÇÃO", "PARTIDAS"};
        Object[][] data = {
                // Estados Unidos (11)
                {"1", "Atlanta Stadium", "Atlanta", "EUA", "71.000", "2017", "7"},
                {"2", "Boston Stadium", "Boston", "EUA", "65.000", "2002", "6"},
                {"3", "Dallas Stadium", "Dallas", "EUA", "80.000", "2009", "7"},
                {"4", "Houston Stadium", "Houston", "EUA", "72.000", "2002", "6"},
                {"5", "Kansas City Stadium", "Kansas City", "EUA", "76.000", "1972", "5"},
                {"6", "Los Angeles Stadium", "Los Ángeles", "EUA", "70.240", "2020", "8"},
                {"7", "Miami Stadium", "Miami", "EUA", "65.000", "1987", "6"},
                {"8", "New York New Jersey Stadium", "Nova Jersey", "EUA", "82.500", "2010", "8"},
                {"9", "Philadelphia Stadium", "Filadélfia", "EUA", "69.176", "2003", "6"},
                {"10", "San Francisco Bay Area Stadium", "San Francisco", "EUA", "68.500", "2014", "7"},
                {"11", "Seattle Stadium", "Seattle", "EUA", "72.000", "2002", "5"},
                // Canadá (2)
                {"12", "Toronto Stadium", "Toronto", "Canadá", "45.000", "2016", "5"},
                {"13", "BC Place Vancouver", "Vancouver", "Canadá", "54.500", "1983", "5"},
                // México (3)
                {"14", "Guadalajara Stadium", "Guadalajara", "México", "48.850", "2010", "6"},
                {"15", "Mexico City Stadium", "Cidade do México", "México", "87.523", "1966", "8"},
                {"16", "Monterrey Stadium", "Monterrey", "México", "53.500", "2015", "6"}
        };

        // 🔒 DESHABILITAR EDICIÓN
        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        stadiumsTable = new JTable(model);
        stadiumsTable.setRowHeight(28);
        stadiumsTable.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        stadiumsTable.setShowGrid(true);
        stadiumsTable.setGridColor(new Color(230, 230, 230));
        stadiumsTable.setBackground(Color.WHITE);

        JTableHeader header = stadiumsTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 11));
        header.setBackground(new Color(240, 240, 240));
        header.setForeground(new Color(15, 42, 92));

        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < stadiumsTable.getColumnCount(); i++) {
            stadiumsTable.getColumnModel().getColumn(i).setCellRenderer(center);
        }

        // Ajustar anchos
        stadiumsTable.getColumnModel().getColumn(0).setPreferredWidth(30);
        stadiumsTable.getColumnModel().getColumn(0).setMaxWidth(40);
        stadiumsTable.getColumnModel().getColumn(1).setPreferredWidth(180);
        stadiumsTable.getColumnModel().getColumn(2).setPreferredWidth(130);
        stadiumsTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        stadiumsTable.getColumnModel().getColumn(4).setPreferredWidth(90);
        stadiumsTable.getColumnModel().getColumn(5).setPreferredWidth(90);
        stadiumsTable.getColumnModel().getColumn(6).setPreferredWidth(70);

        JScrollPane scroll = new JScrollPane(stadiumsTable);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(60, 80, 140)));
        panel.add(scroll, BorderLayout.CENTER);

        // ==================== BOTÓN ADICIONAR ESTÁDIO ====================
        JButton addButton = new JButton("+ ADICIONAR ESTÁDIO");
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

    private void loadStadiumsData() {
        // Los datos ya se cargan en createTablePanel()
    }

    // ==================== MAIN PARA PROBAR ====================
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("FIFA Stadiums");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1100, 750);
            frame.setLocationRelativeTo(null);
            frame.add(new StadiumsScreen());
            frame.setVisible(true);
        });
    }
}