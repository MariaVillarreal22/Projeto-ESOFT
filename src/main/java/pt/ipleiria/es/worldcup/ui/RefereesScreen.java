package pt.ipleiria.es.worldcup.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class RefereesScreen {
    private JPanel rootPanel;
    private JPanel contenido;
    private JPanel botones;

    private JTable refereesTable;
    private JButton addRefereeButton;

    public RefereesScreen() {
        $$$setupUI$$$();
        buildContent();
        loadData();
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

    private void buildContent() {
        // Limpiar el panel contenido
        contenido.removeAll();
        contenido.setLayout(new BorderLayout());
        contenido.setBackground(AppTheme.BACKGROUND);

        // Título
        JPanel titlePanel = new JPanel(new GridLayout(2, 1, 0, 3));
        titlePanel.setBackground(AppTheme.BACKGROUND);

        JLabel titleLabel = new JLabel("ARBITROS");
        titleLabel.setFont(AppTheme.TITLE_FONT);
        titleLabel.setForeground(AppTheme.TEXT);
        titlePanel.add(titleLabel);

        JLabel subTitleLabel = new JLabel("LIST OF REFEREES");
        subTitleLabel.setFont(AppTheme.BODY_BOLD_FONT);
        subTitleLabel.setForeground(AppTheme.MUTED);
        titlePanel.add(subTitleLabel);

        contenido.add(titlePanel, BorderLayout.NORTH);

        // Panel de contenido
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(AppTheme.BACKGROUND);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        contentPanel.add(createTablePanel(), BorderLayout.CENTER);
        contentPanel.add(createStatsPanel(), BorderLayout.SOUTH);

        contenido.add(contentPanel, BorderLayout.CENTER);

        contenido.revalidate();
        contenido.repaint();
    }

    private JPanel createTablePanel() {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(AppTheme.CHIP);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 16), 1),
                BorderFactory.createEmptyBorder(12, 16, 14, 16)
        ));

        // Título
        JLabel titleLabel = new JLabel("REFEREES LIST");
        titleLabel.setFont(new Font("Inter", Font.BOLD, 14));
        titleLabel.setForeground(new Color(0xF8D12F));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        card.add(titleLabel, BorderLayout.NORTH);

        // Columnas
        String[] columns = {"#", "NAME", "COUNTRY", "ROLE", "STATE"};

        // Datos de ejemplo
        Object[][] data = {
                {"1", "Szymon Marciniak", "Poland", "Center Referee", "BUSY"},
                {"2", "Michael Oliver", "England", "Center Referee", "REST"},
                {"3", "Tori Penso", "United States", "Center Referee", "ASSET"},
                {"4", "Bruno Boschilia", "Brazil", "Assistant Referee", "BUSY"},
                {"5", "Taleb Salim Al-Marri", "Qatar", "Assistant Referee", "ASSET"},
                {"6", "Marco Di Bello", "Italy", "VAR", "BUSY"},
                {"7", "Khamis Al-Marri", "Qatar", "VAR", "ASSET"},
                {"8", "Jarred Gillett", "Australia", "VAR", "ASSET"}
        };

        // Modelo NO EDITABLE
        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        refereesTable = new JTable(model);
        refereesTable.setRowHeight(28);
        refereesTable.setFont(new Font("Inter", Font.PLAIN, 12));
        refereesTable.setBackground(AppTheme.CHIP);
        refereesTable.setForeground(AppTheme.TEXT);
        refereesTable.setGridColor(new Color(0x1A356E));
        refereesTable.setShowGrid(true);

        // Cabecera
        refereesTable.getTableHeader().setFont(new Font("Inter", Font.BOLD, 11));
        refereesTable.getTableHeader().setBackground(new Color(0x1A356E));
        refereesTable.getTableHeader().setForeground(AppTheme.TEXT);
        refereesTable.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(refereesTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 16)));
        scrollPane.getViewport().setBackground(AppTheme.CHIP);

        card.add(scrollPane, BorderLayout.CENTER);

        // Botón
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(AppTheme.CHIP);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        addRefereeButton = new JButton("+ Añadir árbitro");
        addRefereeButton.setBackground(AppTheme.ACCENT);
        addRefereeButton.setForeground(new Color(0x08233C));
        addRefereeButton.setFont(new Font("Inter", Font.BOLD, 12));
        addRefereeButton.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        addRefereeButton.setFocusPainted(false);
        addRefereeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        buttonPanel.add(addRefereeButton);
        card.add(buttonPanel, BorderLayout.SOUTH);

        return card;
    }

    private JPanel createStatsPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 4, 15, 0));
        panel.setBackground(AppTheme.BACKGROUND);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        panel.add(createStatCard("TOTAL", "18"));
        panel.add(createStatCard("DISPONIBLES", "14"));
        panel.add(createStatCard("PARTIDOS ASSIGNADOS", "32"));
        panel.add(createStatCard("PAÍSES", "9"));

        return panel;
    }

    private JPanel createStatCard(String label, String value) {
        JPanel card = new JPanel(new GridLayout(2, 1, 0, 4));
        card.setBackground(AppTheme.PANEL_SOFT);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 16), 1),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        JLabel titleLabel = new JLabel(label);
        titleLabel.setFont(AppTheme.BODY_BOLD_FONT);
        titleLabel.setForeground(AppTheme.MUTED);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(titleLabel);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Inter", Font.BOLD, 20));
        valueLabel.setForeground(AppTheme.TEXT);
        valueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(valueLabel);

        return card;
    }

    private void loadData() {
        DefaultTableModel model = (DefaultTableModel) refereesTable.getModel();
        model.setRowCount(0);

        Object[][] data = {
                {"1", "Szymon Marciniak", "Poland", "Center Referee", "BUSY"},
                {"2", "Michael Oliver", "England", "Center Referee", "REST"},
                {"3", "Tori Penso", "United States", "Center Referee", "ASSET"},
                {"4", "Bruno Boschilia", "Brazil", "Assistant Referee", "BUSY"},
                {"5", "Taleb Salim Al-Marri", "Qatar", "Assistant Referee", "ASSET"},
                {"6", "Marco Di Bello", "Italy", "VAR", "BUSY"},
                {"7", "Khamis Al-Marri", "Qatar", "VAR", "ASSET"},
                {"8", "Jarred Gillett", "Australia", "VAR", "ASSET"}
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
        rootPanel = new JPanel();
        rootPanel.setLayout(new BorderLayout(0, 0));
        rootPanel.setBackground(new Color(-15783332));
        contenido = new JPanel();
        contenido.setLayout(new BorderLayout(0, 0));
        contenido.setBackground(new Color(-15783332));
        rootPanel.add(contenido, BorderLayout.CENTER);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return rootPanel;
    }
}