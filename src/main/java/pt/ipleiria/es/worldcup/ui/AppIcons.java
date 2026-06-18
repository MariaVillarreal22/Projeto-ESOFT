package pt.ipleiria.es.worldcup.ui;

import javax.swing.Icon;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.RoundRectangle2D;

final class AppIcons {
    private AppIcons() {
    }

    static Icon search() {
        return new LineIcon(18, "search");
    }

    static Icon calendar() {
        return new LineIcon(18, "calendar");
    }

    static Icon menu() {
        return new LineIcon(28, "menu");
    }

    static Icon argentinaFlag() {
        return new ArgentinaFlagIcon(32, 24);
    }

    static Icon module(String text, Color background) {
        return new ModuleIcon(42, text, background);
    }

    private static final class LineIcon implements Icon {
        private final int size;
        private final String type;

        private LineIcon(int size, String type) {
            this.size = size;
            this.type = type;
        }

        @Override
        public int getIconWidth() {
            return size;
        }

        @Override
        public int getIconHeight() {
            return size;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = prepare(g);
            g2.setColor(c.getForeground());
            g2.setStroke(new BasicStroke(2.2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

            if ("search".equals(type)) {
                g2.drawOval(x + 2, y + 2, size - 8, size - 8);
                g2.drawLine(x + size - 6, y + size - 6, x + size - 1, y + size - 1);
            } else if ("calendar".equals(type)) {
                g2.drawRoundRect(x + 2, y + 4, size - 4, size - 5, 4, 4);
                g2.drawLine(x + 2, y + 9, x + size - 2, y + 9);
                g2.drawLine(x + 6, y + 1, x + 6, y + 6);
                g2.drawLine(x + size - 6, y + 1, x + size - 6, y + 6);
            } else {
                g2.drawLine(x + 2, y + 6, x + size - 2, y + 6);
                g2.drawLine(x + 2, y + size / 2, x + size - 2, y + size / 2);
                g2.drawLine(x + 2, y + size - 6, x + size - 2, y + size - 6);
            }

            g2.dispose();
        }
    }

    private static final class ArgentinaFlagIcon implements Icon {
        private final int width;
        private final int height;

        private ArgentinaFlagIcon(int width, int height) {
            this.width = width;
            this.height = height;
        }

        @Override
        public int getIconWidth() {
            return width;
        }

        @Override
        public int getIconHeight() {
            return height;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = prepare(g);
            ShapePainter.round(g2, x, y, width, height, 10, Color.WHITE);
            g2.setClip(new RoundRectangle2D.Double(x, y, width, height, 10, 10));
            g2.setColor(new Color(0x75AADB));
            g2.fillRect(x, y, width, height / 3);
            g2.fillRect(x, y + (height / 3) * 2, width, height / 3 + 1);
            g2.setColor(new Color(0xF5C867));
            g2.fill(new Ellipse2D.Double(x + width / 2.0 - 3, y + height / 2.0 - 3, 6, 6));
            g2.setClip(null);
            g2.setColor(new Color(0, 0, 0, 35));
            g2.drawRoundRect(x, y, width - 1, height - 1, 10, 10);
            g2.dispose();
        }
    }

    private static final class ModuleIcon implements Icon {
        private final int size;
        private final String text;
        private final Color background;

        private ModuleIcon(int size, String text, Color background) {
            this.size = size;
            this.text = text;
            this.background = background;
        }

        @Override
        public int getIconWidth() {
            return size;
        }

        @Override
        public int getIconHeight() {
            return size;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = prepare(g);
            ShapePainter.round(g2, x, y, size, size, 12, background);
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Inter", Font.BOLD, 13));
            FontMetrics metrics = g2.getFontMetrics();
            int textX = x + (size - metrics.stringWidth(text)) / 2;
            int textY = y + (size + metrics.getAscent()) / 2 - 3;
            g2.drawString(text, textX, textY);
            g2.dispose();
        }
    }

    private static Graphics2D prepare(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        return g2;
    }

    private static final class ShapePainter {
        private ShapePainter() {
        }

        static void round(Graphics2D g2, int x, int y, int width, int height, int radius, Color color) {
            g2.setColor(color);
            g2.fill(new RoundRectangle2D.Double(x, y, width, height, radius, radius));
        }
    }
}
