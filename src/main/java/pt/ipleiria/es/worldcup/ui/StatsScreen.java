package pt.ipleiria.es.worldcup.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import com.intellij.uiDesigner.core.GridConstraints;
import javax.swing.border.TitledBorder;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

public class StatsScreen {
    private JPanel rootPanel;
    private JPanel Principal;

    // Labels para las estadísticas
    private JLabel totalItemsValue;
    private JLabel totalGoalsValue;
    private JLabel avgGoalsValue;
    private JLabel yellowCardsValue;
    private JLabel redCardsValue;

    // Tablas
    private JTable countryTable;
    private JTable contributionTable;
    private JTable goalsTable;

    // Botón
    private JButton updateButton;

    public StatsScreen() {
        buildUi();
        loadData();
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

    private void buildUi() {
        rootPanel = new JPanel(new BorderLayout());
        rootPanel.setBackground(AppTheme.BACKGROUND);
        rootPanel.setBorder(BorderFactory.createEmptyBorder(10, 22, 20, 22));

        // Título
        JPanel titlePanel = UiSupport.panel(AppTheme.BACKGROUND, 2, 1, new Insets(0, 0, 0, 0), 0, 3);
        titlePanel.add(UiSupport.label("ESTATÍSTICAS", AppTheme.TEXT, AppTheme.TITLE_FONT), UiSupport.constraints(0, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
        titlePanel.add(UiSupport.label("DADOS DO MUNDIAL FIFA 2026", AppTheme.MUTED, AppTheme.BODY_BOLD_FONT), UiSupport.constraints(1, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
        rootPanel.add(titlePanel, BorderLayout.NORTH);

        // Panel de contenido principal
        JPanel contentPanel = UiSupport.panel(AppTheme.BACKGROUND, 3, 1, new Insets(10, 0, 0, 0), 0, 14);

        // 1. Tarjetas de estadísticas (fila 0)
        contentPanel.add(createStatsCardsPanel(), UiSupport.fixedHeightConstraints(0, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));

        // 2. Tablas (fila 1)
        contentPanel.add(createTablesPanel(), UiSupport.constraints(1, 0, 1, 1, GridConstraints.FILL_BOTH));

        // 3. Botón ACTUALIZAR (fila 2)
        contentPanel.add(createButtonPanel(), UiSupport.fixedHeightConstraints(2, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));

        rootPanel.add(contentPanel, BorderLayout.CENTER);
    }

    // ==================== TARJETAS DE ESTADÍSTICAS ====================
    private JPanel createStatsCardsPanel() {
        JPanel panel = UiSupport.panel(AppTheme.BACKGROUND, 1, 5, new Insets(0, 0, 0, 0), 14, 0);

        totalItemsValue = new JLabel("0");
        totalGoalsValue = new JLabel("0");
        avgGoalsValue = new JLabel("0.0");
        yellowCardsValue = new JLabel("0");
        redCardsValue = new JLabel("0");

        panel.add(createStatCard("TOTAL NUMBER OF ITEMS", totalItemsValue), UiSupport.constraints(0, 0, 1, 1, GridConstraints.FILL_BOTH));
        panel.add(createStatCard("TOTAL GOALS SCORED", totalGoalsValue), UiSupport.constraints(0, 1, 1, 1, GridConstraints.FILL_BOTH));
        panel.add(createStatCard("AVERAGE GOALS", avgGoalsValue), UiSupport.constraints(0, 2, 1, 1, GridConstraints.FILL_BOTH));
        panel.add(createStatCard("TOTAL YELLOW CARDS", yellowCardsValue), UiSupport.constraints(0, 3, 1, 1, GridConstraints.FILL_BOTH));
        panel.add(createStatCard("TOTAL RED CARDS", redCardsValue), UiSupport.constraints(0, 4, 1, 1, GridConstraints.FILL_BOTH));

        return panel;
    }

    private JPanel createStatCard(String title, JLabel valueLabel) {
        JPanel card = UiSupport.roundedPanel(AppTheme.PANEL_SOFT, 2, 1, new Insets(12, 16, 12, 16), 0, 4, 10);
        card.setPreferredSize(new Dimension(180, 80));
        card.setMinimumSize(new Dimension(150, 80));
        card.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 18)));

        valueLabel.setFont(new Font("Inter", Font.BOLD, 22));
        valueLabel.setForeground(AppTheme.TEXT);
        valueLabel.setHorizontalAlignment(JLabel.CENTER);

        card.add(UiSupport.label(title, AppTheme.MUTED, AppTheme.BODY_BOLD_FONT), UiSupport.constraints(0, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));
        card.add(valueLabel, UiSupport.constraints(1, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));

        return card;
    }

    // ==================== TABLAS ====================
    private JPanel createTablesPanel() {
        JPanel panel = UiSupport.panel(AppTheme.BACKGROUND, 3, 1, new Insets(0, 0, 0, 0), 0, 14);

        panel.add(createCountryTablePanel(), UiSupport.constraints(0, 0, 1, 1, GridConstraints.FILL_BOTH));
        panel.add(createContributionTablePanel(), UiSupport.constraints(1, 0, 1, 1, GridConstraints.FILL_BOTH));
        panel.add(createGoalsTablePanel(), UiSupport.constraints(2, 0, 1, 1, GridConstraints.FILL_BOTH));

        return panel;
    }

    private JPanel createCountryTablePanel() {
        JPanel card = UiSupport.roundedPanel(AppTheme.CHIP, 2, 1, new Insets(12, 16, 14, 16), 0, 8, 10);
        card.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 16)));

