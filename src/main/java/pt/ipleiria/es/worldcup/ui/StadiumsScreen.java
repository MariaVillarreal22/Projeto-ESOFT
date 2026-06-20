package pt.ipleiria.es.worldcup.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class StadiumsScreen {
    private JPanel rootPanel;
    private JPanel contentPanel;

    private JTable stadiumsTable;
    private JButton addStadiumButton;

    public StadiumsScreen() {
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

        JLabel titleLabel = new JLabel("ESTÁDIOS");
        titleLabel.setFont(AppTheme.TITLE_FONT);
        titleLabel.setForeground(AppTheme.TEXT);
        titlePanel.add(titleLabel);

        JLabel subTitleLabel = new JLabel("LIST OF STADIUMS");
        subTitleLabel.setFont(AppTheme.BODY_BOLD_FONT);
        subTitleLabel.setForeground(AppTheme.MUTED);
        titlePanel.add(subTitleLabel);

        rootPanel.add(titlePanel, BorderLayout.NORTH);

        // Contenido
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(AppTheme.BACKGROUND);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        contentPanel.add(createTablePanel(), BorderLayout.CENTER);

        rootPanel.add(contentPanel, BorderLayout.CENTER);

        rootPanel.revalidate();
        rootPanel.repaint();
    }

    private JPanel createTablePanel() {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(AppTheme.CHIP);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 16), 1),
                BorderFactory.createEmptyBorder(12, 16, 14, 16)
        ));

        // Título
        JLabel titleLabel = new JLabel("STADIUMS LIST");
        titleLabel.setFont(new Font("Inter", Font.BOLD, 14));
        titleLabel.setForeground(new Color(0xF8D12F));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        card.add(titleLabel, BorderLayout.NORTH);

        // Columnas
        String[] columns = {"#", "ESTÁDIO", "CIDADE", "PAÍS", "CAPACIDADE", "INAUGURAÇÃO", "PARTIDAS"};

        // Datos de ejemplo
        Object[][] data = {
                {"1", "Estádio Monumental", "Buenos Aires", "Argentina", "83.198", "1938", "7"},
                {"2", "MetLife Stadium", "Nova Jersey", "EUA", "82.500", "2010", "8"},
                {"3", "AT&T Stadium", "Arlington", "EUA", "80.000", "2009", "7"},
                {"4", "Estádio Akron", "Guadalajara", "México", "48.850", "2010", "6"},
                {"5", "BC Place", "Vancouver", "Canadá", "54.500", "1983", "5"},
                {"6", "Estádio Azteca", "Cidade do México", "México", "87.523", "1966", "8"},
                {"7", "Mercedes-Benz Stadium", "Atlanta", "EUA", "71.000", "2017", "7"},
                {"8", "Lincoln Financial Field", "Filadélfia", "EUA", "69.176", "2003", "6"}
        };

        // Modelo NO EDITABLE
        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        stadiumsTable = new JTable(model);
        stadiumsTable.setRowHeight(28);
        stadiumsTable.setFont(new Font("Inter", Font.PLAIN, 12));
        stadiumsTable.setBackground(AppTheme.CHIP);
        stadiumsTable.setForeground(AppTheme.TEXT);
        stadiumsTable.setGridColor(new Color(0x1A356E));
        stadiumsTable.setShowGrid(true);

        // Cabecera
        stadiumsTable.getTableHeader().setFont(new Font("Inter", Font.BOLD, 11));
        stadiumsTable.getTableHeader().setBackground(new Color(0x1A356E));
        stadiumsTable.getTableHeader().setForeground(AppTheme.TEXT);
        stadiumsTable.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(stadiumsTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 16)));
        scrollPane.getViewport().setBackground(AppTheme.CHIP);

        card.add(scrollPane, BorderLayout.CENTER);

        // Botón
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(AppTheme.CHIP);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        addStadiumButton = new JButton("+ ADICIONAR ESTÁDIO");
        addStadiumButton.setBackground(AppTheme.ACCENT);
        addStadiumButton.setForeground(new Color(0x08233C));
        addStadiumButton.setFont(new Font("Inter", Font.BOLD, 12));
        addStadiumButton.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        addStadiumButton.setFocusPainted(false);
        addStadiumButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        buttonPanel.add(addStadiumButton);
        card.add(buttonPanel, BorderLayout.SOUTH);

        return card;
    }

    private void loadData() {
        DefaultTableModel model = (DefaultTableModel) stadiumsTable.getModel();
        model.setRowCount(0);

        Object[][] data = {
                {"1", "Estádio Monumental", "Buenos Aires", "Argentina", "83.198", "1938", "7"},
                {"2", "MetLife Stadium", "Nova Jersey", "EUA", "82.500", "2010", "8"},
                {"3", "AT&T Stadium", "Arlington", "EUA", "80.000", "2009", "7"},
                {"4", "Estádio Akron", "Guadalajara", "México", "48.850", "2010", "6"},
                {"5", "BC Place", "Vancouver", "Canadá", "54.500", "1983", "5"},
                {"6", "Estádio Azteca", "Cidade do México", "México", "87.523", "1966", "8"},
                {"7", "Mercedes-Benz Stadium", "Atlanta", "EUA", "71.000", "2017", "7"},
                {"8", "Lincoln Financial Field", "Filadélfia", "EUA", "69.176", "2003", "6"}
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
        rootPanel.setPreferredSize(new Dimension(1100, 750));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return rootPanel;
    }
}