package pt.ipleiria.es.worldcup.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class TicketPurchaseScreen extends JPanel {
    private JPanel designerPanel;
    private static final String SEARCH_PLACEHOLDER = "Pesquisar jogo ou estádio...";

    private JTextField searchInput;
    private JComboBox<String> countryFilter;
    private JComboBox<String> stadiumCombo;
    private JComboBox<MatchOption> matchCombo;
    private JSpinner quantitySpinner;
    private JCheckBox termsCheck;
    private JButton buyButton;
    private ButtonGroup paymentGroup;

    public TicketPurchaseScreen() {
        setLayout(new BorderLayout());
        setBackground(FifaUiKit.BACKGROUND);
        add(FifaUiKit.sidebar("Comprar"), BorderLayout.WEST);
        add(createWorkspace(), BorderLayout.CENTER);
    }

    private JPanel createWorkspace() {
        searchInput = new JTextField();
        countryFilter = FifaUiKit.combo(new String[]{"Todos os países", "EUA", "Canadá", "México"});

        JPanel workspace = new JPanel(new BorderLayout());
        workspace.setBackground(FifaUiKit.BACKGROUND);
        workspace.add(FifaUiKit.topbar(searchInput, countryFilter, SEARCH_PLACEHOLDER), BorderLayout.NORTH);
        workspace.add(createContent(), BorderLayout.CENTER);
        return workspace;
    }

    private JPanel createContent() {
        JPanel content = new JPanel(new BorderLayout(0, 12));
        content.setBackground(FifaUiKit.BACKGROUND);
        content.setBorder(BorderFactory.createEmptyBorder(14, 22, 20, 22));
        content.add(FifaUiKit.label("COMPRA DE BILHETES", 16, Font.BOLD, FifaUiKit.TEXT), BorderLayout.NORTH);

        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(FifaUiKit.PANEL_SOFT);
        card.setBorder(BorderFactory.createCompoundBorder(new FifaUiKit.RoundedBorder(16, FifaUiKit.LINE), BorderFactory.createEmptyBorder(34, 44, 34, 44)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridy = 0;
        card.add(sectionTitle("MÉTODO DE PAGAMENTO"), gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(18, 0, 28, 0);
        card.add(paymentGrid(), gbc);

        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 24, 0);
        card.add(selectionGrid(), gbc);

        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 0, 0);
        card.add(bottomBar(), gbc);

        content.add(card, BorderLayout.CENTER);
        return content;
    }

    private JLabel sectionTitle(String text) {
        JLabel title = FifaUiKit.label(text, 22, Font.BOLD, FifaUiKit.TEXT);
        title.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, FifaUiKit.LINE));
        title.setPreferredSize(new Dimension(0, 42));
        return title;
    }

    private JPanel paymentGrid() {
        JPanel grid = new JPanel(new GridLayout(1, 4, 44, 0));
        grid.setOpaque(false);
        paymentGroup = new ButtonGroup();

        grid.add(paymentOption("T", "transferência", new Color(0x1476D8), true));
        grid.add(paymentOption("VISA", "visa", new Color(0x253DD2), false));
        grid.add(paymentOption("AMEX", "american express", new Color(0xFFFFFF), false));
        grid.add(paymentOption("MB", "multibanco", new Color(0xFFFFFF), false));
        return grid;
    }

    private JPanel paymentOption(String logo, String actionCommand, Color background, boolean selected) {
        JPanel option = new JPanel(new BorderLayout(0, 12));
        option.setOpaque(false);

        JLabel card = new JLabel(logo, SwingConstants.CENTER);
        card.setOpaque(true);
        card.setBackground(background);
        card.setForeground(background.equals(Color.WHITE) ? Color.BLACK : Color.WHITE);
        card.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, logo.length() <= 2 ? 58 : 38));
        card.setPreferredSize(new Dimension(170, 116));
        card.setBorder(new FifaUiKit.RoundedBorder(8, FifaUiKit.LINE));
        option.add(card, BorderLayout.CENTER);

        JRadioButton radio = new JRadioButton();
        radio.setActionCommand(actionCommand);
        radio.setSelected(selected);
        radio.setOpaque(false);
        radio.setHorizontalAlignment(SwingConstants.CENTER);
        radio.setForeground(FifaUiKit.TEXT);
        paymentGroup.add(radio);
        option.add(radio, BorderLayout.SOUTH);

        card.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                radio.setSelected(true);
            }
        });
        return option;
    }

    private JPanel selectionGrid() {
        JPanel grid = new JPanel(new GridLayout(1, 3, 42, 0));
        grid.setOpaque(false);

        stadiumCombo = FifaUiKit.combo(stadiums().toArray(String[]::new));
        matchCombo = new JComboBox<>(matches().toArray(MatchOption[]::new));
        matchCombo.setPreferredSize(new Dimension(230, 34));
        matchCombo.setBackground(Color.WHITE);
        matchCombo.setForeground(FifaUiKit.FIELD_TEXT);
        matchCombo.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        quantitySpinner.setPreferredSize(new Dimension(230, 34));

        grid.add(fieldBlock("ESCOLHER ESTÁDIO", stadiumCombo));
        grid.add(fieldBlock("ESCOLHER JOGO", matchCombo));
        grid.add(fieldBlock("QUANTIDADE", quantitySpinner));
        return grid;
    }

    private JPanel fieldBlock(String title, JComponent field) {
        JPanel block = new JPanel(new BorderLayout(0, 8));
        block.setOpaque(false);
        block.add(FifaUiKit.label(title, 16, Font.PLAIN, FifaUiKit.MUTED), BorderLayout.NORTH);
        block.add(field, BorderLayout.CENTER);
        return block;
    }

    private JPanel bottomBar() {
        JPanel bottom = new JPanel(new BorderLayout(24, 0));
        bottom.setOpaque(false);
        bottom.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, FifaUiKit.LINE));

        termsCheck = new JCheckBox("Aceito os termos e condições da compra do bilhete");
        termsCheck.setOpaque(false);
        termsCheck.setForeground(FifaUiKit.MUTED);
        termsCheck.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        termsCheck.addActionListener(event -> updateBuyButtonState());
        bottom.add(termsCheck, BorderLayout.CENTER);

        buyButton = FifaUiKit.actionButton("COMPRAR", new Color(0xD4D4D4), new Color(0x111827));
        buyButton.setPreferredSize(new Dimension(190, 34));
        buyButton.setEnabled(false);
        updateBuyButtonState();
        buyButton.addActionListener(event -> buyTicket());
        bottom.add(buyButton, BorderLayout.EAST);
        return bottom;
    }

    private void updateBuyButtonState() {
        boolean enabled = termsCheck != null && termsCheck.isSelected();
        buyButton.setEnabled(enabled);
        buyButton.setBackground(enabled ? FifaUiKit.ACCENT : new Color(0x8F8CA0));
        buyButton.setForeground(enabled ? new Color(0x08233C) : Color.WHITE);
    }

    private void buyTicket() {
        MatchOption match = (MatchOption) matchCombo.getSelectedItem();
        if (match == null) {
            return;
        }

        if (paymentGroup.getSelection() == null) {
            JOptionPane.showMessageDialog(this, "Escolhe um método de pagamento.", "Pagamento em falta", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String stadium = String.valueOf(stadiumCombo.getSelectedItem());
        int quantity = (Integer) quantitySpinner.getValue();
        TicketStore.add(new TicketStore.PurchasedTicket(match.home(), match.away(), "JUN /08/ 2026", stadiumName(stadium), "Fase de grupos", quantity, match.homeFlag(), match.awayFlag()));

        JOptionPane.showMessageDialog(this, "Compra registada com sucesso.", "Bilhete comprado", JOptionPane.INFORMATION_MESSAGE);
        termsCheck.setSelected(false);
        updateBuyButtonState();
    }

    private String stadiumName(String selected) {
        int separator = selected.indexOf(" - ");
        return separator >= 0 ? selected.substring(0, separator).toUpperCase() : selected.toUpperCase();
    }

    private List<String> stadiums() {
        return List.of(
                "Mercedes-Benz Stadium - Atlanta",
                "BC Place - Vancouver",
                "Estádio Akron - Guadalajara",
                "Hard Rock Stadium - Miami",
                "MetLife Stadium - Nova Jérsia",
                "Estádio Azteca - Cidade do México"
        );
    }

    private List<MatchOption> matches() {
        return List.of(
                new MatchOption("Brasil", "Japão", "BRA", "JPN"),
                new MatchOption("Portugal", "Colômbia", "POR", "COL"),
                new MatchOption("Espanha", "Uruguai", "ESP", "URU"),
                new MatchOption("Argentina", "Argélia", "ARG", "ALG"),
                new MatchOption("Canadá", "México", "CAN", "MEX")
        );
    }

    private record MatchOption(String home, String away, String homeFlag, String awayFlag) {
        public String toString() {
            return home + " vs " + away;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
                // Mantém o look and feel padrão caso o do sistema não esteja disponível.
            }
            JFrame frame = new JFrame("FIFA - Compra de bilhetes");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setMinimumSize(new Dimension(1180, 720));
            frame.setSize(1366, 768);
            frame.setLocationRelativeTo(null);
            frame.setContentPane(new TicketPurchaseScreen());
            frame.setVisible(true);
        });
    }
}