        card.add(UiSupport.label("COUNTRY STATISTICS", new Color(0xF8D12F), new Font("Inter", Font.BOLD, 14)),
                UiSupport.fixedHeightConstraints(0, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));

        String[] columns = {"#", "Country", "Victories", "Ties", "Defeats", "Goal in Favor", "Goals Against", "Balance", "Points"};
        Object[][] data = {
                {"1", "Spain", "4", "1", "0", "21", "9", "+5", "13"},
                {"2", "Portugal", "3", "2", "1", "19", "10", "+4", "11"},
                {"3", "France", "2", "3", "0", "20", "12", "+3", "9"}
        };

        countryTable = new JTable(data, columns);
        countryTable.setRowHeight(28);
        countryTable.setFont(new Font("Inter", Font.PLAIN, 12));
        countryTable.getTableHeader().setFont(new Font("Inter", Font.BOLD, 11));
        countryTable.getTableHeader().setBackground(new Color(0x1A356E));
        countryTable.getTableHeader().setForeground(AppTheme.TEXT);
        countryTable.setBackground(AppTheme.CHIP);
        countryTable.setForeground(AppTheme.TEXT);

        JScrollPane scrollPane = new JScrollPane(countryTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 16)));
        scrollPane.getViewport().setBackground(AppTheme.CHIP);

        card.add(scrollPane, UiSupport.constraints(1, 0, 1, 1, GridConstraints.FILL_BOTH));

        return card;
    }

    private JPanel createContributionTablePanel() {
        JPanel card = UiSupport.roundedPanel(AppTheme.CHIP, 2, 1, new Insets(12, 16, 14, 16), 0, 8, 10);
        card.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 16)));

        card.add(UiSupport.label("PLAYERS WITH THE GREATEST GOAL CONTRIBUTION", new Color(0xF8D12F), new Font("Inter", Font.BOLD, 14)),
                UiSupport.fixedHeightConstraints(0, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));

        String[] columns = {"#", "Player", "Goals"};
        Object[][] data = {
                {"1", "VÍTOR MACHADO FERREIRA", "23"},
                {"2", "PEDRO GONZÁLEZ LÓPEZ", "20"},
                {"3", "MICHAEL AKPOVI O OLISE", "17"}
        };

        contributionTable = new JTable(data, columns);
        contributionTable.setRowHeight(28);
        contributionTable.setFont(new Font("Inter", Font.PLAIN, 12));
        contributionTable.getTableHeader().setFont(new Font("Inter", Font.BOLD, 11));
        contributionTable.getTableHeader().setBackground(new Color(0x1A356E));
        contributionTable.getTableHeader().setForeground(AppTheme.TEXT);
        contributionTable.setBackground(AppTheme.CHIP);
        contributionTable.setForeground(AppTheme.TEXT);

        JScrollPane scrollPane = new JScrollPane(contributionTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 16)));
        scrollPane.getViewport().setBackground(AppTheme.CHIP);

        card.add(scrollPane, UiSupport.constraints(1, 0, 1, 1, GridConstraints.FILL_BOTH));

        return card;
    }

    private JPanel createGoalsTablePanel() {
        JPanel card = UiSupport.roundedPanel(AppTheme.CHIP, 2, 1, new Insets(12, 16, 14, 16), 0, 8, 10);
        card.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 16)));

        card.add(UiSupport.label("PLAYERS WITH THE MOST GOALS", new Color(0xF8D12F), new Font("Inter", Font.BOLD, 14)),
                UiSupport.fixedHeightConstraints(0, 0, 1, 1, GridConstraints.FILL_HORIZONTAL));

        String[] columns = {"#", "Player", "Goals"};
        Object[][] data = {
                {"1", "LIONEL ANDRÉS MESSI CUCCITTINI", "17"},
                {"2", "KILIAN SANMI MBAPPÉ LOTTIN", "17"},
                {"3", "VÍTOR MACHADO FERREIRA", "12"}
        };

        goalsTable = new JTable(data, columns);
        goalsTable.setRowHeight(28);
        goalsTable.setFont(new Font("Inter", Font.PLAIN, 12));
        goalsTable.getTableHeader().setFont(new Font("Inter", Font.BOLD, 11));
        goalsTable.getTableHeader().setBackground(new Color(0x1A356E));
        goalsTable.getTableHeader().setForeground(AppTheme.TEXT);
        goalsTable.setBackground(AppTheme.CHIP);
        goalsTable.setForeground(AppTheme.TEXT);

        JScrollPane scrollPane = new JScrollPane(goalsTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 16)));
        scrollPane.getViewport().setBackground(AppTheme.CHIP);

        card.add(scrollPane, UiSupport.constraints(1, 0, 1, 1, GridConstraints.FILL_BOTH));

        return card;
    }

    // ==================== BOTÓN ACTUALIZAR ====================
    private JPanel createButtonPanel() {
        JPanel panel = UiSupport.panel(AppTheme.BACKGROUND, 1, 1, new Insets(0, 0, 0, 0), 0, 0);

        updateButton = new JButton("ACTUALIZAR");
        updateButton.setBackground(AppTheme.ACCENT);
        updateButton.setForeground(new Color(0x08233C));
        updateButton.setFont(new Font("Inter", Font.BOLD, 12));
        updateButton.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        updateButton.setFocusPainted(false);
        updateButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        updateButton.addActionListener(e -> loadData());

        JPanel buttonWrapper = UiSupport.panel(AppTheme.BACKGROUND, 1, 1, new Insets(0, 0, 0, 0), 0, 0);
        buttonWrapper.add(updateButton, UiSupport.constraints(0, 0, 1, 1, GridConstraints.FILL_NONE));
        panel.add(buttonWrapper, UiSupport.constraints(0, 0, 1, 1, GridConstraints.ANCHOR_EAST));

        return panel;
    }

    // ==================== CARGA DE DATOS ====================
    private void loadData() {
        // Datos de ejemplo (después se cargarán desde DataManager)
        totalItemsValue.setText("6");
        totalGoalsValue.setText("200");
        avgGoalsValue.setText("3.1");
        yellowCardsValue.setText("30");
        redCardsValue.setText("15");

        // Recargar tablas con los mismos datos (por ahora)
        loadCountryTable();
        loadContributionTable();
        loadGoalsTable();
    }

    private void loadCountryTable() {
        String[] columns = {"#", "Country", "Victories", "Ties", "Defeats", "Goal in Favor", "Goals Against", "Balance", "Points"};
        Object[][] data = {
                {"1", "Spain", "4", "1", "0", "21", "9", "+5", "13"},
                {"2", "Portugal", "3", "2", "1", "19", "10", "+4", "11"},
                {"3", "France", "2", "3", "0", "20", "12", "+3", "9"}
        };
        countryTable.setBackground(new Color(0x0F3D6E));
        countryTable.setForeground(AppTheme.TEXT);
        countryTable.setGridColor(new Color(0x1A356E));
        countryTable.setShowGrid(true);
        countryTable.setRowHeight(28);
    }

    private void loadContributionTable() {
        String[] columns = {"#", "Player", "Goals"};
        Object[][] data = {
                {"1", "VÍTOR MACHADO FERREIRA", "23"},
                {"2", "PEDRO GONZÁLEZ LÓPEZ", "20"},
                {"3", "MICHAEL AKPOVI O OLISE", "17"}
        };
        contributionTable.setBackground(new Color(0x0F3D6E));
        contributionTable.setForeground(AppTheme.TEXT);
        contributionTable.setGridColor(new Color(0x1A356E));
        contributionTable.setShowGrid(true);
        contributionTable.setRowHeight(28);
    }

    private void loadGoalsTable() {
        String[] columns = {"#", "Player", "Goals"};
        Object[][] data = {
                {"1", "LIONEL ANDRÉS MESSI CUCCITTINI", "17"},
                {"2", "KILIAN SANMI MBAPPÉ LOTTIN", "17"},
                {"3", "VÍTOR MACHADO FERREIRA", "12"}
        };
        goalsTable.setBackground(new Color(0x0F3D6E));
        goalsTable.setForeground(AppTheme.TEXT);
        goalsTable.setGridColor(new Color(0x1A356E));
        goalsTable.setShowGrid(true);
        goalsTable.setRowHeight(28);
    }

    {

        $$$setupUI$$$();
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
        rootPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(10, 22, 20, 22), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        Principal = new JPanel();
        Principal.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        rootPanel.add(Principal, BorderLayout.CENTER);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.setBackground(new Color(-15783332));
        Principal.add(panel1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel2.setBackground(new Color(-15783332));
        Principal.add(panel2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel3.setBackground(new Color(-15783332));
        Principal.add(panel3, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return rootPanel;
    }
}