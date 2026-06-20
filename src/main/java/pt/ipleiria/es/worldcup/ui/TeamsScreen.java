package pt.ipleiria.es.worldcup.ui;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TeamsScreen {
    private JPanel rootPanel2;
    private JPanel principaltabla;

    private JTable teamsTable;
    private JButton addTeamButton;

    public TeamsScreen() {
        $$$setupUI$$$();
        buildContent();
        loadData();
    }

    public JPanel getRootPanel() {
        return rootPanel2;
    }

    private void buildContent() {
        principaltabla.removeAll();
        principaltabla.setLayout(new BorderLayout());
        principaltabla.setBackground(AppTheme.BACKGROUND);

        // Título
        JPanel titlePanel = new JPanel(new GridLayout(2, 1, 0, 3));
        titlePanel.setBackground(AppTheme.BACKGROUND);

        JLabel titleLabel = new JLabel("WORLD CUP TEAMS");
        titleLabel.setFont(AppTheme.TITLE_FONT);
        titleLabel.setForeground(AppTheme.TEXT);
        titlePanel.add(titleLabel);

        JLabel subTitleLabel = new JLabel("48 SELEÇÕES CLASSIFICADAS");
        subTitleLabel.setFont(AppTheme.BODY_BOLD_FONT);
        subTitleLabel.setForeground(AppTheme.MUTED);
        titlePanel.add(subTitleLabel);

        principaltabla.add(titlePanel, BorderLayout.NORTH);

        // Contenido
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(AppTheme.BACKGROUND);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        contentPanel.add(createTablePanel(), BorderLayout.CENTER);
        contentPanel.add(createButtonPanel(), BorderLayout.SOUTH);

        principaltabla.add(contentPanel, BorderLayout.CENTER);

        principaltabla.revalidate();
        principaltabla.repaint();
    }

    private JPanel createTablePanel() {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(AppTheme.CHIP);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 16), 1),
                BorderFactory.createEmptyBorder(12, 16, 14, 16)
        ));

        // Título
        JLabel titleLabel = new JLabel("LIST OF TEAMS");
        titleLabel.setFont(new Font("Inter", Font.BOLD, 14));
        titleLabel.setForeground(new Color(0xF8D12F));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        card.add(titleLabel, BorderLayout.NORTH);

        // Columnas
        String[] columns = {"#", "COUNTRY", "CONFEDERATION", "CLUSTER", "MATCHES",
                "VICTORIES", "TIES", "DEFEATS", "POINTS"};

        // Datos de ejemplo
        Object[][] data = {
                {"1", "Spain", "UEFA", "B", "0", "0", "0", "0", "0"},
                {"2", "Portugal", "UEFA", "F", "0", "0", "0", "0", "0"},
                {"3", "France", "UEFA", "D", "0", "0", "0", "0", "0"}
        };

        // Modelo NO EDITABLE
        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        teamsTable = new JTable(model);
        teamsTable.setRowHeight(28);
        teamsTable.setFont(new Font("Inter", Font.PLAIN, 12));
        teamsTable.setBackground(AppTheme.CHIP);
        teamsTable.setForeground(AppTheme.TEXT);
        teamsTable.setGridColor(new Color(0x1A356E));
        teamsTable.setShowGrid(true);

        // Cabecera
        teamsTable.getTableHeader().setFont(new Font("Inter", Font.BOLD, 11));
        teamsTable.getTableHeader().setBackground(new Color(0x1A356E));
        teamsTable.getTableHeader().setForeground(AppTheme.TEXT);
        teamsTable.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(teamsTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 16)));
        scrollPane.getViewport().setBackground(AppTheme.CHIP);

        card.add(scrollPane, BorderLayout.CENTER);

        return card;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.setBackground(AppTheme.BACKGROUND);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        addTeamButton = new JButton("+ AÑADIR EQUIPO");
        addTeamButton.setBackground(AppTheme.ACCENT);
        addTeamButton.setForeground(new Color(0x08233C));
        addTeamButton.setFont(new Font("Inter", Font.BOLD, 12));
        addTeamButton.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        addTeamButton.setFocusPainted(false);
        addTeamButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        panel.add(addTeamButton);
        return panel;
    }

    private void loadData() {
        DefaultTableModel model = (DefaultTableModel) teamsTable.getModel();
        model.setRowCount(0);

        Object[][] data = {
                {"1", "Spain", "UEFA", "B", "0", "0", "0", "0", "0"},
                {"2", "Portugal", "UEFA", "F", "0", "0", "0", "0", "0"},
                {"3", "France", "UEFA", "D", "0", "0", "0", "0", "0"}
        };
        for (Object[] row : data) {
            model.addRow(row);
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
        rootPanel2 = new JPanel();
        rootPanel2.setLayout(new BorderLayout(0, 0));
        rootPanel2.setBackground(new Color(-15783332));
        rootPanel2.setPreferredSize(new Dimension(1100, 400));
        principaltabla = new JPanel();
        principaltabla.setLayout(new BorderLayout(0, 0));
        principaltabla.setBackground(new Color(-15783332));
        rootPanel2.add(principaltabla, BorderLayout.CENTER);
        principaltabla.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return rootPanel2;
    }
}