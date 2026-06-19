package pt.ipleiria.es.worldcup.ui;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.RoundRectangle2D;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

final class FifaUiKit {
    static final int SIDEBAR_WIDTH = 190;
    static final int TOPBAR_HEIGHT = 56;

    static final Color BACKGROUND = new Color(0x121D42);
    static final Color SIDEBAR = new Color(0x13204A);
    static final Color HEADER = new Color(0x1D5DDB);
    static final Color PANEL = new Color(0x142F66);
    static final Color PANEL_SOFT = new Color(0x17386F);
    static final Color CARD = new Color(0x25005E);
    static final Color CHIP = new Color(0x0F3D6E);
    static final Color TEXT = Color.WHITE;
    static final Color MUTED = new Color(0xB9C8D8);
    static final Color ACCENT = new Color(0x61D394);
    static final Color DANGER = new Color(0xEF6F6C);
    static final Color GOLD = new Color(0xFFD21E);
    static final Color LINE = new Color(0x2D5593);
    static final Color FIELD_TEXT = new Color(0x1D2744);
    private static final Locale PT_LOCALE = Locale.forLanguageTag("pt-PT");
    private static final ZoneId APP_ZONE = ZoneId.systemDefault();
    private static final LocalDate DEFAULT_DATE = LocalDate.of(2026, 6, 1);

    private FifaUiKit() {
    }

    static JPanel sidebar(String activeItem) {
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(SIDEBAR_WIDTH, 0));
        sidebar.setBackground(SIDEBAR);
        sidebar.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        JLabel brand = label("FIFA°", 30, Font.BOLD, TEXT);
        brand.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebar.add(brand);
        sidebar.add(Box.createRigidArea(new Dimension(0, 18)));

