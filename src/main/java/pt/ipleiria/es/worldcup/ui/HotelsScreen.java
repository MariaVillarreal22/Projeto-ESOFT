package pt.ipleiria.es.worldcup.ui;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Path2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HotelsScreen extends JPanel {
    private JPanel designerPanel;

    private static final int SIDEBAR_WIDTH = 190;
    private static final int TOPBAR_HEIGHT = 56;
    private static final int ROW_HEIGHT = 86;
    private static final int PHOTO_WIDTH = 176;
    private static final int PHOTO_HEIGHT = 64;
    private static final int ACTIONS_COLUMN = 7;
    private static final int ACTION_BUTTON_WIDTH = 66;
    private static final int ACTION_BUTTON_HEIGHT = 24;
    private static final int ACTION_BUTTON_GAP = 8;
    private static final String SEARCH_PLACEHOLDER = "Pesquisar hotel, cidade ou estádio...";

    private static final Color BACKGROUND = new Color(0x121D42);
    private static final Color SIDEBAR = new Color(0x13204A);
    private static final Color TOPBAR = new Color(0x1D5DDB);
    private static final Color CONTENT = new Color(0x142F66);
    private static final Color TABLE = new Color(0x142F66);
    private static final Color TABLE_ALT = new Color(0x17386F);
    private static final Color LINE = new Color(0x2D5593);
    private static final Color TEXT = Color.WHITE;
    private static final Color MUTED = new Color(0xB9C8D8);
    private static final Color GOLD = new Color(0xFFD21E);
    private static final Color CHIP = new Color(0x0F3D6E);
    private static final Color ACCENT = new Color(0x61D394);
    private static final Color DANGER = new Color(0xEF6F6C);

    private JTextField searchInput;
    private JComboBox<String> countryFilter;
    private JComboBox<String> ratingFilter;
    private JTable hotelsTable;
    private HotelsTableModel tableModel;
    private TableRowSorter<HotelsTableModel> sorter;

    public HotelsScreen() {
        setLayout(new BorderLayout());
        setBackground(BACKGROUND);
        add(createSidebar(), BorderLayout.WEST);
        add(createWorkspace(), BorderLayout.CENTER);
    }

    private JPanel createSidebar() {
        return FifaUiKit.sidebar("Hotéis");
    }

    private JPanel createWorkspace() {
        JPanel workspace = new JPanel(new BorderLayout());
        workspace.setBackground(BACKGROUND);
        workspace.add(createTopbar(), BorderLayout.NORTH);
        workspace.add(createContent(), BorderLayout.CENTER);
        return workspace;
    }

    private JPanel createTopbar() {
        JPanel topbar = new JPanel(new GridBagLayout());
        topbar.setPreferredSize(new Dimension(0, TOPBAR_HEIGHT));
        topbar.setBackground(TOPBAR);
        topbar.setBorder(BorderFactory.createEmptyBorder(9, 12, 9, 14));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 7, 0, 7);
        gbc.fill = GridBagConstraints.BOTH;

        gbc.gridx = 0;
        gbc.weightx = 0;
        topbar.add(FifaUiKit.menuButton(), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        searchInput = new JTextField();
        FifaUiKit.onTextChange(searchInput, this::applyFilters);
        topbar.add(FifaUiKit.searchBox(searchInput, SEARCH_PLACEHOLDER), gbc);

        gbc.gridx = 2;
        gbc.weightx = 0.55;
        countryFilter = FifaUiKit.combo(new String[]{"Todos os países", "EUA", "Canadá", "México"});
        countryFilter.addActionListener(event -> applyFilters());
        topbar.add(countryFilter, gbc);

        gbc.gridx = 3;
        gbc.weightx = 0.38;
        ratingFilter = FifaUiKit.combo(new String[]{"Classificação", "5 estrelas", "4 estrelas", "3 estrelas", "2 estrelas", "1 estrela"});
        ratingFilter.addActionListener(event -> applyFilters());
        topbar.add(ratingFilter, gbc);

        gbc.gridx = 4;
        gbc.weightx = 0.30;
        topbar.add(FifaUiKit.dateBox(), gbc);
        return topbar;
    }

    private JPanel createContent() {
        JPanel content = new JPanel(new BorderLayout(0, 10));
        content.setBackground(CONTENT);
        content.setBorder(BorderFactory.createEmptyBorder(12, 18, 18, 18));
        content.add(titleRow(), BorderLayout.NORTH);
        content.add(tableScroll(), BorderLayout.CENTER);
        return content;
    }

    private JPanel titleRow() {
        JPanel row = new JPanel(new BorderLayout());
        row.setOpaque(false);
        row.add(label("HOTÉIS POPULARES", 16, Font.BOLD, TEXT), BorderLayout.WEST);

        JButton add = FifaUiKit.actionButton("+ ADICIONAR HOTEL", ACCENT, new Color(0x08233C));
        add.setFont(new Font("Segoe UI", Font.BOLD, 11));
        add.setPreferredSize(new Dimension(150, 30));
        add.addActionListener(event -> showHotelDialog(null));
        row.add(add, BorderLayout.EAST);
        return row;
    }

    private JScrollPane tableScroll() {
        tableModel = new HotelsTableModel(mockHotels());
        hotelsTable = new JTable(tableModel);
        sorter = new TableRowSorter<>(tableModel);
        hotelsTable.setRowSorter(sorter);
        hotelsTable.setRowHeight(ROW_HEIGHT);
        hotelsTable.setFillsViewportHeight(true);
        hotelsTable.setShowHorizontalLines(true);
        hotelsTable.setShowVerticalLines(false);
        hotelsTable.setGridColor(LINE);
        hotelsTable.setIntercellSpacing(new Dimension(0, 1));
        hotelsTable.setBackground(TABLE);
        hotelsTable.setForeground(TEXT);
        hotelsTable.setFont(new Font("Segoe UI", Font.BOLD, 12));
        hotelsTable.setSelectionBackground(new Color(0x1B4F9E));
        hotelsTable.setSelectionForeground(TEXT);
        hotelsTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                handleTableClick(event);
            }
        });

        JTableHeader header = hotelsTable.getTableHeader();
        header.setPreferredSize(new Dimension(0, 28));
        header.setReorderingAllowed(false);
        header.setDefaultRenderer(new HeaderRenderer());

        int[] widths = {38, 180, 195, 160, 180, 185, 118, 176};
        for (int i = 0; i < widths.length; i++) {
            hotelsTable.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);
        }
        hotelsTable.getColumnModel().getColumn(4).setMinWidth(170);
        hotelsTable.getColumnModel().getColumn(ACTIONS_COLUMN).setMinWidth(168);

        hotelsTable.getColumnModel().getColumn(0).setCellRenderer(new NumberRenderer());
        hotelsTable.getColumnModel().getColumn(1).setCellRenderer(new PhotoRenderer());
        hotelsTable.getColumnModel().getColumn(2).setCellRenderer(new TextRenderer(SwingConstants.LEFT));
        hotelsTable.getColumnModel().getColumn(3).setCellRenderer(new TextRenderer(SwingConstants.LEFT));
        hotelsTable.getColumnModel().getColumn(4).setCellRenderer(new RatingRenderer());
        hotelsTable.getColumnModel().getColumn(5).setCellRenderer(new TextRenderer(SwingConstants.CENTER));
        hotelsTable.getColumnModel().getColumn(6).setCellRenderer(new OccupancyRenderer());
        hotelsTable.getColumnModel().getColumn(7).setCellRenderer(new ActionsRenderer());

        JScrollPane scroll = new JScrollPane(hotelsTable);
        scroll.setBorder(new RoundedBorder(14, LINE));
        scroll.getViewport().setBackground(TABLE);
        return scroll;
    }

    private void handleTableClick(MouseEvent event) {
        int viewRow = hotelsTable.rowAtPoint(event.getPoint());
        int viewColumn = hotelsTable.columnAtPoint(event.getPoint());
        if (viewRow < 0 || viewColumn != ACTIONS_COLUMN) {
            return;
        }

        Rectangle cell = hotelsTable.getCellRect(viewRow, viewColumn, true);
        int modelRow = hotelsTable.convertRowIndexToModel(viewRow);
        int localX = event.getX() - cell.x;
        int totalWidth = ACTION_BUTTON_WIDTH * 2 + ACTION_BUTTON_GAP;
        int startX = Math.max(0, (cell.width - totalWidth) / 2);
        int editEnd = startX + ACTION_BUTTON_WIDTH;
        int deleteStart = editEnd + ACTION_BUTTON_GAP;
        int deleteEnd = deleteStart + ACTION_BUTTON_WIDTH;

        if (localX >= startX && localX <= editEnd) {
            showHotelDialog(tableModel.getHotel(modelRow));
        } else if (localX >= deleteStart && localX <= deleteEnd) {
            deleteHotel(modelRow);
        }
    }

    private void applyFilters() {
        if (sorter == null) {
            return;
        }

        sorter.setRowFilter(new RowFilter<>() {
            public boolean include(Entry<? extends HotelsTableModel, ? extends Integer> entry) {
                Hotel hotel = entry.getModel().getHotel(entry.getIdentifier());
                return matchesSearch(hotel) && matchesCountry(hotel) && matchesRating(hotel);
            }
        });
    }

    private boolean matchesSearch(Hotel hotel) {
        if (searchInput == null) {
            return true;
        }
        String query = searchInput.getText().trim().toLowerCase(Locale.ROOT);
        if (query.isBlank() || SEARCH_PLACEHOLDER.toLowerCase(Locale.ROOT).equals(query)) {
            return true;
        }
        return hotel.name().toLowerCase(Locale.ROOT).contains(query)
                || hotel.location().toLowerCase(Locale.ROOT).contains(query)
                || hotel.distance().toLowerCase(Locale.ROOT).contains(query);
    }

    private boolean matchesCountry(Hotel hotel) {
        if (countryFilter == null || countryFilter.getSelectedIndex() == 0) {
            return true;
        }
        String selected = String.valueOf(countryFilter.getSelectedItem()).toUpperCase(Locale.ROOT);
        return hotel.location().toUpperCase(Locale.ROOT).endsWith("/" + selected);
    }

    private boolean matchesRating(Hotel hotel) {
        if (ratingFilter == null || ratingFilter.getSelectedIndex() == 0) {
            return true;
        }
        String selected = String.valueOf(ratingFilter.getSelectedItem());
        int rating = Character.digit(selected.charAt(0), 10);
        return hotel.rating() == rating;
    }

    private void showHotelDialog(Hotel existingHotel) {
        boolean editing = existingHotel != null;
        JTextField name = new JTextField(editing ? existingHotel.name() : "");
        JTextField city = new JTextField(editing ? existingHotel.city() : "");
        JComboBox<String> country = new JComboBox<>(new String[]{"EUA", "Canadá", "México"});
        JSpinner rating = new JSpinner(new SpinnerNumberModel(editing ? existingHotel.rating() : 4, 1, 5, 1));
        JTextField distance = new JTextField(editing ? existingHotel.distance() : "500 metros");
        JSpinner occupancy = new JSpinner(new SpinnerNumberModel(editing ? existingHotel.occupancy() : 70, 0, 100, 1));

        if (editing) {
            country.setSelectedItem(existingHotel.country());
        }

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        addFormRow(form, 0, "Hotel", name);
        addFormRow(form, 1, "Cidade", city);
        addFormRow(form, 2, "País", country);
        addFormRow(form, 3, "Classificação", rating);
        addFormRow(form, 4, "Distância", distance);
        addFormRow(form, 5, "Ocupação (%)", occupancy);

        int result = JOptionPane.showConfirmDialog(this, form, editing ? "Editar hotel" : "Adicionar hotel", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        if (name.getText().isBlank() || city.getText().isBlank() || distance.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Preenche hotel, cidade e distância.", "Dados incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String countryValue = String.valueOf(country.getSelectedItem());
        String code = city.getText().trim().substring(0, Math.min(3, city.getText().trim().length())).toUpperCase(Locale.ROOT);
        Hotel updated = new Hotel(
                editing ? existingHotel.id() : tableModel.nextId(),
                new HotelPhotoIcon(photoLight(countryValue), photoDark(countryValue), code),
                name.getText().trim(),
                city.getText().trim().toUpperCase(Locale.ROOT) + "/" + countryValue.toUpperCase(Locale.ROOT),
                (Integer) rating.getValue(),
                distance.getText().trim(),
                (Integer) occupancy.getValue()
        );

        if (editing) {
            tableModel.updateHotel(existingHotel.id(), updated);
        } else {
            tableModel.addHotel(updated);
        }
        applyFilters();
    }

    private void addFormRow(JPanel form, int row, String text, JComponent field) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = row;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.weightx = 0;
        form.add(new JLabel(text), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        field.setPreferredSize(new Dimension(240, 28));
        form.add(field, gbc);
    }

    private void deleteHotel(int modelRow) {
        Hotel hotel = tableModel.getHotel(modelRow);
        int result = JOptionPane.showConfirmDialog(this, "Apagar " + hotel.name() + "?", "Eliminar hotel", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (result == JOptionPane.YES_OPTION) {
            tableModel.removeHotel(modelRow);
            applyFilters();
        }
    }

    private List<Hotel> mockHotels() {
        return List.of(
                new Hotel(1, new HotelPhotoIcon(new Color(0xF5C7A8), new Color(0x8E4B32), "ATL"), "Royal Pitch Atlanta", "ATLANTA/EUA", 3, "200 metros", 82),
                new Hotel(2, new HotelPhotoIcon(new Color(0xDDE7EF), new Color(0x284E79), "BOS"), "Harbor View Boston", "BOSTON/EUA", 4, "6 quilómetros", 64),
                new Hotel(3, new HotelPhotoIcon(new Color(0xD8B072), new Color(0x7C3C2C), "MIA"), "Ocean Drive Palace", "MIAMI/EUA", 1, "300 metros", 91),
                new Hotel(4, new HotelPhotoIcon(new Color(0xF2F6E7), new Color(0x8EB070), "TOR"), "Maple Garden Suites", "TORONTO/CANADÁ", 2, "3 quilómetros", 48),
                new Hotel(5, new HotelPhotoIcon(new Color(0xD7E5FF), new Color(0x305DA8), "VAN"), "North Gate Vancouver", "VANCOUVER/CANADÁ", 5, "800 metros", 76),
                new Hotel(6, new HotelPhotoIcon(new Color(0xFFE0BA), new Color(0xB96A30), "MEX"), "Azteca Grand Hotel", "CIDADE DO MÉXICO/MÉXICO", 4, "1,2 quilómetros", 69)
        );
    }

    private Color photoLight(String country) {
        return switch (country) {
            case "Canadá" -> new Color(0xD7E5FF);
            case "México" -> new Color(0xFFE0BA);
            default -> new Color(0xF5C7A8);
        };
    }

    private Color photoDark(String country) {
        return switch (country) {
            case "Canadá" -> new Color(0x305DA8);
            case "México" -> new Color(0xB96A30);
            default -> new Color(0x8E4B32);
        };
    }

    private static JLabel label(String text, int size, int style, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", style, size));
        label.setForeground(color);
        return label;
    }

    private record Hotel(int id, Icon photo, String name, String location, int rating, String distance, int occupancy) {
        String city() {
            int separator = location.indexOf('/');
            return separator >= 0 ? location.substring(0, separator) : location;
        }

        String country() {
            int separator = location.indexOf('/');
            return separator >= 0 ? normalizeCountry(location.substring(separator + 1)) : "EUA";
        }

        private static String normalizeCountry(String country) {
            return switch (country.toUpperCase(Locale.ROOT)) {
                case "CANADÁ", "CANADA" -> "Canadá";
                case "MÉXICO", "MEXICO" -> "México";
                default -> "EUA";
            };
        }
    }

    private static final class HotelsTableModel extends AbstractTableModel {
        private static final String[] COLUMNS = {"#", "FOTO", "HOTEL", "LOCALIZAÇÃO", "CLASSIFICAÇÃO", "DISTÂNCIA AO ESTÁDIO", "OCUPAÇÃO", "AÇÕES"};
        private final List<Hotel> hotels;

        private HotelsTableModel(List<Hotel> hotels) {
            this.hotels = new ArrayList<>(hotels);
        }

        public int getRowCount() { return hotels.size(); }
        public int getColumnCount() { return COLUMNS.length; }
        public String getColumnName(int column) { return COLUMNS[column]; }
        public Class<?> getColumnClass(int column) { return column == 1 ? Icon.class : Object.class; }
        public boolean isCellEditable(int row, int column) { return false; }

        public Object getValueAt(int row, int column) {
            Hotel h = hotels.get(row);
            return switch (column) {
                case 0 -> h.id();
                case 1 -> h.photo();
                case 2 -> h.name();
                case 3 -> h.location();
                case 4 -> h.rating();
                case 5 -> h.distance();
                case 6 -> h.occupancy();
                case 7 -> "Editar/Eliminar";
                default -> "";
            };
        }

        Hotel getHotel(int row) {
            return hotels.get(row);
        }

        int nextId() {
            return hotels.stream().mapToInt(Hotel::id).max().orElse(0) + 1;
        }

        void addHotel(Hotel hotel) {
            hotels.add(hotel);
            int row = hotels.size() - 1;
            fireTableRowsInserted(row, row);
        }

        void updateHotel(int hotelId, Hotel updatedHotel) {
            for (int i = 0; i < hotels.size(); i++) {
                if (hotels.get(i).id() == hotelId) {
                    hotels.set(i, updatedHotel);
                    fireTableRowsUpdated(i, i);
                    return;
                }
            }
        }

        void removeHotel(int row) {
            hotels.remove(row);
            fireTableRowsDeleted(row, row);
        }
    }

    private static final class HeaderRenderer extends DefaultTableCellRenderer {
        HeaderRenderer() {
            setOpaque(true);
            setBackground(TABLE);
            setForeground(TEXT);
            setFont(new Font("Segoe UI", Font.BOLD, 12));
            setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, LINE));
            setHorizontalAlignment(SwingConstants.CENTER);
        }
    }

    private abstract static class CellPanel extends JPanel implements TableCellRenderer {
        CellPanel() {
            setOpaque(true);
            setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean focus, int row, int column) {
            setBackground(selected ? table.getSelectionBackground() : row % 2 == 0 ? TABLE : TABLE_ALT);
            removeAll();
            build(value);
            return this;
        }

        abstract void build(Object value);
    }

    private static final class NumberRenderer extends CellPanel {
        void build(Object value) {
            setLayout(new BorderLayout());
            JLabel l = label(String.valueOf(value), 12, Font.PLAIN, MUTED);
            l.setHorizontalAlignment(SwingConstants.CENTER);
            add(l);
        }
    }

    private static final class PhotoRenderer extends CellPanel {
        void build(Object value) {
            setLayout(new GridBagLayout());
            add(new JLabel((Icon) value));
        }
    }

    private static final class TextRenderer extends CellPanel {
        private final int align;
        TextRenderer(int align) { this.align = align; }
        void build(Object value) {
            setLayout(new BorderLayout());
            JLabel l = label(String.valueOf(value), 12, Font.BOLD, TEXT);
            l.setHorizontalAlignment(align);
            add(l);
        }
    }

    private static final class RatingRenderer extends CellPanel {
        void build(Object value) {
            setLayout(new GridBagLayout());
            add(new StarsPanel((Integer) value));
        }
    }

    private static final class OccupancyRenderer extends CellPanel {
        void build(Object value) {
            setLayout(new GridBagLayout());
            add(new OccupancyChip((Integer) value));
        }
    }

    private static final class ActionsRenderer extends CellPanel {
        void build(Object value) {
            setLayout(new GridBagLayout());

            JPanel actions = new JPanel(new GridLayout(1, 2, ACTION_BUTTON_GAP, 0));
            actions.setOpaque(false);
            actions.setPreferredSize(new Dimension(ACTION_BUTTON_WIDTH * 2 + ACTION_BUTTON_GAP, ACTION_BUTTON_HEIGHT));
            actions.add(pill("Editar", ACCENT));
            actions.add(pill("Eliminar", DANGER));
            add(actions);
        }

        private JLabel pill(String text, Color color) {
            JLabel l = label(text, 10, Font.BOLD, color);
            l.setHorizontalAlignment(SwingConstants.CENTER);
            l.setVerticalAlignment(SwingConstants.CENTER);
            l.setPreferredSize(new Dimension(ACTION_BUTTON_WIDTH, ACTION_BUTTON_HEIGHT));
            l.setMinimumSize(new Dimension(ACTION_BUTTON_WIDTH, ACTION_BUTTON_HEIGHT));
            l.setBorder(new RoundedBorder(12, color));
            return l;
        }
    }

    private static final class StarsPanel extends JPanel {
        private static final int STAR_SIZE = 18;
        private static final int STAR_GAP = 5;
        private final int rating;

        StarsPanel(int rating) {
            this.rating = Math.max(0, Math.min(5, rating));
            setOpaque(false);
            setPreferredSize(new Dimension(136, 28));
            setMinimumSize(new Dimension(136, 28));
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = prepare(g);
            int totalWidth = 5 * STAR_SIZE + 4 * STAR_GAP;
            int startX = (getWidth() - totalWidth) / 2 + STAR_SIZE / 2;
            int centerY = getHeight() / 2;

            for (int i = 0; i < 5; i++) {
                Shape star = createStar(startX + i * (STAR_SIZE + STAR_GAP), centerY, STAR_SIZE / 2.0);
                if (i < rating) {
                    g2.setColor(GOLD);
                    g2.fill(star);
                } else {
                    g2.setColor(new Color(0xDDE5F5));
                    g2.fill(star);
                    g2.setColor(new Color(0xB9C8D8));
                    g2.draw(star);
                }
            }
            g2.dispose();
        }
    }

    private static final class OccupancyChip extends JPanel {
        private final int value;
        OccupancyChip(int value) {
            this.value = value;
            setOpaque(false);
            setPreferredSize(new Dimension(104, 24));
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = prepare(g);
            g2.setColor(new Color(0x0A1C45));
            g2.fillRoundRect(0, 3, getWidth(), 18, 18, 18);
            g2.setColor(value >= 85 ? new Color(0xFFB454) : new Color(0x61D394));
            g2.fillRoundRect(0, 3, Math.max(18, value * getWidth() / 100), 18, 18, 18);
            g2.setColor(TEXT);
            g2.setFont(new Font("Segoe UI", Font.BOLD, 10));
            String t = value + "%";
            FontMetrics m = g2.getFontMetrics();
            g2.drawString(t, (getWidth() - m.stringWidth(t)) / 2, 16);
            g2.dispose();
        }
    }

    private static final class HotelPhotoIcon implements Icon {
        private final Color light;
        private final Color dark;
        private final String code;

        HotelPhotoIcon(Color light, Color dark, String code) {
            this.light = light;
            this.dark = dark;
            this.code = code;
        }

        public int getIconWidth() { return PHOTO_WIDTH; }
        public int getIconHeight() { return PHOTO_HEIGHT; }

        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = prepare(g);
            g2.setClip(new RoundRectangle2D.Double(x, y, PHOTO_WIDTH, PHOTO_HEIGHT, 8, 8));
            g2.setPaint(new GradientPaint(x, y, light, x + PHOTO_WIDTH, y + PHOTO_HEIGHT, dark));
            g2.fillRect(x, y, PHOTO_WIDTH, PHOTO_HEIGHT);
            g2.setColor(new Color(255, 255, 255, 115));
            for (int i = 0; i < 5; i++) {
                g2.fillRect(x + 8 + i * 16, y + 8, 8, PHOTO_HEIGHT - 16);
            }
            g2.setColor(new Color(0, 0, 0, 90));
            g2.fillRoundRect(x + PHOTO_WIDTH - 62, y + 10, 42, 20, 5, 5);
            g2.fillRoundRect(x + 8, y + PHOTO_HEIGHT - 24, 42, 16, 10, 10);
            g2.setColor(new Color(255, 255, 255, 160));
            g2.fillRoundRect(x + PHOTO_WIDTH - 68, y + 39, 54, 16, 7, 7);
            g2.setColor(TEXT);
            g2.setFont(new Font("Segoe UI", Font.BOLD, 10));
            g2.drawString(code, x + 18, y + PHOTO_HEIGHT - 12);
            g2.setClip(null);
            g2.setColor(new Color(255, 255, 255, 60));
            g2.drawRoundRect(x, y, PHOTO_WIDTH - 1, PHOTO_HEIGHT - 1, 8, 8);
            g2.dispose();
        }
    }

    private static final class RoundedBorder extends AbstractBorder {
        private final int radius;
        private final Color color;

        RoundedBorder(int radius, Color color) {
            this.radius = radius;
            this.color = color;
        }

        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            Graphics2D g2 = prepare(g);
            g2.setColor(color);
            g2.drawRoundRect(x, y, w - 1, h - 1, radius, radius);
            g2.dispose();
        }
    }

    private static Graphics2D prepare(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        return g2;
    }

    private static Shape createStar(double cx, double cy, double r) {
        Path2D.Double s = new Path2D.Double();
        for (int i = 0; i < 10; i++) {
            double a = Math.PI / 2 + i * Math.PI / 5;
            double rr = i % 2 == 0 ? r : r * 0.42;
            double x = cx + Math.cos(a) * rr;
            double y = cy - Math.sin(a) * rr;
            if (i == 0) {
                s.moveTo(x, y);
            } else {
                s.lineTo(x, y);
            }
        }
        s.closePath();
        return s;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
                // Swing default remains usable if the system look and feel is unavailable.
            }
            JFrame frame = new JFrame("FIFA Hotels");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setMinimumSize(new Dimension(1180, 720));
            frame.setSize(1366, 768);
            frame.setLocationRelativeTo(null);
            frame.setContentPane(new HotelsScreen());
            frame.setVisible(true);
        });
    }
}
