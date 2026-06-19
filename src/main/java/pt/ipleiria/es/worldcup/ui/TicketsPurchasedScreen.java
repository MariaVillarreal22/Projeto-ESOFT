package pt.ipleiria.es.worldcup.ui;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TicketsPurchasedScreen extends JPanel {
    private JPanel designerPanel;
    private static final String SEARCH_PLACEHOLDER = "Pesquisar bilhete, estádio ou fase...";

    private JTextField searchInput;
    private JComboBox<String> countryFilter;
    private JTable ticketsTable;
    private TicketsTableModel tableModel;

    public TicketsPurchasedScreen() {
        setLayout(new BorderLayout());
        setBackground(FifaUiKit.BACKGROUND);
        add(FifaUiKit.sidebar("Bilhetes comprados"), BorderLayout.WEST);
        add(createWorkspace(), BorderLayout.CENTER);
    }

    private JPanel createWorkspace() {
        searchInput = new JTextField();
        countryFilter = FifaUiKit.combo(new String[]{"Todos os países", "EUA", "Canadá", "México"});

        JPanel workspace = new JPanel(new BorderLayout());
        workspace.setBackground(FifaUiKit.BACKGROUND);
        workspace.add(FifaUiKit.topbar(searchInput, countryFilter, SEARCH_PLACEHOLDER), BorderLayout.NORTH);
        workspace.add(createContent(), BorderLayout.CENTER);
        FifaUiKit.onTextChange(searchInput, this::filterTickets);
        countryFilter.addActionListener(event -> filterTickets());
        return workspace;
    }

    private JPanel createContent() {
        JPanel content = new JPanel(new BorderLayout(0, 12));
        content.setBackground(FifaUiKit.BACKGROUND);
        content.setBorder(BorderFactory.createEmptyBorder(14, 22, 20, 22));
        content.add(FifaUiKit.label("OS MEUS BILHETES", 16, Font.BOLD, FifaUiKit.TEXT), BorderLayout.NORTH);

        JPanel card = new JPanel(new BorderLayout(0, 10));
        card.setBackground(FifaUiKit.PANEL_SOFT);
        card.setBorder(BorderFactory.createCompoundBorder(new FifaUiKit.RoundedBorder(16, FifaUiKit.LINE), BorderFactory.createEmptyBorder(16, 22, 14, 22)));
        card.add(tableScroll(), BorderLayout.CENTER);
        card.add(buttonRow(), BorderLayout.SOUTH);
        content.add(card, BorderLayout.CENTER);
        return content;
    }

    private JScrollPane tableScroll() {
        tableModel = new TicketsTableModel(TicketStore.snapshot());
        ticketsTable = new JTable(tableModel);
        ticketsTable.setRowHeight(78);
        ticketsTable.setFillsViewportHeight(true);
        ticketsTable.setShowHorizontalLines(true);
        ticketsTable.setShowVerticalLines(false);
        ticketsTable.setGridColor(FifaUiKit.LINE);
        ticketsTable.setIntercellSpacing(new Dimension(0, 1));
        ticketsTable.setBackground(FifaUiKit.PANEL_SOFT);
        ticketsTable.setForeground(FifaUiKit.TEXT);
        ticketsTable.setFont(new Font("Segoe UI", Font.BOLD, 15));
        ticketsTable.setSelectionBackground(new Color(0x1B4F9E));
        ticketsTable.setSelectionForeground(FifaUiKit.TEXT);

        JTableHeader header = ticketsTable.getTableHeader();
        header.setPreferredSize(new Dimension(0, 42));
        header.setReorderingAllowed(false);
        header.setDefaultRenderer(new HeaderRenderer());

        int[] widths = {330, 170, 190, 190, 90};
        for (int i = 0; i < widths.length; i++) {
            ticketsTable.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);
        }

        ticketsTable.getColumnModel().getColumn(0).setCellRenderer(new FlagsRenderer());
        for (int i = 1; i < ticketsTable.getColumnCount(); i++) {
            ticketsTable.getColumnModel().getColumn(i).setCellRenderer(new TextRenderer(i == 4 ? SwingConstants.CENTER : SwingConstants.LEFT));
        }

        JScrollPane scroll = new JScrollPane(ticketsTable);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(FifaUiKit.PANEL_SOFT);
        return scroll;
    }

    private JPanel buttonRow() {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.CENTER, 160, 0));
        row.setOpaque(false);

        JButton add = FifaUiKit.actionButton("+ ADICIONAR BILHETE", FifaUiKit.ACCENT, new Color(0x08233C));
        add.setPreferredSize(new Dimension(240, 34));
        add.addActionListener(event -> showAddDialog());
        row.add(add);

        JButton delete = FifaUiKit.actionButton("ELIMINAR BILHETE", FifaUiKit.DANGER, FifaUiKit.TEXT);
        delete.setPreferredSize(new Dimension(240, 34));
        delete.addActionListener(event -> deleteSelected());
        row.add(delete);
        return row;
    }

    private void filterTickets() {
        if (tableModel == null) {
            return;
        }
        tableModel.setRows(TicketStore.snapshot().stream()
                .filter(this::matchesSearch)
                .filter(this::matchesCountry)
                .toList());
    }

    private boolean matchesSearch(TicketStore.PurchasedTicket ticket) {
        String query = searchInput.getText().trim().toLowerCase();
        if (query.isBlank() || SEARCH_PLACEHOLDER.toLowerCase().equals(query)) {
            return true;
        }
        return ticket.home().toLowerCase().contains(query)
                || ticket.away().toLowerCase().contains(query)
                || ticket.stadium().toLowerCase().contains(query)
                || ticket.phase().toLowerCase().contains(query);
    }

    private boolean matchesCountry(TicketStore.PurchasedTicket ticket) {
        String selected = String.valueOf(countryFilter.getSelectedItem());
        if ("Todos os países".equals(selected)) {
            return true;
        }
        return switch (selected) {
            case "EUA" -> List.of("ATLANTA", "MIAMI", "NOVA JÉRSIA", "KANSAS CITY").contains(ticket.stadium());
            case "Canadá" -> "VANCOUVER".equals(ticket.stadium()) || "TORONTO".equals(ticket.stadium());
            case "México" -> "GUADALAJARA".equals(ticket.stadium()) || ticket.stadium().contains("MÉXICO");
            default -> true;
        };
    }

    private void showAddDialog() {
        JComboBox<MatchOption> match = new JComboBox<>(matches().toArray(MatchOption[]::new));
        JComboBox<String> stadium = new JComboBox<>(new String[]{"ATLANTA", "VANCOUVER", "GUADALAJARA", "MIAMI", "CIDADE DO MÉXICO"});
        JSpinner quantity = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));

        JPanel form = new JPanel(new GridBagLayout());
        addFormRow(form, 0, "Jogo", match);
        addFormRow(form, 1, "Estádio", stadium);
        addFormRow(form, 2, "Quantidade", quantity);

        int result = JOptionPane.showConfirmDialog(this, form, "Adicionar bilhete", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        MatchOption selected = (MatchOption) match.getSelectedItem();
        if (selected == null) {
            return;
        }
        TicketStore.PurchasedTicket ticket = new TicketStore.PurchasedTicket(selected.home(), selected.away(), "JUN /08/ 2026", String.valueOf(stadium.getSelectedItem()), "Fase de grupos", (Integer) quantity.getValue(), selected.homeFlag(), selected.awayFlag());
        TicketStore.add(ticket);
        filterTickets();
    }

    private void addFormRow(JPanel form, int row, String label, JComponent field) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = row;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 0;
        form.add(new JLabel(label), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        field.setPreferredSize(new Dimension(240, 28));
        form.add(field, gbc);
    }

    private void deleteSelected() {
        int viewRow = ticketsTable.getSelectedRow();
        if (viewRow < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona um bilhete para eliminar.", "Sem selecção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        TicketStore.PurchasedTicket ticket = tableModel.getTicket(viewRow);
        int result = JOptionPane.showConfirmDialog(this, "Eliminar o bilhete " + ticket.home() + " vs " + ticket.away() + "?", "Eliminar bilhete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (result == JOptionPane.YES_OPTION) {
            TicketStore.remove(ticket);
            filterTickets();
        }
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

    private static final class TicketsTableModel extends AbstractTableModel {
        private static final String[] COLUMNS = {"JOGO", "DATA", "ESTÁDIO", "FASE", "QTD"};
        private List<TicketStore.PurchasedTicket> tickets;

        private TicketsTableModel(List<TicketStore.PurchasedTicket> tickets) {
            this.tickets = new ArrayList<>(tickets);
        }

        public int getRowCount() { return tickets.size(); }
        public int getColumnCount() { return COLUMNS.length; }
        public String getColumnName(int column) { return COLUMNS[column]; }
        public boolean isCellEditable(int row, int column) { return false; }
        public Object getValueAt(int row, int column) {
            TicketStore.PurchasedTicket ticket = tickets.get(row);
            return switch (column) {
                case 0 -> ticket;
                case 1 -> ticket.date();
                case 2 -> ticket.stadium();
                case 3 -> ticket.phase().toUpperCase();
                case 4 -> ticket.quantity();
                default -> "";
            };
        }

        TicketStore.PurchasedTicket getTicket(int row) {
            return tickets.get(row);
        }

        void setRows(List<TicketStore.PurchasedTicket> tickets) {
            this.tickets = new ArrayList<>(tickets);
            fireTableDataChanged();
        }
    }

    private static final class HeaderRenderer extends DefaultTableCellRenderer {
        HeaderRenderer() {
            setOpaque(true);
            setBackground(FifaUiKit.PANEL_SOFT);
            setForeground(FifaUiKit.MUTED);
            setFont(new Font("Segoe UI", Font.PLAIN, 18));
            setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, FifaUiKit.LINE));
            setHorizontalAlignment(SwingConstants.CENTER);
        }
    }

    private static final class TextRenderer extends DefaultTableCellRenderer {
        private final int alignment;
        private TextRenderer(int alignment) {
            this.alignment = alignment;
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean focus, int row, int column) {
            super.getTableCellRendererComponent(table, value, selected, focus, row, column);
            setBackground(selected ? table.getSelectionBackground() : FifaUiKit.PANEL_SOFT);
            setForeground(FifaUiKit.TEXT);
            setFont(new Font("Segoe UI", Font.BOLD, 16));
            setHorizontalAlignment(alignment);
            setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, FifaUiKit.LINE));
            return this;
        }
    }

    private static final class FlagsRenderer extends JPanel implements TableCellRenderer {
        private FlagsRenderer() {
            setOpaque(true);
            setLayout(new GridBagLayout());
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean focus, int row, int column) {
            removeAll();
            setBackground(selected ? table.getSelectionBackground() : FifaUiKit.PANEL_SOFT);
            setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, FifaUiKit.LINE));
            TicketStore.PurchasedTicket ticket = (TicketStore.PurchasedTicket) value;

            JPanel flags = new JPanel(new GridLayout(1, 2, 0, 0));
            flags.setOpaque(false);
            flags.setPreferredSize(new Dimension(310, 58));
            flags.add(new JLabel(FifaUiKit.flag(ticket.homeFlag(), 155, 58)));
            flags.add(new JLabel(FifaUiKit.flag(ticket.awayFlag(), 155, 58)));
            add(flags);
            return this;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
                // Mantém o look and feel padrão caso o do sistema não esteja disponível.
            }
            JFrame frame = new JFrame("FIFA - Bilhetes comprados");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setMinimumSize(new Dimension(1180, 720));
            frame.setSize(1366, 768);
            frame.setLocationRelativeTo(null);
            frame.setContentPane(new TicketsPurchasedScreen());
            frame.setVisible(true);
        });
    }
}
