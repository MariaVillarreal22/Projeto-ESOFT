package pt.ipleiria.es.worldcup.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class StatsScreen {
    private JPanel rootPanel;
    private JPanel Principal;

    private JLabel totalItemsValue;
    private JLabel totalGoalsValue;
    private JLabel avgGoalsValue;
    private JLabel yellowCardsValue;
    private JLabel redCardsValue;

    private JTable countryTable;
    private JTable contributionTable;
    private JTable goalsTable;

    private JButton updateButton;

    public StatsScreen() {
        $$$setupUI$$$();
        buildContent();
        loadData();
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

    private void buildContent() {
        rootPanel.removeAll();
        rootPanel.setLayout(new BorderLayout());
        rootPanel.setBackground(AppTheme.BACKGROUND);
        rootPanel.setBorder(BorderFactory.createEmptyBorder(10, 22, 20, 22));

        // Título
        JPanel titlePanel = new JPanel(new GridLayout(2, 1, 0, 3));
        titlePanel.setBackground(AppTheme.BACKGROUND);

        JLabel titleLabel = new JLabel("ESTATÍSTICAS");
        titleLabel.setFont(AppTheme.TITLE_FONT);
        titleLabel.setForeground(AppTheme.TEXT);
        titlePanel.add(titleLabel);

        JLabel subTitleLabel = new JLabel("DADOS DO MUNDIAL FIFA 2026");
        subTitleLabel.setFont(AppTheme.BODY_BOLD_FONT);
        subTitleLabel.setForeground(AppTheme.MUTED);
        titlePanel.add(subTitleLabel);

        rootPanel.add(titlePanel, BorderLayout.NORTH);

        // Contenido
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(AppTheme.BACKGROUND);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        contentPanel.add(createStatsCardsPanel(), BorderLayout.NORTH);
        contentPanel.add(createTablesPanel(), BorderLayout.CENTER);

        rootPanel.add(contentPanel, BorderLayout.CENTER);

        rootPanel.revalidate();
        rootPanel.repaint();
    }

    private JPanel createStatsCardsPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 5, 14, 0));
        panel.setBackground(AppTheme.BACKGROUND);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 14, 0));

        totalItemsValue = new JLabel("0");
        totalGoalsValue = new JLabel("0");
        avgGoalsValue = new JLabel("0.0");
        yellowCardsValue = new JLabel("0");
        redCardsValue = new JLabel("0");

        panel.add(createStatCard("TOTAL NUMBER OF ITEMS", totalItemsValue));
        panel.add(createStatCard("TOTAL GOALS SCORED", totalGoalsValue));
        panel.add(createStatCard("AVERAGE GOALS", avgGoalsValue));
        panel.add(createStatCard("TOTAL YELLOW CARDS", yellowCardsValue));
        panel.add(createStatCard("TOTAL RED CARDS", redCardsValue));

        return panel;
    }

    private JPanel createStatCard(String title, JLabel valueLabel) {
        JPanel card = new JPanel(new GridLayout(2, 1, 0, 4));
        card.setBackground(AppTheme.PANEL_SOFT);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 16), 1),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(AppTheme.BODY_BOLD_FONT);
        titleLabel.setForeground(AppTheme.MUTED);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(titleLabel);

        valueLabel.setFont(new Font("Inter", Font.BOLD, 20));
        valueLabel.setForeground(AppTheme.TEXT);
        valueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(valueLabel);

        return card;
    }

    private JPanel createTablesPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 1, 0, 14));
        panel.setBackground(AppTheme.BACKGROUND);

        panel.add(createCountryTablePanel());
        panel.add(createContributionTablePanel());
        panel.add(createGoalsTablePanel());

        // Botón ACTUALIZAR
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(AppTheme.BACKGROUND);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        updateButton = new JButton("ACTUALIZAR");
        updateButton.setBackground(AppTheme.ACCENT);
        updateButton.setForeground(new Color(0x08233C));
        updateButton.setFont(new Font("Inter", Font.BOLD, 12));
        updateButton.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        updateButton.setFocusPainted(false);
        updateButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        updateButton.addActionListener(e -> loadData());

        buttonPanel.add(updateButton);

        JPanel container = new JPanel(new BorderLayout());
        container.setBackground(AppTheme.BACKGROUND);
        container.add(panel, BorderLayout.CENTER);
        container.add(buttonPanel, BorderLayout.SOUTH);

        return container;
    }

    private JPanel createCountryTablePanel() {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(AppTheme.CHIP);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 16), 1),
                BorderFactory.createEmptyBorder(12, 16, 14, 16)
        ));

        JLabel titleLabel = new JLabel("COUNTRY STATISTICS");
        titleLabel.setFont(new Font("Inter", Font.BOLD, 14));
        titleLabel.setForeground(new Color(0xF8D12F));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        card.add(titleLabel, BorderLayout.NORTH);

        String[] columns = {"#", "Country", "Victories", "Ties", "Defeats",
                "Goal in Favor", "Goals Against", "Balance", "Points"};

        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        countryTable = new JTable(model);
        countryTable.setRowHeight(28);
        countryTable.setFont(new Font("Inter", Font.PLAIN, 12));
        countryTable.setBackground(AppTheme.CHIP);
        countryTable.setForeground(AppTheme.TEXT);
        countryTable.setGridColor(new Color(0x1A356E));
        countryTable.setShowGrid(true);

        countryTable.getTableHeader().setFont(new Font("Inter", Font.BOLD, 11));
        countryTable.getTableHeader().setBackground(new Color(0x2E7D32));
        countryTable.getTableHeader().setForeground(Color.WHITE);
        countryTable.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(countryTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 16)));
        scrollPane.getViewport().setBackground(AppTheme.CHIP);

        card.add(scrollPane, BorderLayout.CENTER);
        return card;
    }

    private JPanel createContributionTablePanel() {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(AppTheme.CHIP);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 16), 1),
                BorderFactory.createEmptyBorder(12, 16, 14, 16)
        ));

        JLabel titleLabel = new JLabel("PLAYERS WITH THE GREATEST GOAL CONTRIBUTION");
        titleLabel.setFont(new Font("Inter", Font.BOLD, 14));
        titleLabel.setForeground(new Color(0xF8D12F));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        card.add(titleLabel, BorderLayout.NORTH);

        String[] columns = {"#", "Player", "Goals"};

        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        contributionTable = new JTable(model);
        contributionTable.setRowHeight(28);
        contributionTable.setFont(new Font("Inter", Font.PLAIN, 12));
        contributionTable.setBackground(AppTheme.CHIP);
        contributionTable.setForeground(AppTheme.TEXT);
        contributionTable.setGridColor(new Color(0x1A356E));
        contributionTable.setShowGrid(true);

        contributionTable.getTableHeader().setFont(new Font("Inter", Font.BOLD, 11));
        contributionTable.getTableHeader().setBackground(new Color(0x2E7D32));
        contributionTable.getTableHeader().setForeground(Color.WHITE);
        contributionTable.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(contributionTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 16)));
        scrollPane.getViewport().setBackground(AppTheme.CHIP);

        card.add(scrollPane, BorderLayout.CENTER);
        return card;
    }

    private JPanel createGoalsTablePanel() {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(AppTheme.CHIP);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 16), 1),
                BorderFactory.createEmptyBorder(12, 16, 14, 16)
        ));

        JLabel titleLabel = new JLabel("PLAYERS WITH THE MOST GOALS");
        titleLabel.setFont(new Font("Inter", Font.BOLD, 14));
        titleLabel.setForeground(new Color(0xF8D12F));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        card.add(titleLabel, BorderLayout.NORTH);

        String[] columns = {"#", "Player", "Goals"};

        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        goalsTable = new JTable(model);
        goalsTable.setRowHeight(28);
        goalsTable.setFont(new Font("Inter", Font.PLAIN, 12));
        goalsTable.setBackground(AppTheme.CHIP);
        goalsTable.setForeground(AppTheme.TEXT);
        goalsTable.setGridColor(new Color(0x1A356E));
        goalsTable.setShowGrid(true);

        goalsTable.getTableHeader().setFont(new Font("Inter", Font.BOLD, 11));
        goalsTable.getTableHeader().setBackground(new Color(0x2E7D32));
        goalsTable.getTableHeader().setForeground(Color.WHITE);
        goalsTable.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(goalsTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 16)));
        scrollPane.getViewport().setBackground(AppTheme.CHIP);

        card.add(scrollPane, BorderLayout.CENTER);
        return card;
    }

    private void loadData() {
        DataManager data = DataManager.getInstance();
        List<Team> teams = data.getTeams();
        List<Player> players = data.getAllPlayers();

        // Actualizar estadísticas
        totalItemsValue.setText(String.valueOf(teams.size()));
        totalGoalsValue.setText(String.valueOf(data.getTotalGoals()));
        avgGoalsValue.setText(String.format("%.1f", data.getAverageGoals()));
        yellowCardsValue.setText(String.valueOf(data.getTotalYellowCards()));
        redCardsValue.setText(String.valueOf(data.getTotalRedCards()));

        // Actualizar tablas
        updateCountryTable(teams);
        updateContributionTable(players);
        updateGoalsTable(players);
    }

    private void updateCountryTable(List<Team> teams) {
        DefaultTableModel model = (DefaultTableModel) countryTable.getModel();
        model.setRowCount(0);

        teams.sort((t1, t2) -> Integer.compare(t2.getPoints(), t1.getPoints()));

        int index = 1;
        for (Team team : teams) {
            model.addRow(new Object[]{
                    index++,
                    team.getName(),
                    team.getVictories(),
                    team.getTies(),
                    team.getDefeats(),
                    team.getGoalsFor(),
                    team.getGoalsAgainst(),
                    team.getBalance(),
                    team.getPoints()
            });
        }
    }

    private void updateContributionTable(List<Player> players) {
        DefaultTableModel model = (DefaultTableModel) contributionTable.getModel();
        model.setRowCount(0);

        players.sort((p1, p2) -> Integer.compare(p2.getGoals(), p1.getGoals()));

        int limit = Math.min(10, players.size());
        for (int i = 0; i < limit; i++) {
            Player p = players.get(i);
            model.addRow(new Object[]{
                    i + 1,
                    p.getName(),
                    p.getGoals()
            });
        }
    }

    private void updateGoalsTable(List<Player> players) {
        DefaultTableModel model = (DefaultTableModel) goalsTable.getModel();
        model.setRowCount(0);

        players.sort((p1, p2) -> Integer.compare(p2.getGoals(), p1.getGoals()));

        int limit = Math.min(10, players.size());
        for (int i = 0; i < limit; i++) {
            Player p = players.get(i);
            model.addRow(new Object[]{
                    i + 1,
                    p.getName(),
                    p.getGoals()
            });
        }
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        rootPanel = new JPanel();
        rootPanel.setLayout(new BorderLayout(0, 0));
        rootPanel.setBackground(new Color(-15783332));
        rootPanel.setPreferredSize(new Dimension(1100, 750));
        Principal = new JPanel();
        Principal.setLayout(new BorderLayout(0, 0));
        Principal.setBackground(new Color(-15783332));
        rootPanel.add(Principal, BorderLayout.CENTER);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return rootPanel;
    }
}