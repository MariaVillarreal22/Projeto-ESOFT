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
        addTeamButton.addActionListener(e -> showAddTeamDialog());

        panel.add(addTeamButton);
        return panel;
    }

    private void loadData() {
        DefaultTableModel model = (DefaultTableModel) teamsTable.getModel();
        model.setRowCount(0);

        DataManager data = DataManager.getInstance();
        int index = 1;
        for (Team team : data.getTeams()) {
            model.addRow(new Object[]{
                    index++,
                    team.getName(),
                    team.getConfederation(),
                    team.getCluster(),
                    team.getMatches(),
                    team.getVictories(),
                    team.getTies(),
                    team.getDefeats(),
                    team.getPoints()
            });
        }
    }

    private void showAddTeamDialog() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Añadir Equipo");
        dialog.setModal(true);
        dialog.setSize(450, 400);
        dialog.setLocationRelativeTo(rootPanel2);
        dialog.setLayout(new GridBagLayout());
        dialog.getContentPane().setBackground(new Color(15, 42, 92));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Nombre del País
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel nameLabel = new JLabel("Nombre del País:");
        nameLabel.setForeground(Color.WHITE);
        dialog.add(nameLabel, gbc);
        gbc.gridx = 1;
        JTextField nameField = new JTextField(15);
        dialog.add(nameField, gbc);

        // Confederación
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel confLabel = new JLabel("Confederación:");
        confLabel.setForeground(Color.WHITE);
        dialog.add(confLabel, gbc);
        gbc.gridx = 1;
        JComboBox<String> confCombo = new JComboBox<>(new String[]{"UEFA", "CAF", "AFC", "CONCACAF", "CONMEBOL", "OFC"});
        dialog.add(confCombo, gbc);

        // Cluster
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel clusterLabel = new JLabel("Cluster:");
        clusterLabel.setForeground(Color.WHITE);
        dialog.add(clusterLabel, gbc);
        gbc.gridx = 1;
        JTextField clusterField = new JTextField(5);
        dialog.add(clusterField, gbc);

        // Partidos
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel matchesLabel = new JLabel("Partidos:");
        matchesLabel.setForeground(Color.WHITE);
        dialog.add(matchesLabel, gbc);
        gbc.gridx = 1;
        JTextField matchesField = new JTextField("0", 5);
        dialog.add(matchesField, gbc);

        // Victorias
        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel victoriesLabel = new JLabel("Victorias:");
        victoriesLabel.setForeground(Color.WHITE);
        dialog.add(victoriesLabel, gbc);
        gbc.gridx = 1;
        JTextField victoriesField = new JTextField("0", 5);
        dialog.add(victoriesField, gbc);

        // Empates
        gbc.gridx = 0;
        gbc.gridy = 5;
        JLabel tiesLabel = new JLabel("Empates:");
        tiesLabel.setForeground(Color.WHITE);
        dialog.add(tiesLabel, gbc);
        gbc.gridx = 1;
        JTextField tiesField = new JTextField("0", 5);
        dialog.add(tiesField, gbc);

        // Derrotas
        gbc.gridx = 0;
        gbc.gridy = 6;
        JLabel defeatsLabel = new JLabel("Derrotas:");
        defeatsLabel.setForeground(Color.WHITE);
        dialog.add(defeatsLabel, gbc);
        gbc.gridx = 1;
        JTextField defeatsField = new JTextField("0", 5);
        dialog.add(defeatsField, gbc);

        // Goles a Favor
        gbc.gridx = 0;
        gbc.gridy = 7;
        JLabel goalsForLabel = new JLabel("Goles a Favor:");
        goalsForLabel.setForeground(Color.WHITE);
        dialog.add(goalsForLabel, gbc);
        gbc.gridx = 1;
        JTextField goalsForField = new JTextField("0", 5);
        dialog.add(goalsForField, gbc);

        // Goles en Contra
        gbc.gridx = 0;
        gbc.gridy = 8;
        JLabel goalsAgainstLabel = new JLabel("Goles en Contra:");
        goalsAgainstLabel.setForeground(Color.WHITE);
        dialog.add(goalsAgainstLabel, gbc);
        gbc.gridx = 1;
        JTextField goalsAgainstField = new JTextField("0", 5);
        dialog.add(goalsAgainstField, gbc);

        // Botones
        gbc.gridx = 0;
        gbc.gridy = 9;
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
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "El nombre del país es obligatorio.");
                return;
            }

            try {
                int matches = Integer.parseInt(matchesField.getText().trim());
                int victories = Integer.parseInt(victoriesField.getText().trim());
                int ties = Integer.parseInt(tiesField.getText().trim());
                int defeats = Integer.parseInt(defeatsField.getText().trim());
                int goalsFor = Integer.parseInt(goalsForField.getText().trim());
                int goalsAgainst = Integer.parseInt(goalsAgainstField.getText().trim());

                String conf = (String) confCombo.getSelectedItem();
                String cluster = clusterField.getText().trim().toUpperCase();

                Team team = new Team(name, conf, cluster);
                team.setMatches(matches);
                team.setVictories(victories);
                team.setTies(ties);
                team.setDefeats(defeats);
                team.setGoalsFor(goalsFor);
                team.setGoalsAgainst(goalsAgainst);
                team.setPoints((victories * 3) + ties);

                DataManager.getInstance().addTeam(team);
                loadData();
                dialog.dispose();
                JOptionPane.showMessageDialog(rootPanel2, "Equipo añadido con éxito.");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Todos los números deben ser valores válidos.");
            }
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