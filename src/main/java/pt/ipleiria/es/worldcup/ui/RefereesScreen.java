package pt.ipleiria.es.worldcup.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class RefereesScreen {
    private JPanel rootPanel;
    private JPanel contenido;
    private JPanel botones;
    private JLabel totalLabel;
    private JLabel disponiblesLabel;
    private JLabel partidosLabel;
    private JLabel paisesLabel;

    private JTable refereesTable;
    private JButton addRefereeButton;

    public RefereesScreen() {
        $$$setupUI$$$();
        buildContent();
        loadData();
        updateStats();
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

    private void buildContent() {
        contenido.removeAll();
        contenido.setLayout(new BorderLayout());
        contenido.setBackground(AppTheme.BACKGROUND);

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

        // Datos
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
        addRefereeButton.addActionListener(e -> showAddRefereeDialog());

        buttonPanel.add(addRefereeButton);
        card.add(buttonPanel, BorderLayout.SOUTH);

        return card;
    }

    private JPanel createStatsPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 4, 15, 0));
        panel.setBackground(AppTheme.BACKGROUND);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        panel.add(createStatCard("TOTAL", "0", totalLabel = new JLabel()));
        panel.add(createStatCard("DISPONIBLES", "0", disponiblesLabel = new JLabel()));
        panel.add(createStatCard("PARTIDOS ASSIGNADOS", "0", partidosLabel = new JLabel()));
        panel.add(createStatCard("PAÍSES", "0", paisesLabel = new JLabel()));

        return panel;
    }

    private void showAddRefereeDialog() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Añadir Árbitro");
        dialog.setModal(true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(rootPanel);
        dialog.setLayout(new GridBagLayout());
        dialog.getContentPane().setBackground(new Color(15, 42, 92));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Nombre
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel nameLabel = new JLabel("Nombre:");
        nameLabel.setForeground(Color.WHITE);
        dialog.add(nameLabel, gbc);
        gbc.gridx = 1;
        JTextField nameField = new JTextField(15);
        dialog.add(nameField, gbc);

        // País
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel countryLabel = new JLabel("País:");
        countryLabel.setForeground(Color.WHITE);
        dialog.add(countryLabel, gbc);
        gbc.gridx = 1;
        JTextField countryField = new JTextField(15);
        dialog.add(countryField, gbc);

        // Rol
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel roleLabel = new JLabel("Rol:");
        roleLabel.setForeground(Color.WHITE);
        dialog.add(roleLabel, gbc);
        gbc.gridx = 1;
        JComboBox<String> roleCombo = new JComboBox<>(new String[]{"Center Referee", "Assistant Referee", "VAR"});
        dialog.add(roleCombo, gbc);

        // Estado
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel stateLabel = new JLabel("Estado:");
        stateLabel.setForeground(Color.WHITE);
        dialog.add(stateLabel, gbc);
        gbc.gridx = 1;
        JComboBox<String> stateCombo = new JComboBox<>(new String[]{"BUSY", "REST", "ASSET"});
        dialog.add(stateCombo, gbc);

        // Botones
        gbc.gridx = 0;
        gbc.gridy = 4;
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
            String country = countryField.getText().trim();
            if (name.isEmpty() || country.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Nombre y país son obligatorios.");
                return;
            }

            String role = (String) roleCombo.getSelectedItem();
            String state = (String) stateCombo.getSelectedItem();

            DefaultTableModel model = (DefaultTableModel) refereesTable.getModel();
            model.addRow(new Object[]{
                    model.getRowCount() + 1,
                    name,
                    country,
                    role,
                    state
            });
            updateStats();
            dialog.dispose();
            JOptionPane.showMessageDialog(rootPanel, "Árbitro añadido con éxito.");
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

    private void updateStats() {
        DefaultTableModel model = (DefaultTableModel) refereesTable.getModel();
        int total = model.getRowCount();

        int disponibles = 0;
        int partidosAsignados = 0;
        int paises = 0;

        // Contar países únicos
        java.util.Set<String> countries = new java.util.HashSet<>();

        for (int i = 0; i < total; i++) {
            String state = (String) model.getValueAt(i, 4); // STATE está en columna 4
            String country = (String) model.getValueAt(i, 2); // COUNTRY está en columna 2

            if ("REST".equals(state) || "ASSET".equals(state)) {
                disponibles++;
            }

            // Cada árbitro tiene 4 partidos asignados (ejemplo)
            partidosAsignados += 4;

            countries.add(country);
        }

        paises = countries.size();

        // Actualizar las tarjetas
        // Necesitamos acceso a los JLabels de las tarjetas
        // Por ahora, usamos un método alternativo
        updateStatCards(total, disponibles, partidosAsignados, paises);
    }

    private void updateStatCards(int total, int disponibles, int partidos, int paises) {
        totalLabel.setText(String.valueOf(total));
        disponiblesLabel.setText(String.valueOf(disponibles));
        partidosLabel.setText(String.valueOf(partidos));
        paisesLabel.setText(String.valueOf(paises));
    }

    private JPanel createStatCard(String label, String value, JLabel valueLabel) {
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

        valueLabel.setText(value);
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