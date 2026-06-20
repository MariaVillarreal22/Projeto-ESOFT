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

        addStadiumButton.addActionListener(e -> showAddStadiumDialog());

        buttonPanel.add(addStadiumButton);
        card.add(buttonPanel, BorderLayout.SOUTH);

        return card;
    }

    private void showAddStadiumDialog() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Adicionar Estádio");
        dialog.setModal(true);
        dialog.setSize(450, 350);
        dialog.setLocationRelativeTo(rootPanel);
        dialog.setLayout(new GridBagLayout());
        dialog.getContentPane().setBackground(new Color(15, 42, 92));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Nombre
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel nameLabel = new JLabel("Nombre del Estádio:");
        nameLabel.setForeground(Color.WHITE);
        dialog.add(nameLabel, gbc);
        gbc.gridx = 1;
        JTextField nameField = new JTextField(15);
        dialog.add(nameField, gbc);

        // Ciudad
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel cityLabel = new JLabel("Cidade:");
        cityLabel.setForeground(Color.WHITE);
        dialog.add(cityLabel, gbc);
        gbc.gridx = 1;
        JTextField cityField = new JTextField(15);
        dialog.add(cityField, gbc);

        // País
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel countryLabel = new JLabel("País:");
        countryLabel.setForeground(Color.WHITE);
        dialog.add(countryLabel, gbc);
        gbc.gridx = 1;
        JTextField countryField = new JTextField(15);
        dialog.add(countryField, gbc);

        // Capacidad
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel capacityLabel = new JLabel("Capacidade:");
        capacityLabel.setForeground(Color.WHITE);
        dialog.add(capacityLabel, gbc);
        gbc.gridx = 1;
        JTextField capacityField = new JTextField(10);
        dialog.add(capacityField, gbc);

        // Inauguración
        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel yearLabel = new JLabel("Ano Inauguração:");
        yearLabel.setForeground(Color.WHITE);
        dialog.add(yearLabel, gbc);
        gbc.gridx = 1;
        JTextField yearField = new JTextField(10);
        dialog.add(yearField, gbc);

        // Partidos
        gbc.gridx = 0;
        gbc.gridy = 5;
        JLabel matchesLabel = new JLabel("Partidos:");
        matchesLabel.setForeground(Color.WHITE);
        dialog.add(matchesLabel, gbc);
        gbc.gridx = 1;
        JTextField matchesField = new JTextField(5);
        dialog.add(matchesField, gbc);

        // Botones
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(15, 42, 92));

        JButton saveButton = new JButton("Guardar");
        saveButton.setBackground(new Color(79, 195, 247));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFont(new Font("Inter", Font.BOLD, 12));
        saveButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String city = cityField.getText().trim();
            String country = countryField.getText().trim();
            String capacity = capacityField.getText().trim();
            String year = yearField.getText().trim();
            String matches = matchesField.getText().trim();

            if (name.isEmpty() || city.isEmpty() || country.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Nombre, Cidade e País são obrigatórios.");
                return;
            }

            DefaultTableModel model = (DefaultTableModel) stadiumsTable.getModel();
            model.addRow(new Object[]{
                    model.getRowCount() + 1,
                    name,
                    city,
                    country,
                    capacity.isEmpty() ? "0" : capacity,
                    year.isEmpty() ? "0" : year,
                    matches.isEmpty() ? "0" : matches
            });

            dialog.dispose();
            JOptionPane.showMessageDialog(rootPanel, "Estádio adicionado com sucesso.");
        });

        JButton cancelButton = new JButton("Cancelar");
        cancelButton.setBackground(new Color(200, 70, 70));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFont(new Font("Inter", Font.BOLD, 12));
        cancelButton.addActionListener(e -> dialog.dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        dialog.add(buttonPanel, gbc);

        dialog.setVisible(true);
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