        addMenuSection(sidebar, "COMPETIÇÕES", new String[]{"Fases", "Calendário", "Classificações", "Estatísticas"}, activeItem);
        addMenuSection(sidebar, "ENTIDADES", new String[]{"Equipas", "Árbitros", "Estádios"}, activeItem);
        addMenuSection(sidebar, "BILHETES", new String[]{"Comprar", "Bilhetes comprados"}, activeItem);
        addMenuSection(sidebar, "HOSPITALIDADE", new String[]{"Hotéis", "Locações"}, activeItem);
        sidebar.add(Box.createVerticalGlue());
        return sidebar;
    }

    private static void addMenuSection(JPanel parent, String title, String[] items, String activeItem) {
        JLabel heading = label(title, 12, Font.BOLD, TEXT);
        heading.setAlignmentX(Component.LEFT_ALIGNMENT);
        parent.add(heading);
        parent.add(Box.createRigidArea(new Dimension(0, 5)));

        for (String item : items) {
            boolean active = item.equals(activeItem);
            JLabel option = label(item, 11, active ? Font.BOLD : Font.PLAIN, active ? TEXT : MUTED);
            option.setAlignmentX(Component.LEFT_ALIGNMENT);
            option.setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0));
            option.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            option.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent event) {
                    navigate(option, item);
                }

                public void mouseEntered(MouseEvent event) {
                    option.setForeground(TEXT);
                }

                public void mouseExited(MouseEvent event) {
                    option.setForeground(active ? TEXT : MUTED);
                }
            });
            parent.add(option);
        }
        parent.add(Box.createRigidArea(new Dimension(0, 17)));
    }

    private static void navigate(Component source, String item) {
        if ("Calendário".equals(item)) {
            showSimpleCalendar(source);
            return;
        }

        String className = screenClassName(item);
        if (className == null) {
            JOptionPane.showMessageDialog(source, "O ecrã \"" + item + "\" ainda não está implementado.", "Navegação", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        try {
            Object screen = Class.forName(className).getDeclaredConstructor().newInstance();
            if (!(screen instanceof JPanel panel)) {
                return;
            }

            Window window = SwingUtilities.getWindowAncestor(source);
            if (window instanceof JFrame frame) {
                frame.setContentPane(panel);
                frame.revalidate();
                frame.repaint();
            }
        } catch (ReflectiveOperationException exception) {
            JOptionPane.showMessageDialog(source, "Não foi possível abrir o ecrã \"" + item + "\".", "Navegação", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void showSimpleCalendar(Component source) {
        JOptionPane.showMessageDialog(
                source,
                """
                <html>
                  <b>Calendário ilustrativo</b><br><br>
                  11 JUN 2026 - México vs África do Sul<br>
                  12 JUN 2026 - Canadá vs Japão<br>
                  13 JUN 2026 - Brasil vs Marrocos<br>
                  15 JUN 2026 - Portugal vs Colômbia
                </html>
                """,
                "Calendário",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private static String screenClassName(String item) {
        return switch (item) {
            case "Fases" -> "pt.ipleiria.es.worldcup.ui.MainScreen";
            case "Calendário", "Classificações" -> null;
            case "Estatísticas" -> "pt.ipleiria.es.worldcup.ui.StatsScreen";
            case "Equipas" -> "pt.ipleiria.es.worldcup.ui.TeamsScreen";
            case "Árbitros" -> "pt.ipleiria.es.worldcup.ui.RefereesScreen";
            case "Estádios" -> "pt.ipleiria.es.worldcup.ui.StadiumsScreen";
            case "Comprar" -> "pt.ipleiria.es.worldcup.ui.TicketPurchaseScreen";
            case "Bilhetes comprados" -> "pt.ipleiria.es.worldcup.ui.TicketsPurchasedScreen";
            case "Hotéis" -> "pt.ipleiria.es.worldcup.ui.HotelsScreen";
            case "Locações" -> "pt.ipleiria.es.worldcup.ui.LocationsScreen";
            default -> null;
        };
    }

    static JPanel topbar(JTextField searchField, JComboBox<String> filter, String searchPlaceholder) {
        JPanel topbar = new JPanel(new GridBagLayout());
        topbar.setPreferredSize(new Dimension(0, TOPBAR_HEIGHT));
        topbar.setBackground(HEADER);
        topbar.setBorder(BorderFactory.createEmptyBorder(9, 12, 9, 14));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 7, 0, 7);
        gbc.fill = GridBagConstraints.BOTH;

        gbc.gridx = 0;
        gbc.weightx = 0;
        topbar.add(menuButton(), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        topbar.add(searchBox(searchField, searchPlaceholder), gbc);

        gbc.gridx = 2;
        gbc.weightx = 0.7;
        topbar.add(filter, gbc);

        gbc.gridx = 3;
        gbc.weightx = 0.30;
        topbar.add(dateBox(), gbc);
        return topbar;
    }

    static JButton menuButton() {
        JButton button = new JButton(new LineIcon(24, LineIcon.Type.MENU));
        button.setPreferredSize(new Dimension(42, 34));
        button.setForeground(TEXT);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }

    static JPanel searchBox(JTextField field, String placeholder) {
        JPanel box = whiteBox(new BorderLayout(8, 0));
        JLabel icon = new JLabel(new LineIcon(17, LineIcon.Type.SEARCH));
        icon.setForeground(FIELD_TEXT);
        box.add(icon, BorderLayout.WEST);
        field.setText(placeholder);
        field.setOpaque(false);
        field.setBorder(BorderFactory.createEmptyBorder());
        field.setForeground(FIELD_TEXT);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent event) {
                if (placeholder.equals(field.getText())) {
                    field.setText("");
                }
            }

            public void focusLost(java.awt.event.FocusEvent event) {
                if (field.getText().isBlank()) {
                    field.setText(placeholder);
                }
            }
        });
        box.add(field, BorderLayout.CENTER);
        return box;
    }

    static JPanel dateBox() {
        return new DateSelectorBox(DEFAULT_DATE);
    }

    static JPanel whiteBox(LayoutManager layout) {
        JPanel box = new JPanel(layout);
        box.setPreferredSize(new Dimension(160, 34));
        box.setBackground(Color.WHITE);
        box.setBorder(BorderFactory.createCompoundBorder(new RoundedBorder(10, new Color(0xDDE6F8)), BorderFactory.createEmptyBorder(0, 12, 0, 12)));
        return box;
    }

    static JComboBox<String> combo(String[] values) {
        JComboBox<String> combo = new JComboBox<>(values);
        combo.setPreferredSize(new Dimension(210, 34));
        combo.setBackground(Color.WHITE);
        combo.setForeground(FIELD_TEXT);
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        combo.setFocusable(false);
        return combo;
    }

    static JLabel label(String text, int size, int style, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", style, size));
        label.setForeground(color);
        return label;
    }

    static JButton actionButton(String text, Color background, Color foreground) {
        JButton button = new SolidButton(text);
        button.setBackground(background);
        button.setForeground(foreground);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBorder(BorderFactory.createEmptyBorder(7, 16, 7, 16));
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setOpaque(false);
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }

    static void onTextChange(JTextField field, Runnable runnable) {
        field.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent event) { runnable.run(); }
            public void removeUpdate(DocumentEvent event) { runnable.run(); }
            public void changedUpdate(DocumentEvent event) { runnable.run(); }
        });
    }

    static Icon flag(String code, int width, int height) {
        return new FlagIcon(code, width, height);
    }

    static Icon markerIcon() {
        return new MarkerIcon();
    }

    static final class RoundedBorder extends AbstractBorder {
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

    private static final class LineIcon implements Icon {
        private enum Type { MENU, SEARCH, CALENDAR }
        private final int size;
        private final Type type;

        private LineIcon(int size, Type type) {
            this.size = size;
            this.type = type;
        }

        public int getIconWidth() { return size; }
        public int getIconHeight() { return size; }

        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = prepare(g);
            g2.setColor(c.getForeground());
            g2.setStroke(new BasicStroke(2.2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            if (type == Type.MENU) {
                int left = x + 3;
                int right = x + size - 3;
                g2.drawLine(left, y + 6, right, y + 6);
                g2.drawLine(left, y + size / 2, right, y + size / 2);
                g2.drawLine(left, y + size - 6, right, y + size - 6);
            } else if (type == Type.SEARCH) {
                g2.drawOval(x + 2, y + 2, size - 8, size - 8);
                g2.drawLine(x + size - 6, y + size - 6, x + size - 1, y + size - 1);
            } else {
                g2.drawRoundRect(x + 2, y + 4, size - 4, size - 5, 4, 4);
                g2.drawLine(x + 2, y + 9, x + size - 2, y + 9);
                g2.drawLine(x + 6, y + 2, x + 6, y + 6);
                g2.drawLine(x + size - 6, y + 2, x + size - 6, y + 6);
            }
            g2.dispose();
        }
    }

    private static final class MarkerIcon implements Icon {
        public int getIconWidth() { return 14; }
        public int getIconHeight() { return 18; }

        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = prepare(g);
            g2.setColor(GOLD);
            Polygon triangle = new Polygon(new int[]{x + 2, x + 12, x + 2}, new int[]{y + 3, y + 9, y + 15}, 3);
            g2.fillPolygon(triangle);
            g2.dispose();
        }
    }

    private static final class SolidButton extends JButton {
        private SolidButton(String text) {
            super(text);
        }

        protected void paintComponent(Graphics g) {
            Graphics2D g2 = prepare(g);
            Color base = getBackground();
            if (!isEnabled()) {
                base = new Color(0x8F8CA0);
            } else if (getModel().isPressed()) {
                base = base.darker();
            } else if (getModel().isRollover()) {
                base = base.brighter();
            }
            g2.setColor(base);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
            g2.setFont(getFont());
            g2.setColor(isEnabled() ? getForeground() : Color.WHITE);
            FontMetrics metrics = g2.getFontMetrics();
            String text = getText();
            int textX = (getWidth() - metrics.stringWidth(text)) / 2;
            int textY = (getHeight() - metrics.getHeight()) / 2 + metrics.getAscent();
            g2.drawString(text, textX, textY);
            g2.dispose();
        }
    }

    private static final class DateSelectorBox extends JPanel {
        private LocalDate selectedDate;
        private final JLabel valueLabel;

        private DateSelectorBox(LocalDate selectedDate) {
            super(new BorderLayout(8, 0));
            this.selectedDate = selectedDate;
            setPreferredSize(new Dimension(160, 34));
            setBackground(Color.WHITE);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            setBorder(BorderFactory.createCompoundBorder(new RoundedBorder(10, new Color(0xDDE6F8)), BorderFactory.createEmptyBorder(0, 12, 0, 12)));
            setToolTipText("Seleccionar data");

            valueLabel = label(formatDate(selectedDate), 10, Font.BOLD, FIELD_TEXT);
            add(valueLabel, BorderLayout.CENTER);

            JLabel icon = new JLabel(new LineIcon(15, LineIcon.Type.CALENDAR));
            icon.setForeground(FIELD_TEXT);
            add(icon, BorderLayout.EAST);

            MouseAdapter opener = new MouseAdapter() {
                public void mouseClicked(MouseEvent event) {
                    openDateDialog();
                }
            };
            addMouseListener(opener);
            valueLabel.addMouseListener(opener);
            icon.addMouseListener(opener);
        }

        private void openDateDialog() {
            Date initialDate = Date.from(selectedDate.atStartOfDay(APP_ZONE).toInstant());
            Date firstWorldCupDate = Date.from(LocalDate.of(2026, 6, 1).atStartOfDay(APP_ZONE).toInstant());
            Date lastWorldCupDate = Date.from(LocalDate.of(2026, 7, 31).atStartOfDay(APP_ZONE).toInstant());

            JSpinner spinner = new JSpinner(new SpinnerDateModel(initialDate, firstWorldCupDate, lastWorldCupDate, Calendar.DAY_OF_MONTH));
            spinner.setPreferredSize(new Dimension(180, 32));
            spinner.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            spinner.setEditor(new JSpinner.DateEditor(spinner, "dd/MM/yyyy"));

            JPanel panel = new JPanel(new GridBagLayout());
            panel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.gridy = 0;
            gbc.gridx = 0;
            gbc.anchor = GridBagConstraints.WEST;
            panel.add(new JLabel("Data"), gbc);
            gbc.gridx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weightx = 1;
            panel.add(spinner, gbc);

            int result = JOptionPane.showConfirmDialog(this, panel, "Seleccionar data", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result != JOptionPane.OK_OPTION) {
                return;
            }

            Date chosenDate = (Date) spinner.getValue();
            selectedDate = chosenDate.toInstant().atZone(APP_ZONE).toLocalDate();
            valueLabel.setText(formatDate(selectedDate));
            firePropertyChange("selectedDate", null, selectedDate);
        }

        private static String formatDate(LocalDate date) {
            String month = date.getMonth().getDisplayName(java.time.format.TextStyle.SHORT, PT_LOCALE)
                    .replace(".", "")
                    .toUpperCase(PT_LOCALE);
            return "%02d %s %d".formatted(date.getDayOfMonth(), month, date.getYear());
        }
    }

    private static final class FlagIcon implements Icon {
        private final String code;
        private final int width;
        private final int height;

        private FlagIcon(String code, int width, int height) {
            this.code = code;
            this.width = width;
            this.height = height;
        }

        public int getIconWidth() { return width; }
        public int getIconHeight() { return height; }

        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = prepare(g);
            g2.setClip(new RoundRectangle2D.Double(x, y, width, height, 4, 4));
            switch (code) {
                case "CAN" -> paintCanada(g2, x, y);
                case "USA" -> paintUsa(g2, x, y);
                case "MEX" -> paintVertical(g2, x, y, new Color(0x007A3D), Color.WHITE, new Color(0xCE1126));
                case "BRA" -> paintBrazil(g2, x, y);
                case "MAR" -> paintMorocco(g2, x, y);
                case "ARG" -> paintArgentina(g2, x, y);
                case "ALG" -> paintAlgeria(g2, x, y);
                case "CIV" -> paintVertical(g2, x, y, new Color(0xF77F00), Color.WHITE, new Color(0x009E60));
                case "ECU" -> paintHorizontal(g2, x, y, new Color(0xFCD116), new Color(0x003893), new Color(0xCE1126));
                case "ESP" -> paintHorizontal(g2, x, y, new Color(0xAA151B), new Color(0xF1BF00), new Color(0xAA151B));
                case "URU" -> paintUruguay(g2, x, y);
                case "POR" -> paintPortugal(g2, x, y);
                case "COL" -> paintHorizontal(g2, x, y, new Color(0xFCD116), new Color(0x003893), new Color(0xCE1126));
                case "JPN" -> paintJapan(g2, x, y);
                default -> paintSolid(g2, x, y, new Color(0x315DA8));
            }
            g2.setClip(null);
            g2.setColor(new Color(255, 255, 255, 70));
            g2.drawRoundRect(x, y, width - 1, height - 1, 4, 4);
            g2.dispose();
        }

        private void paintCanada(Graphics2D g2, int x, int y) {
            int side = width / 4;
            g2.setColor(new Color(0xFF3131));
            g2.fillRect(x, y, side, height);
            g2.fillRect(x + width - side, y, side, height);
            g2.setColor(Color.WHITE);
            g2.fillRect(x + side, y, width - side * 2, height);
            g2.setColor(new Color(0xE31B23));
            Shape leaf = mapleLeaf(x + width / 2.0, y + height / 2.0, height * 0.28);
            g2.fill(leaf);
        }

        private void paintUsa(Graphics2D g2, int x, int y) {
            int stripe = Math.max(1, height / 7);
            for (int i = 0; i < 7; i++) {
                g2.setColor(i % 2 == 0 ? new Color(0xBF0A30) : Color.WHITE);
                g2.fillRect(x, y + i * stripe, width, stripe + 1);
            }
            g2.setColor(new Color(0x002868));
            g2.fillRect(x, y, width / 2, stripe * 4);
            g2.setColor(Color.WHITE);
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 5; col++) {
                    g2.fillOval(x + 7 + col * 9, y + 5 + row * 8, 3, 3);
                }
            }
        }

        private void paintBrazil(Graphics2D g2, int x, int y) {
            g2.setColor(new Color(0x009B3A));
            g2.fillRect(x, y, width, height);
            Polygon diamond = new Polygon(new int[]{x + width / 2, x + width - 16, x + width / 2, x + 16}, new int[]{y + 8, y + height / 2, y + height - 8, y + height / 2}, 4);
            g2.setColor(new Color(0xFFDF00));
            g2.fillPolygon(diamond);
            g2.setColor(new Color(0x002776));
            g2.fill(new Ellipse2D.Double(x + width / 2.0 - height / 5.0, y + height / 2.0 - height / 5.0, height / 2.5, height / 2.5));
        }

        private void paintMorocco(Graphics2D g2, int x, int y) {
            paintSolid(g2, x, y, new Color(0xC1272D));
            g2.setColor(new Color(0x006233));
            g2.setStroke(new BasicStroke(Math.max(2f, height / 18f)));
            g2.draw(star(x + width / 2.0, y + height / 2.0, height * 0.20));
        }

        private void paintArgentina(Graphics2D g2, int x, int y) {
            paintHorizontal(g2, x, y, new Color(0x75AADB), Color.WHITE, new Color(0x75AADB));
            g2.setColor(GOLD);
            g2.fillOval(x + width / 2 - 5, y + height / 2 - 5, 10, 10);
        }

        private void paintAlgeria(Graphics2D g2, int x, int y) {
            g2.setColor(Color.WHITE);
            g2.fillRect(x, y, width / 2, height);
            g2.setColor(new Color(0x006233));
            g2.fillRect(x + width / 2, y, width - width / 2, height);
            g2.setColor(new Color(0xD21034));
            int r = height / 4;
            g2.fillOval(x + width / 2 - r, y + height / 2 - r, r * 2, r * 2);
            g2.setColor(Color.WHITE);
            g2.fillOval(x + width / 2 - r / 3, y + height / 2 - r, r * 2, r * 2);
            g2.setColor(new Color(0xD21034));
            g2.fill(star(x + width / 2.0 + r * 0.9, y + height / 2.0, r * 0.45));
        }

        private void paintUruguay(Graphics2D g2, int x, int y) {
            int stripe = Math.max(1, height / 5);
            for (int i = 0; i < 5; i++) {
                g2.setColor(i % 2 == 0 ? Color.WHITE : new Color(0x6CB6E8));
                g2.fillRect(x, y + i * stripe, width, stripe + 1);
            }
            g2.setColor(GOLD);
            g2.fillOval(x + 8, y + 8, Math.max(8, height / 4), Math.max(8, height / 4));
        }

        private void paintPortugal(Graphics2D g2, int x, int y) {
            int green = (int) (width * 0.42);
            g2.setColor(new Color(0x006600));
            g2.fillRect(x, y, green, height);
            g2.setColor(new Color(0xFF0000));
            g2.fillRect(x + green, y, width - green, height);
            g2.setColor(GOLD);
            g2.fillOval(x + green - height / 8, y + height / 2 - height / 8, height / 4, height / 4);
        }

        private void paintJapan(Graphics2D g2, int x, int y) {
            paintSolid(g2, x, y, Color.WHITE);
            g2.setColor(new Color(0xBC002D));
            int size = height / 3;
            g2.fillOval(x + width / 2 - size / 2, y + height / 2 - size / 2, size, size);
        }

        private void paintVertical(Graphics2D g2, int x, int y, Color a, Color b, Color c) {
            int part = width / 3;
            g2.setColor(a); g2.fillRect(x, y, part, height);
            g2.setColor(b); g2.fillRect(x + part, y, part, height);
            g2.setColor(c); g2.fillRect(x + part * 2, y, width - part * 2, height);
        }

        private void paintHorizontal(Graphics2D g2, int x, int y, Color a, Color b, Color c) {
            int part = height / 3;
            g2.setColor(a); g2.fillRect(x, y, width, part);
            g2.setColor(b); g2.fillRect(x, y + part, width, part);
            g2.setColor(c); g2.fillRect(x, y + part * 2, width, height - part * 2);
        }

        private void paintSolid(Graphics2D g2, int x, int y, Color color) {
            g2.setColor(color);
            g2.fillRect(x, y, width, height);
        }
    }

    private static Shape mapleLeaf(double cx, double cy, double r) {
        Path2D.Double leaf = new Path2D.Double();
        leaf.moveTo(cx, cy - r);
        leaf.lineTo(cx + r * 0.18, cy - r * 0.35);
        leaf.lineTo(cx + r * 0.58, cy - r * 0.62);
        leaf.lineTo(cx + r * 0.42, cy - r * 0.12);
        leaf.lineTo(cx + r * 0.82, cy - r * 0.05);
        leaf.lineTo(cx + r * 0.28, cy + r * 0.12);
        leaf.lineTo(cx + r * 0.42, cy + r * 0.62);
        leaf.lineTo(cx, cy + r * 0.34);
        leaf.lineTo(cx - r * 0.42, cy + r * 0.62);
        leaf.lineTo(cx - r * 0.28, cy + r * 0.12);
        leaf.lineTo(cx - r * 0.82, cy - r * 0.05);
        leaf.lineTo(cx - r * 0.42, cy - r * 0.12);
        leaf.lineTo(cx - r * 0.58, cy - r * 0.62);
        leaf.lineTo(cx - r * 0.18, cy - r * 0.35);
        leaf.closePath();
        return leaf;
    }

    private static Shape star(double cx, double cy, double r) {
        Path2D.Double star = new Path2D.Double();
        for (int i = 0; i < 10; i++) {
            double angle = Math.PI / 2 + i * Math.PI / 5;
            double radius = i % 2 == 0 ? r : r * 0.42;
            double px = cx + Math.cos(angle) * radius;
            double py = cy - Math.sin(angle) * radius;
            if (i == 0) {
                star.moveTo(px, py);
            } else {
                star.lineTo(px, py);
            }
        }
        star.closePath();
        return star;
    }

    static Graphics2D prepare(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        return g2;
    }
}
